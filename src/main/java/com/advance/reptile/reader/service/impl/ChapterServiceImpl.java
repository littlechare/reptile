package com.advance.reptile.reader.service.impl;

import com.advance.reptile.common.Logger;
import com.advance.reptile.reader.constant.ReadConstant;
import com.advance.reptile.reader.entity.Chapter;
import com.advance.reptile.reader.mapper.ChapterMapper;
import com.advance.reptile.reader.service.IChapterService;
import com.advance.reptile.redis.RedisService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2019-07-28
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements IChapterService {

    Logger logger = Logger.getLogger(ChapterServiceImpl.class);

    @Autowired
    private RedisService redisService;

    @Override
    public List<Chapter> getBookChapters(String bookId) {
        if(redisService.exists(ReadConstant.REDIS_KEY_PREFIX_OF_CHAPTER + bookId)){
            logger.info("/------------  从redis中取出章节截数据   ------------/");
            return redisService.getList(ReadConstant.REDIS_KEY_PREFIX_OF_CHAPTER + bookId).toJavaList(Chapter.class);
        }else{
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("book_id", bookId);
            wrapper.orderByAsc("num");
            List<Chapter> chapters = super.list(wrapper);
            redisService.set(ReadConstant.REDIS_KEY_PREFIX_OF_CHAPTER + bookId, chapters, 60*24);//章节更新一般比较慢，设置一天到期
            logger.info("/------------  从Mysql中取出章节截数据  -------------/");
            return chapters;
        }
    }
}
