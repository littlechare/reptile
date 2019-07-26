package com.advance.reptile.jsoup.controller;


import com.advance.reptile.common.CommonUtils;
import com.advance.reptile.common.Response;
import com.advance.reptile.jsoup.entity.Book;
import com.advance.reptile.jsoup.service.IBookService;
import com.advance.reptile.mongoDb.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2019-06-28
 */
@RestController
@RequestMapping("/jsoup/book")
public class BookController {

    @Autowired
    private IBookService bookService;
    @Autowired
    private ChapterService chapterService;
    /**
     * 爬取书籍信息
     * @param param
     * @return
     */
    @RequestMapping(value = "getBook")
    public Response getBook(@RequestParam Map<String, String> param){
        String path = CommonUtils.hanldNull(param.get("path"));
        Book book = bookService.getBook(path);

        return Response.success();
    }

}
