package com.advance.reptile.wx.books.controller;

import com.advance.reptile.common.Response;
import com.advance.reptile.common.SysConstant;
import com.advance.reptile.wx.books.service.IWxBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx/book")
public class WxBookController {

    @Autowired
    private IWxBookService wxBookService;

    @RequestMapping("/list")
    public Response bookList(String bookName){
        return Response.success(wxBookService.getWxBooks(bookName));
    }

    @RequestMapping("/book/{id}")
    public Response getBookDetail(@PathVariable("id")String id){
        return Response.success(wxBookService.getBookDetail(id));
    }

    @RequestMapping("/chapters/{id}")
    public Response getChapters(@PathVariable("id")String id){
        return Response.success(wxBookService.getChaptersList(id));
    }

    @RequestMapping("/chapter/init/get/id/{bookId}")
    public Response getInitChapterId(@PathVariable("bookId")String bookId){
        String userId = "0EFFF6396D10457C83927BB4A2224241";
        return Response.success(wxBookService.getInitChapterId(userId, bookId));
    }

    @RequestMapping("/chapter/{id}")
    public Response getChapterById(@PathVariable("id")String id){
        return Response.success(wxBookService.getChapterData(id));
    }

    /**
     * 添加浏览历史
     * @param bookId
     * @param chapterNum
     * @param chapterId
     * @return
     */
    @RequestMapping("/userbook/update/{bookId}")
    public Response addUserBook(@PathVariable("bookId")String bookId, Integer chapterNum, String chapterId){
        String userId = "0EFFF6396D10457C83927BB4A2224241";
        wxBookService.updateUserBook(userId, bookId, chapterNum, chapterId);
        return Response.success();
    }

    /**
     * 获取浏览历史记录
     * @param bookId
     * @return
     */
    @RequestMapping("/userbook/get/{bookId}")
    public Response getUserBook(@PathVariable("bookId")String bookId){
        String userId = "0EFFF6396D10457C83927BB4A2224241";
        return Response.success(wxBookService.getUserBook(userId, bookId, SysConstant.DATA_STATUS_VALID).get(0));
    }
}
