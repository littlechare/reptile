package com.advance.reptile.reader.service;

import com.advance.reptile.reader.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2019-07-28
 */
public interface IChapterService extends IService<Chapter> {

    List<Chapter> getBookChapters(String bookId);

}
