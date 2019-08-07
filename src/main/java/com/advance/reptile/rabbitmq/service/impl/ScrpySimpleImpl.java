package com.advance.reptile.rabbitmq.service.impl;

import com.advance.reptile.common.CommonUtils;
import com.advance.reptile.jsoup.constant.JsoupConstant;
import com.advance.reptile.jsoup.utils.JsoupUtil;
import com.advance.reptile.jsoup.vo.JsoupSaveDataVo;
import com.advance.reptile.mongo.pojo.ChapterMogo;
import com.advance.reptile.mongo.service.ChapterService;
import com.advance.reptile.rabbitmq.service.IScrpySimple;
import com.advance.reptile.reader.entity.Book;
import com.advance.reptile.reader.entity.Chapter;
import com.advance.reptile.reader.service.IBookService;
import com.advance.reptile.reader.service.IChapterService;
import com.advance.reptile.reader.vo.ScrpyParamVo;
import com.advance.reptile.redis.RedisService;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @ClassName ScrpySimpleImpl
 * @Description TODO
 * @Author Lenovo
 * @Date 2019/8/6 11:16
 * @Version V1.0
 **/
@Service
public class ScrpySimpleImpl implements IScrpySimple {

    @Autowired
    private RedisService redisService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private IBookService bookService;

    @Transactional
    @Override
    public void scrpyBook(Map<String, Object> param) throws Exception {

        ScrpyParamVo paramVo = new ScrpyParamVo();
        String baseUrl = CommonUtils.hanldNull(param.get("baseUrl"));
        paramVo.setBaseUrl(baseUrl);
        paramVo.setStartUrl(this.getStartUrl(baseUrl));
        Document doc = JsoupUtil.parseUrlHtml(paramVo.getBaseUrl());

        if(CommonUtils.isObjEmpty(doc)){
            return;
        }

        String title = JsoupUtil.getElementText(doc, JsoupConstant.CSS_QUERY_OF_BOOK_NAME);
        String author = JsoupUtil.parseDom(doc, JsoupConstant.CSS_QUERY_OF_BOOK_INFO).eq(0).text();
        if(!title.equals(CommonUtils.hanldNull(param.get("name"))) || !author.equals(CommonUtils.hanldNull(param.get("author")))){
            return;
        }

        if(bookService.isBookExist(title)){
           return;
        }

//        String bookId = bookService.saveDocIntoBook(doc, paramVo.getBaseUrl());
//        JsoupSaveDataVo dataVo = new JsoupSaveDataVo();
//        dataVo.setBookId(bookId);
//        dataVo.setBaseUrl(paramVo.getBaseUrl());
//        dataVo.setStartUrl(paramVo.getStartUrl());
//        dataVo.setCharpterId(CommonUtils.getUuid());
//        dataVo.setNextTitle("");
//        dataVo.setNext(CommonUtils.getUuid());
//        dataVo.setPre("");
//        dataVo.setPreTitle("无");
//        ChapterMogo chapterMogo = new ChapterMogo();
//        chapterMogo.setCharpterNum(0);
//        try {
//            doNext(dataVo.getStartUrl(),chapterMogo,dataVo);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public String getStartUrl(String baseUrl) throws Exception {
        Document document = JsoupUtil.parseUrlHtml(baseUrl);
        return JsoupUtil.getElementText(document, JsoupConstant.CSS_QUERY_START_URL);
    }

    @Transactional
    public void doNext(String url, ChapterMogo chapter, JsoupSaveDataVo dataVo) throws Exception {
        if(url.equals(dataVo.getBaseUrl())){
            chapter.setCharpterNum(chapter.getCharpterNum() + 1);
            chapter.setNextTitle("无");
            chapter.setNextId("");
            dataVo.setNext("");
            dataVo.setNextTitle("无");
            Chapter chapter1 = bookService.saveMysqlChapter(chapter,dataVo);
            chapterService.insertDocument(chapter);
            Book book = bookService.getBook(chapter1.getBookId());
            book.setPraNum(chapter.getCharpterNum());
            //修改章节数
            bookService.updateBook(book);
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
                chapterMysql = bookService.saveMysqlChapter(chapter,dataVo);
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
}
