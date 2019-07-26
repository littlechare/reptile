package com.advance.reptile.mongoDb.service;

import com.advance.reptile.mongoDb.dao.ChapterRepository;
import com.advance.reptile.mongoDb.pojo.Chapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MongoDb服务层
 */
@Service
public class ChapterService {

    @Autowired
    private ChapterRepository chapterRepository;

    public void insertDocument(Chapter chapter){
        chapterRepository.save(chapter);
    }

    public Chapter getChapter(Chapter chapter){
        return chapterRepository.queryOne(chapter);
    }

}
