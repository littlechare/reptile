package com.advance.reptile.reader.controller;


import com.advance.reptile.common.Response;
import com.advance.reptile.mongoDb.pojo.ChapterMogo;
import com.advance.reptile.mongoDb.service.ChapterService;
import com.advance.reptile.reader.service.IChapterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author author
 * @since 2019-07-28
 */
@RestController
@RequestMapping("/reader/chapter")
@CrossOrigin
public class ChapterController {

    @Autowired
    private IChapterService chapterService;
    @Autowired
    private ChapterService mongoChapterService;

    @RequestMapping("/chapters/{bookId}")
    public Response getChapters(@PathVariable("bookId")String bookId){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("book_id", bookId);
        wrapper.orderByAsc("num");
        return Response.success(chapterService.list(wrapper));
    }

    @RequestMapping("/{id}")
    public Response getChapterById(@PathVariable("id")String id){
        ChapterMogo chapterMogo = new ChapterMogo();
        chapterMogo.setChapterId(id);
        chapterMogo.setCharpterNum(null);
        return Response.success(mongoChapterService.getChapter(chapterMogo));
    }
}
