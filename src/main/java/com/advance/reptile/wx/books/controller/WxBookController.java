package com.advance.reptile.wx.books.controller;

import com.advance.reptile.common.Response;
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

}
