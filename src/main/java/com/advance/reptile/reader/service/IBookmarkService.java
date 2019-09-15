package com.advance.reptile.reader.service;

import com.advance.reptile.reader.entity.Bookmark;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  书签处理服务类
 * </p>
 *
 * @author author
 * @since 2019-07-28
 */
public interface IBookmarkService extends IService<Bookmark> {

    void addMarker(String userId, String bookId);

    List<Map<String, Object>> getBookMarks(String bookName, String userId);

}
