package com.advance.reptile.wx.books.service.impl;

import com.advance.reptile.reader.entity.Book;
import com.advance.reptile.reader.service.IBookService;
import com.advance.reptile.wx.books.service.IWxBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxBookService implements IWxBookService {

    @Autowired
    private IBookService bookService;

    @Override
    public List<Book> getWxBooks(String bookName) {
        return bookService.getBookList(bookName);
    }

    @Override
    public Book getBookDetail(String bookId) {
        return bookService.getBook(bookId);
    }
}
