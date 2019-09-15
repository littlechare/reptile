package com.advance.reptile.wx.books.service.impl;

import com.advance.reptile.common.CommonUtils;
import com.advance.reptile.common.SysConstant;
import com.advance.reptile.mongo.pojo.ChapterMogo;
import com.advance.reptile.mongo.service.ChapterService;
import com.advance.reptile.reader.entity.Book;
import com.advance.reptile.reader.entity.Chapter;
import com.advance.reptile.reader.entity.UserBook;
import com.advance.reptile.reader.service.IBookService;
import com.advance.reptile.reader.service.IChapterService;
import com.advance.reptile.reader.service.IUserBookService;
import com.advance.reptile.wx.books.service.IWxBookService;
import com.advance.reptile.wx.login.entity.User;
import com.advance.reptile.wx.shelf.service.IShelfService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxBookService implements IWxBookService {

    @Autowired
    private IBookService bookService;
    @Autowired
    private IChapterService chapterService;
    @Autowired
    private ChapterService mongoChapterService;
    @Autowired
    private IShelfService shelfService;
    @Autowired
    private IUserBookService userBookService;

    @Override
    public List<Book> getWxBooks(String bookName) {
        return bookService.getBookList(bookName);
    }

    @Override
    public Book getBookDetail(String bookId) {
        return bookService.getBook(bookId);
    }

    @Override
    public List<Chapter> getChaptersList(String bookId) {
        return chapterService.getBookChapters(bookId);
    }

    @Override
    public ChapterMogo getChapterData(String chapterId) {
        ChapterMogo chapterMogo = new ChapterMogo();
        chapterMogo.setChapterId(chapterId);
        chapterMogo.setCharpterNum(null);
        return mongoChapterService.getChapter(chapterMogo);
    }

    @Override
    public Map<String, Object> getInitChapterId(String userId, String bookId) {
        Map<String, Object> resultData = new HashMap<>();
        //先判断是否存在浏览历史
        boolean hasHistory = isAdd(userId, bookId);
        String chapterId = "";
        String initChapterId = getBookDetail(bookId).getStartChapterId();
        //存在历史纪录
        if(hasHistory){
            List<UserBook> list = getUserBook(userId, bookId, SysConstant.DATA_STATUS_VALID);
            chapterId = list.get(0).getChapterId();
            Integer chapterNum = list.get(0).getChapterNum();
            resultData.put("chapterNum", chapterNum);
            if(SysConstant.START_CHARPTER_NUM == chapterNum){
                hasHistory = false;
            }
        }else{
            chapterId = initChapterId;
        }
        resultData.put("isRead", hasHistory);
        resultData.put("chapterId", chapterId);
        resultData.put("initChapterId", initChapterId);
        return resultData;
    }

    /**
     * 是否存在浏览历史
     * @param userId
     * @param bookId
     * @return
     */
    private boolean isAdd(String userId, String bookId) {
        return getUserBook(userId, bookId, SysConstant.DATA_STATUS_VALID).size() > 0 ? true : false;
    }

    @Override
    public List<UserBook> getUserBook(String userId, String bookId, String status) {
        QueryWrapper<UserBook> userBookQueryWrapper = new QueryWrapper<>();
        userBookQueryWrapper.eq("user_id", userId);
        userBookQueryWrapper.eq("book_id", bookId);
        if(CommonUtils.isNotEmpty(status)){
            userBookQueryWrapper.eq("status", status);
        }
        List<UserBook> list = userBookService.list(userBookQueryWrapper);
        return list;
    }

    @Override
    public void updateUserBook(String userId, String bookId, Integer chapterNum, String chapterId) {
        //存在就更新
        List<UserBook> list = getUserBook(userId, bookId, SysConstant.DATA_STATUS_VALID);
        if(list.size() > 0){
            UserBook userBook = list.get(0);
            userBook.setChapterId(chapterId);
            userBook.setChapterNum(chapterNum);
            updateUserBookData(userBook);
            return;
        }
        userBookService.saveBookHistory(bookId, userId, chapterId, SysConstant.START_CHARPTER_NUM);
    }

    @Override
    public void updateUserBookData(UserBook userBook) {
        userBookService.updateById(userBook);
    }
}
