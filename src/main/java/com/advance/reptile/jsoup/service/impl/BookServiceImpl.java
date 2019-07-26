package com.advance.reptile.jsoup.service.impl;

import com.advance.reptile.common.Response;
import com.advance.reptile.jsoup.entity.Book;
import com.advance.reptile.jsoup.mapper.BookMapper;
import com.advance.reptile.jsoup.service.IBookService;
import com.advance.reptile.jsoup.utils.JsoupUtil;
import com.advance.reptile.mongoDb.pojo.Chapter;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jsoup.Jsoup;
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
 * @since 2019-06-28
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {

    @Autowired
    private BookMapper bookMapper;

    private final static String BASEIC_URL = "http://www.xinshubao.net/7/7828/";

    @Override
    public Book getBook(String id) {
        return bookMapper.selectById(id);
    }

    @Override
    public void saveDocIntoBook(Document doc, String path) {
        String bookName = doc.select("#info h1").text();
        String author = doc.select("#info p").eq(0).text();
        String status = doc.select("#info p").eq(1).text();
        String last_update = doc.select("#info p").eq(3).text();
        String intro = doc.select("#intro p").text();
        Elements links = doc.select("#list ._chapter a[href]");
        String startUrl = links.eq(0).attr("href");
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

    private void saveChapter(String srartUrl, Chapter chapter) throws IOException {
        String next = Jsoup.connect(srartUrl).get().select(".bottem1 p:eq(1) a:eq(3)").attr("href");

        if(!(srartUrl.lastIndexOf("_") > -1)){
            String title = Jsoup.connect(srartUrl).get().select(".bookname h1").text();
            System.err.println(chapter.getTxt());
            chapter = new Chapter();
            chapter.setUrl(srartUrl);
            chapter.setDataTime(LocalDateTime.now());
            chapter.setDataStatus("1");
            chapter.setBookId("");
            chapter.setTxt(Jsoup.connect(srartUrl).get().select("#content").text().trim().replaceAll("br/>",""));
            chapter.setTitle(title);
//			System.err.println("--------------------------------"+Jsoup.connect(url).get().select(".bookname h1").text()+"----------------------------------");
        }else{
            chapter.setTxt(chapter.getTxt() + Jsoup.connect(srartUrl).get().select("#content").text().trim().replaceAll("br/>",""));
        }
//		System.err.println(Jsoup.connect(url).get().select("#content").text().trim().replaceAll("br/>",""));
        if(srartUrl.equals(BASEIC_URL)){
            return;
        }
        srartUrl = BASEIC_URL + next;

        saveChapter(srartUrl, chapter);
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
