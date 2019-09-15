package com.advance.reptile.wx.books.service;

import com.advance.reptile.mongo.pojo.ChapterMogo;
import com.advance.reptile.reader.entity.Book;
import com.advance.reptile.reader.entity.Chapter;
import com.advance.reptile.reader.entity.UserBook;

import java.util.List;
import java.util.Map;

public interface IWxBookService {

    List<Book> getWxBooks(String bookName);

    Book getBookDetail(String bookId);

    List<Chapter> getChaptersList(String bookId);

    ChapterMogo getChapterData(String chapterId);

    Map<String, Object> getInitChapterId(String userId, String bookId);

    List<UserBook> getUserBook(String userId, String bookId, String status);

    void updateUserBook(String userId, String bookId, Integer chapterNum, String chapterId);

    void updateUserBookData(UserBook userBook);

}
