package com.advance.reptile.reader.service;

import com.advance.reptile.reader.entity.UserBook;
import com.baomidou.mybatisplus.extension.service.IService;

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

}
