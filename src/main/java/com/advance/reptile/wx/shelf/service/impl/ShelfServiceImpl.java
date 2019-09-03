package com.advance.reptile.wx.shelf.service.impl;
import com.advance.reptile.common.CommonUtils;
import com.advance.reptile.common.SysConstant;
import com.advance.reptile.reader.entity.Book;
import com.advance.reptile.reader.entity.UserBook;
import com.advance.reptile.reader.service.IChapterService;
import com.advance.reptile.reader.service.IUserBookService;
import com.advance.reptile.wx.books.service.IWxBookService;
import com.advance.reptile.wx.shelf.service.IShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ShelfServiceImpl implements IShelfService{

    @Autowired
    private IUserBookService userBookService;
    @Autowired
    private IChapterService chapterService;

    @Override
    public List<Map<String, Object>> getShelfBooks(String userId) {
        return userBookService.getBookShelfDatas(userId);
    }

    @Override
    public void addBookShelf(String userId, String bookId) {
        String chapterId = chapterService.getBookChapters(bookId).get(0).getId();
        userBookService.saveBookHistory(bookId, userId, chapterId, SysConstant.START_CHARPTER_NUM);
    }

    @Override
    public void delShelfBooks(String id) {
        userBookService.delUserBook(id);
    }
}
