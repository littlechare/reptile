package com.advance.reptile.jsoup.service.impl;

import com.advance.reptile.common.Response;
import com.advance.reptile.jsoup.entity.Book;
import com.advance.reptile.jsoup.mapper.BookMapper;
import com.advance.reptile.jsoup.service.IBookService;
import com.advance.reptile.jsoup.utils.JsoupUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public void getBook(String path) {
        Document doc = JsoupUtil.parseUrlHtml(path);
        saveDocIntoBook(doc, path);
        List<String> chapter_links = getChapterLinks(doc);
    }

    @Override
    public void saveDocIntoBook(Document document, String path) {
        String bookName = document.select("#info h1").text();
        String author = document.select("#info p").eq(0).text();
        String status = document.select("#info p").eq(1).text();
        String last_update = document.select("#info p").eq(3).text();
        String intro = document.select("#intro p").text();
        Book book = new Book();
        book.setName(bookName);
        book.setAuthor(author);
        book.setLastUpdateTime(last_update);
        book.setUrl(path);
        book.setIntro(intro);
        book.setStatus(status);
        saveBook(book);
    }

    @Override
    public void saveBook(Book book) {
        bookMapper.insert(book);
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
