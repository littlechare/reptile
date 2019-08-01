package com.advance.reptile.reader.service.impl;

import com.advance.reptile.common.CommonUtils;
import com.advance.reptile.common.Logger;
import com.advance.reptile.jsoup.constant.JsoupConstant;
import com.advance.reptile.jsoup.utils.JsoupUtil;
import com.advance.reptile.jsoup.vo.JsoupSaveDataVo;
import com.advance.reptile.mongo.pojo.ChapterMogo;
import com.advance.reptile.mongo.service.ChapterService;
import com.advance.reptile.reader.vo.ScrpyParamVo;
import com.advance.reptile.reader.constant.ReadConstant;
import com.advance.reptile.reader.entity.Book;
import com.advance.reptile.reader.entity.Chapter;
import com.advance.reptile.reader.mapper.BookMapper;
import com.advance.reptile.reader.service.IBookService;
import com.advance.reptile.reader.service.IChapterService;
import com.advance.reptile.redis.RedisService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2019-07-28
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {

    Logger logger = Logger.getLogger(BookServiceImpl.class);

    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private IChapterService mysqlChpterService;
    @Autowired
    private RedisService redisService;

    private String URL_PREFIX = "http://www.xinshubao.net/7/7828/";

    private static String BASEIC_URL = "http://www.xinshubao.net/7/7828/23028539.html";

    @Override
    public List<Book> getBookList( String bookName) {
        if(CommonUtils.isNotEmpty(bookName)){
            JSONObject data = JSONObject.parseObject(bookName);
            bookName = data.getString("bookName");
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.like("name",bookName);
            List<Book> bookList = super.list(queryWrapper);
            logger.info("/------------  从Mysql中搜索出书籍列表截数据   ------------/");
            return bookList;
        }
        if(redisService.exists(ReadConstant.REDIS_KEY_OF_BOOK_LIST)){
            logger.info("/------------  从redis中取出书籍列表截数据   ------------/");
            return redisService.getList(ReadConstant.REDIS_KEY_OF_BOOK_LIST).toJavaList(Book.class);
        }else{
            List<Book> bookList = super.list();
            redisService.set(ReadConstant.REDIS_KEY_OF_BOOK_LIST, bookList, 30);//半小时过期，这样可以确保书籍更新能比较好的展示
            logger.info("/------------  从Mysql中取出书籍列表截数据   ------------/");
            return bookList;
        }
    }

    @Override
    public Book getBook(String id) {
        return bookMapper.selectById(id);
    }

    @Override
    public String saveDocIntoBook(Document doc, String path) {
        String bookName = JsoupUtil.getElementText(doc, JsoupConstant.CSS_QUERY_OF_BOOK_NAME);
        String author = JsoupUtil.parseDom(doc, JsoupConstant.CSS_QUERY_OF_BOOK_INFO).eq(0).text();
        String status = JsoupUtil.parseDom(doc, JsoupConstant.CSS_QUERY_OF_BOOK_INFO).eq(1).text();
        String last_update = JsoupUtil.parseDom(doc, JsoupConstant.CSS_QUERY_OF_BOOK_INFO).eq(3).text();
        String intro = JsoupUtil.getElementText(doc, JsoupConstant.CSS_QUERY_OF_BOOK_INFO);
        Book book = new Book();
        String uuid = CommonUtils.getUuid();
        book.setId(uuid);
        book.setName(bookName);
        book.setAuthor(author);
        book.setLastUpdateTime(last_update);
        book.setUrl(path);
        book.setIntro(intro);
        book.setStatus(status);
        saveBook(book);
        return uuid;
    }

    @Transactional
    @Override
    public void saveBook(Book book) {
        bookMapper.insert(book);
    }

    @Transactional
    public void doNext(String url, ChapterMogo chapter, JsoupSaveDataVo dataVo) throws IOException {
        if(url.equals(dataVo.getBaseUrl())){
            chapter.setCharpterNum(chapter.getCharpterNum() + 1);
            chapter.setNextTitle("无");
            chapter.setNextId("");
            dataVo.setNext("");
            dataVo.setNextTitle("无");
            saveMysqlChapter(chapter,dataVo);
            chapterService.insertDocument(chapter);
            return ;
        }
        //获取文档内容
        Document document = JsoupUtil.parseUrlHtml(url);
        //获取下一章url地址
        String next = JsoupUtil.getElementAttr(document, JsoupConstant.CSS_QUERY_OF_NEXT_CHAPTER_URL, "href");
        if(!(next.lastIndexOf("_") > -1)){
            if(!next.equals(dataVo.getBaseUrl())){
                dataVo.setNextTitle(JsoupUtil.getElementText(JsoupUtil.parseUrlHtml(dataVo.getBaseUrl() + next),JsoupConstant.CSS_QUERY_OF_CHAPTER_TITLE));
                chapter.setNextTitle(dataVo.getNextTitle());
            }
        }
        //不包含_的视为本章开头
        if(!(url.lastIndexOf("_") > -1)){
            Chapter chapterMysql = new Chapter();
            //下一章标题
            String title = JsoupUtil.getElementText(document, JsoupConstant.CSS_QUERY_OF_CHAPTER_TITLE);
            //内容不为空保存
            if(CommonUtils.objNotEmpty(chapter.getTxt())){
                chapter.setCharpterNum(chapter.getCharpterNum() + 1);
                chapterMysql = saveMysqlChapter(chapter,dataVo);
                dataVo.setPreTitle(chapter.getTitle());
                //保存至mongoDb
                chapterService.insertDocument(chapter);
                //在此处设置保存参数
                //下一章信息
                dataVo.setNext(CommonUtils.getUuid());

                //上一章信息
                dataVo.setPre(dataVo.getCharpterId());
                dataVo.setCharpterId(chapterMysql.getNext());
            }
//            chapter = new ChapterMogo();
            chapter.setUrl(url);
            chapter.setDataTime(LocalDateTime.now());
            chapter.setDataStatus("1");
            chapter.setChapterId(dataVo.getCharpterId());
            chapter.setNextId(dataVo.getNext());
            chapter.setNextTitle(dataVo.getNextTitle());
            chapter.setPreId(dataVo.getPre());
            chapter.setPreTitle(dataVo.getPreTitle());
            chapter.setTxt(JsoupUtil.getElementText(document, JsoupConstant.CSS_QUERY_OF_CHAPTER_CONTENT).trim().replaceAll("br/>",""));
            chapter.setTitle(title);

        }else{
            chapter.setTxt(chapter.getTxt() + JsoupUtil.getElementText(document, JsoupConstant.CSS_QUERY_OF_CHAPTER_CONTENT).trim().replaceAll("br/>",""));
        }

        if(next.equals(dataVo.getBaseUrl())){
            url = next;
        }else{
            url = dataVo.getBaseUrl() + next;
        }

        doNext(url, chapter, dataVo);
    }

    /**
     * 保存mysql章节信息
     * @param mogo
     */
    private Chapter saveMysqlChapter(ChapterMogo mogo, JsoupSaveDataVo dataVo){
        Chapter chapter = new Chapter();
        chapter.setId(dataVo.getCharpterId());
        chapter.setTitle(mogo.getTitle());
        chapter.setUrl(mogo.getUrl());
        chapter.setNum(mogo.getCharpterNum());
        chapter.setPre(dataVo.getPre());
        chapter.setNext(dataVo.getNext());
        chapter.setBookId(dataVo.getBookId());
        mysqlChpterService.save(chapter);
        return chapter;
    }

    @Override
    public List<String> getChapterLinks(Document doc) {
        List<String> results = new ArrayList<>();
        List<Map<String, String>> chapterDatas = new ArrayList<>();
        Elements links = doc.select("#list ._chapter a[href]");
        for (Element link : links) {
            Map<String, String> chapter = new HashMap<>();
            String href = link.attr("href");
            String name = link.text();
            chapter.put("title", name);
            chapter.put("url", href);
            results.add(href);
            chapterDatas.add(chapter);
        }
        return results;
    }

    @Transactional
    @Override
    public void scrpyBook( Map<String, String> param) {
        ScrpyParamVo paramVo = new ScrpyParamVo();
        paramVo.setBaseUrl(CommonUtils.hanldNull(param.get("baseUrl")));
        paramVo.setStartUrl(CommonUtils.hanldNull(param.get("startUrl")));
        Document doc = JsoupUtil.parseUrlHtml(paramVo.getBaseUrl());
        String bookId = saveDocIntoBook(doc, paramVo.getBaseUrl());
        JsoupSaveDataVo dataVo = new JsoupSaveDataVo();
        dataVo.setBookId(bookId);
        dataVo.setBaseUrl(paramVo.getBaseUrl());
        dataVo.setStartUrl(paramVo.getStartUrl());
        dataVo.setCharpterId(CommonUtils.getUuid());
        dataVo.setNextTitle("");
        dataVo.setNext(CommonUtils.getUuid());
        dataVo.setPre("");
        dataVo.setPreTitle("无");
        ChapterMogo chapterMogo = new ChapterMogo();
        chapterMogo.setCharpterNum(0);
        try {
            doNext(dataVo.getStartUrl(),chapterMogo,dataVo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
