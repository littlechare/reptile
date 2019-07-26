package com.advance.reptile.mongoDb.dao;

import com.advance.reptile.mongoDb.pojo.Chapter;
import org.springframework.stereotype.Repository;

/**
 * 章节dao层
 */
@Repository
public class ChapterRepository extends BaseMongoRepository<Chapter>{
    @Override
    protected Class getEntityClass() {
        return Chapter.class;
    }
}
