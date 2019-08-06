package com.advance.reptile.reader.service;

import com.advance.reptile.jsoup.vo.JsoupSaveDataVo;
import com.advance.reptile.mongo.pojo.ChapterMogo;
import com.advance.reptile.reader.entity.Book;
import com.advance.reptile.reader.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2019-07-28
 */
public interface IBookService extends IService<Book> {

    List<Book> getBookList(String bookName);

    Book getBook(String id);

    String saveDocIntoBook(Document document, String path);

    void saveBook(Book book);

    List<String> getChapterLinks(Document doc);

//    void scrpyBook( Map<String, String> paramVo);

    void updateBook(Book book);

    Chapter saveMysqlChapter(ChapterMogo mogo, JsoupSaveDataVo dataVo);

    boolean isBookExist(String bookName);
}
