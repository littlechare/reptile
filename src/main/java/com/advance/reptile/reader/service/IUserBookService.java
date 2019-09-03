package com.advance.reptile.reader.service;

import com.advance.reptile.reader.entity.UserBook;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2019-08-01
 */
public interface IUserBookService extends IService<UserBook> {

    void saveBookHistory(String bookId, String userId, String chapterId, int num);

    UserBook getBookHistpry(String bookId, String userId);

    List<Map<String, Object>> getBookShelfDatas(String userId);

    void delUserBook(String id);

}
