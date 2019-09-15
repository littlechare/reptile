package com.advance.reptile.wx.shelf.service;

import com.advance.reptile.reader.entity.UserBook;

import java.util.List;
import java.util.Map;

public interface IShelfService {

    List<Map<String, Object>> getShelfBooks(String userId, String bookName);

    boolean addBookShelf(String userId, String bookId);

    void delShelfBooks(String ids);
}
