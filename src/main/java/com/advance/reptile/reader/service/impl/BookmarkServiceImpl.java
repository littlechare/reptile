package com.advance.reptile.reader.service.impl;

import com.advance.reptile.common.CommonUtils;
import com.advance.reptile.reader.entity.Bookmark;
import com.advance.reptile.reader.mapper.BookmarkMapper;
import com.advance.reptile.reader.service.IBookmarkService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2019-07-28
 */
@Service
public class BookmarkServiceImpl extends ServiceImpl<BookmarkMapper, Bookmark> implements IBookmarkService {

    @Autowired
    private BookmarkMapper bookmarkMapper;

    /**
     * 保存书架书籍
     * @param userId
     * @param bookId
     */
    @Override
    public void addMarker(String userId, String bookId) {
        Bookmark bookmark = new Bookmark();
        bookmark.setId(CommonUtils.getUuid());
        bookmark.setBookId(bookId);
        bookmark.setUserId(userId);
        save(bookmark);
    }

    /**
     * 查询书架书籍信息
     * @param bookName
     * @return
     */
    @Override
    public List<Map<String, Object>> getBookMarks(String bookName, String userId) {
        return bookmarkMapper.selectBookMarks(bookName, userId);
    }
}
