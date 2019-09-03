package com.advance.reptile.login.service.impl;

import com.advance.reptile.login.entity.User;
import com.advance.reptile.login.mapper.UserMapper;
import com.advance.reptile.login.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2019-08-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    /**
     * 获取用户信息
     * @param openId
     * @return
     */
    @Override
    public User getUserInfo(String openId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("open_id", openId);
        return super.getOne(queryWrapper);
    }
}
