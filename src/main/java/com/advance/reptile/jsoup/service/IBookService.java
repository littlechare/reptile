package com.advance.reptile.jsoup.service;

import com.advance.reptile.common.Response;
import com.advance.reptile.jsoup.entity.Book;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2019-06-28
 */
public interface IBookService extends IService<Book> {

    void getBook(String path);

    void saveDocIntoBook(Document document, String path);

    void saveBook(Book book);

    List<String> getChapterLinks(Document doc);
}
