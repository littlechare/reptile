package com.advance.reptile.reader.controller;


import com.advance.reptile.common.CommonUtils;
import com.advance.reptile.common.Response;
import com.advance.reptile.mongoDb.service.ChapterService;
import com.advance.reptile.reader.ScrpyParamVo;
import com.advance.reptile.reader.entity.Book;
import com.advance.reptile.reader.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2019-07-28
 */
@RestController
@RequestMapping("/reader/book")
@CrossOrigin
public class BookController {

    @Autowired
    private IBookService bookService;
    @Autowired
    private ChapterService chapterService;
    /**
     * 爬取书籍信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/getBook/{id}")
    public Response getBook(@PathVariable("id")String id){
        Book book = bookService.getBook(id);
        return Response.success(book);
    }

    @RequestMapping(value = "/getBooks")
    public Response getBooks(){
        return Response.success(bookService.getBookList());
    }

    @RequestMapping(value = "/scrpyBook")
    public Response scrpyBook(@RequestParam Map<String, String> paramVo){
        bookService.scrpyBook(paramVo);
        return Response.success();
    }
}
