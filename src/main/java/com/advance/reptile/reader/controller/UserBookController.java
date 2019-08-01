package com.advance.reptile.reader.controller;


import com.advance.reptile.common.CommonUtils;
import com.advance.reptile.common.Response;
import com.advance.reptile.common.SysConstant;
import com.advance.reptile.reader.entity.UserBook;
import com.advance.reptile.reader.service.IUserBookService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2019-08-01
 */
@RestController
@RequestMapping("/reader/user-book")
public class UserBookController {

    @Autowired
    private IUserBookService userBookService;

    @RequestMapping("/save/{bookId}/{chapterId}/{num}")
    public Response saveBookHistory(@PathVariable("bookId")String bookId, @PathVariable("chapterId")String chapterId, @PathVariable("num")int num){
        userBookService.saveBookHistory(bookId, "zhouz", chapterId, num);
        return Response.success();
    }

    @RequestMapping("/history/{bookId}")
    public Response bookHistory(@PathVariable("bookId")String bookId){
        return Response.success(userBookService.getBookHistpry(bookId, "zhouz"));
    }

}
