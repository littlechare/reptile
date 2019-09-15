package com.advance.reptile.reader.service.impl;

import com.advance.reptile.common.CommonUtils;
import com.advance.reptile.common.SysConstant;
import com.advance.reptile.reader.entity.UserBook;
import com.advance.reptile.reader.mapper.UserBookMapper;
import com.advance.reptile.reader.service.IUserBookService;
import com.alibaba.fastjson.JSONArray;
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
 * @since 2019-08-01
 */
@Service
public class UserBookServiceImpl extends ServiceImpl<UserBookMapper, UserBook> implements IUserBookService {

    @Autowired
    private UserBookMapper userBookMapper;

    @Override
    public void saveBookHistory(String bookId, String userId, String chapterId, int num) {
        UserBook userBookData = this.getBookHistpry(bookId, userId);
        if(CommonUtils.objNotEmpty(userBookData)){ //存在就更新章节数 TODO:后期改为redis修改然后通过消息队列同步到mysql
            userBookData.setChapterNum(num);
            userBookData.setChapterId(chapterId);
            super.updateById(userBookData);
            return;
        }
        UserBook userBook = new UserBook();
        String uuid = CommonUtils.getUuid();
        userBook.setId(uuid);
        userBook.setBookId(bookId);
        userBook.setUserId(userId);
        userBook.setChapterNum(num);
        userBook.setChapterId(chapterId);
        super.save(userBook);
    }

    @Override
    public UserBook getBookHistpry(String bookId, String userId) {
        QueryWrapper<UserBook> queryWrapper = new QueryWrapper();
        queryWrapper.eq("book_id",bookId);
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("status", SysConstant.DATA_STATUS_VALID);
        return super.list(queryWrapper).size() > 0 ? super.list(queryWrapper).get(0) : null;
    }

    @Override
    public List<Map<String, Object>> getBookShelfDatas(String userId) {
        return userBookMapper.getBookShelfDatas(userId);
    }

    @Override
    public void delUserBook(String ids) {
        JSONArray idListJson = JSONArray.parseArray(ids);
        List<String> idList = idListJson.toJavaList(String.class);
        idList.forEach(id -> {
            UserBook userBook = new UserBook();
            userBook.setId(id);
            userBook.setStatus(SysConstant.DATA_STATUS_INVALID);
            super.updateById(userBook);
        });
    }
}
