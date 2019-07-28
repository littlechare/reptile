package com.advance.reptile.mongoDb.dao;

import com.advance.reptile.mongoDb.pojo.ChapterMogo;
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
