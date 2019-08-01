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

    @RequestMapping("/save/{bookId}/{num}")
    public Response saveBookHistory(@PathVariable("bookId")String bookId, @PathVariable("num")int num){
        UserBook userBook = new UserBook();
        String uuid = CommonUtils.getUuid();
        userBook.setId(uuid);
        userBook.setBookId(bookId);
        userBook.setUserId("zhouz");
        userBook.setChapterNum(num);
        userBookService.save(userBook);
        return Response.success();
    }

    @RequestMapping("/history/{bookId}")
    public Response bookHistory(@PathVariable("bookId")String bookId){
        QueryWrapper<UserBook> queryWrapper = new QueryWrapper();
        queryWrapper.eq("book_id",bookId);
        queryWrapper.eq("user_id","zhouz");
        queryWrapper.eq("status", SysConstant.DATA_STATUS_VALID);
        UserBook userBook = userBookService.list(queryWrapper).size() > 0 ? userBookService.list(queryWrapper).get(0) : null;
        return Response.success(userBook);
    }

}
