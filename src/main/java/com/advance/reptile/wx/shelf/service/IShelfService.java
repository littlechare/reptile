package com.advance.reptile.wx.shelf.service;

import java.util.List;
import java.util.Map;

public interface IShelfService {

    List<Map<String, Object>> getShelfBooks(String userId);

    void addBookShelf(String userId, String bookId);

    void delShelfBooks(String ids);

}
