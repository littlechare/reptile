package com.advance.reptile.reader.service.impl;

import com.advance.reptile.common.CommonUtils;
import com.advance.reptile.jsoup.constant.JsoupConstant;
import com.advance.reptile.jsoup.utils.JsoupUtil;
import com.advance.reptile.jsoup.vo.JsoupSaveDataVo;
import com.advance.reptile.mongoDb.pojo.ChapterMogo;
import com.advance.reptile.mongoDb.service.ChapterService;
import com.advance.reptile.reader.ScrpyParamVo;
import com.advance.reptile.reader.entity.Book;
import com.advance.reptile.reader.entity.Chapter;
import com.advance.reptile.reader.mapper.BookMapper;
import com.advance.reptile.reader.service.IBookService;
import com.advance.reptile.reader.service.IChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private IChapterService mysqlChpterService;

    private String URL_PREFIX = "http://www.xinshubao.net/7/7828/";

    private static String BASEIC_URL = "http://www.xinshubao.net/7/7828/23028539.html";

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

    @Override
    public void saveBook(Book book) {
        bookMapper.insert(book);
    }

    public void doNext(String url, ChapterMogo chapter, JsoupSaveDataVo dataVo) throws IOException {
        if(url.equals(URL_PREFIX)){
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
            if(!next.equals(URL_PREFIX)){
                dataVo.setNextTitle(JsoupUtil.getElementText(JsoupUtil.parseUrlHtml(URL_PREFIX + next),JsoupConstant.CSS_QUERY_OF_CHAPTER_TITLE));
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

        if(next.equals(URL_PREFIX)){
            url = next;
        }else{
            url = URL_PREFIX + next;
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

    @Override
    public void scrpyBook( ScrpyParamVo paramVo) {
        Document doc = JsoupUtil.parseUrlHtml(paramVo.getPath());
        String bookId = saveDocIntoBook(doc, paramVo.getPath());
        JsoupSaveDataVo dataVo = new JsoupSaveDataVo();
        dataVo.setBookId(bookId);
        dataVo.setCharpterId(CommonUtils.getUuid());
        dataVo.setNextTitle("");
        dataVo.setNext(CommonUtils.getUuid());
        dataVo.setPre("");
        dataVo.setPreTitle("无");
        ChapterMogo chapterMogo = new ChapterMogo();
        chapterMogo.setCharpterNum(0);
        try {
            doNext(BASEIC_URL,chapterMogo,dataVo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
