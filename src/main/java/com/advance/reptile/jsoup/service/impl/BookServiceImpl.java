package com.advance.reptile.jsoup.service.impl;

import com.advance.reptile.common.CommonUtils;
import com.advance.reptile.jsoup.JsoupConstant;
import com.advance.reptile.jsoup.entity.MongoBook;
import com.advance.reptile.jsoup.mapper.MongoBookMapper;
import com.advance.reptile.jsoup.service.IBookService;
import com.advance.reptile.jsoup.utils.JsoupUtil;
import com.advance.reptile.jsoup.vo.JsoupSaveDataVo;
import com.advance.reptile.mongoDb.pojo.ChapterMogo;
import com.advance.reptile.mongoDb.service.ChapterService;
import com.advance.reptile.reader.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
 * @since 2019-06-28
 */
@Service(value = "mongoBookService")
public class BookServiceImpl extends ServiceImpl<MongoBookMapper, MongoBook> implements IBookService {

    @Autowired
    private MongoBookMapper bookMapper;
    @Autowired
    private ChapterService chapterService;

    @Value("${sysConstant.BASE_URL}")
    private String BASEIC_URL;

    @Override
    public MongoBook getBook(String id) {
        return bookMapper.selectById(id);
    }

    @Override
    public void saveDocIntoBook(Document doc, String path) {
        String bookName = JsoupUtil.getElementText(doc, JsoupConstant.CSS_QUERY_OF_BOOK_NAME);
        String author = JsoupUtil.parseDom(doc, JsoupConstant.CSS_QUERY_OF_BOOK_INFO).eq(0).text();
        String status = JsoupUtil.parseDom(doc, JsoupConstant.CSS_QUERY_OF_BOOK_INFO).eq(1).text();
        String last_update = JsoupUtil.parseDom(doc, JsoupConstant.CSS_QUERY_OF_BOOK_INFO).eq(3).text();
        String intro = JsoupUtil.getElementText(doc, JsoupConstant.CSS_QUERY_OF_BOOK_INFO);
        MongoBook book = new MongoBook();
        book.setName(bookName);
        book.setAuthor(author);
        book.setLastUpdateTime(last_update);
        book.setUrl(BASEIC_URL);
        book.setIntro(intro);
        book.setStatus(status);
        saveBook(book);
    }

    @Override
    public void saveBook(MongoBook book) {
        bookMapper.insert(book);
    }

    public void doNext(String url, ChapterMogo chapter, JsoupSaveDataVo dataVo) throws IOException {
        //获取文档内容
        Document document = JsoupUtil.parseUrlHtml(url);
        //获取下一章url地址
        String next = JsoupUtil.getElementAttr(document, JsoupConstant.CSS_QUERY_OF_NEXT_CHAPTER_URL, "href");
        //不包含_的视为本章开头
        if(!(url.lastIndexOf("_") > -1)){
            chapter.setCharpterNum(chapter.getCharpterNum() + 1);
            //内容不为空保存
            if(CommonUtils.objNotEmpty(chapter.getTxt())){
                saveMysqlChapter(chapter);
                //保存至mongoDb
                chapterService.insertDocument(chapter);
            }
            String title = JsoupUtil.getElementText(document, JsoupConstant.CSS_QUERY_OF_CHAPTER_TITLE);
            chapter = new ChapterMogo();
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
        if(url.equals(BASEIC_URL)){
            System.err.println("-------结束--------");
            chapterService.insertDocument(chapter);
            return;
        }
        url = BASEIC_URL + next;

        doNext(url, chapter, dataVo);
    }

    private void saveMysqlChapter(ChapterMogo mogo){
        Chapter chapter = new Chapter();
        chapter.setId(CommonUtils.getUuid());
        chapter.setTitle(mogo.getTitle());
        chapter.setUrl(mogo.getUrl());
        chapter.setNum(mogo.getCharpterNum());
        chapter.setPre(mogo.getPreId());
        chapter.setNext(mogo.getNextId());
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
}
