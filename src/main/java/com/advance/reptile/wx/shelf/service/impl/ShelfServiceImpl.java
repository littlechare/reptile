package com.advance.reptile.wx.shelf.service.impl;
import com.advance.reptile.common.CommonUtils;
import com.advance.reptile.common.SysConstant;
import com.advance.reptile.reader.entity.Bookmark;
import com.advance.reptile.reader.entity.UserBook;
import com.advance.reptile.reader.service.IBookmarkService;
import com.advance.reptile.reader.service.IChapterService;
import com.advance.reptile.reader.service.IUserBookService;
import com.advance.reptile.wx.shelf.service.IShelfService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ShelfServiceImpl implements IShelfService{

    @Autowired
    private IUserBookService userBookService;
    @Autowired
    private IBookmarkService bookmarkService;
    @Autowired
    private IChapterService chapterService;

    @Override
    public List<Map<String, Object>> getShelfBooks(String userId, String bookName) {
        return bookmarkService.getBookMarks(bookName, userId);
    }

    @Override
    public boolean addBookShelf(String userId, String bookId) {
        if(isBookMarkExist(userId, bookId)){
            return false;
        }
        bookmarkService.addMarker(userId, bookId);
        return true;
    }

    /**
     * 移除书架中书签
     * @param id
     */
    @Override
    public void delShelfBooks(String id) {
        bookmarkService.removeById(id);
    }

    /**
     * 判断是否存在书架当中
     * @param userId
     * @param bookId
     * @return
     */
    private boolean isBookMarkExist(String userId, String bookId){
        QueryWrapper<Bookmark> bookmarkQueryWrapper = new QueryWrapper<>();
        bookmarkQueryWrapper.eq("book_id", bookId);
        bookmarkQueryWrapper.eq("user_id", userId);
        List<Bookmark> list = bookmarkService.list(bookmarkQueryWrapper);
        return list.size() > 0 ? true : false;
    }
}
