package com.advance.reptile.mongo.service;

import com.advance.reptile.mongo.dao.ChapterRepository;
import com.advance.reptile.mongo.pojo.ChapterMogo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MongoDb服务层
 */
@Service
public class ChapterService {

    @Autowired
    private ChapterRepository chapterRepository;

    public void insertDocument(ChapterMogo chapter){
        chapterRepository.save(chapter);
    }

    public ChapterMogo getChapter(ChapterMogo chapter){
        return chapterRepository.queryOne(chapter);
    }

}
