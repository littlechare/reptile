package com.advance.reptile.mongo.dao;

import com.advance.reptile.mongo.pojo.ChapterMogo;
import org.springframework.stereotype.Repository;

/**
 * 章节dao层
 */
@Repository
public class ChapterRepository extends BaseMongoRepository<ChapterMogo>{
    @Override
    protected Class getEntityClass() {
        return ChapterMogo.class;
    }
}
