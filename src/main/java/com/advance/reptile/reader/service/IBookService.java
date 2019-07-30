package com.advance.reptile.reader.service;

import com.advance.reptile.reader.entity.Book;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2019-07-28
 */
public interface IBookService extends IService<Book> {

    Book getBook(String id);

    void saveDocIntoBook(Document document, String path);

    void saveBook(Book book);

    List<String> getChapterLinks(Document doc);

}
