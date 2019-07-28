package com.advance.reptile.jsoup.service;

import com.advance.reptile.jsoup.entity.MongoBook;
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
public interface IBookService extends IService<MongoBook> {

    MongoBook getBook(String id);

    void saveDocIntoBook(Document document, String path);

    void saveBook(MongoBook book);

    List<String> getChapterLinks(Document doc);
}
