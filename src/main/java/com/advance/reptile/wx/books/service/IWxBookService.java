package com.advance.reptile.wx.books.service;

import com.advance.reptile.reader.entity.Book;

import java.util.List;

public interface IWxBookService {

    List<Book> getWxBooks(String bookName);

    Book getBookDetail(String bookId);

}
