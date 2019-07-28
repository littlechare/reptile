package com.advance.reptile.reader.service.impl;

import com.advance.reptile.reader.entity.User;
import com.advance.reptile.reader.mapper.UserMapper;
import com.advance.reptile.reader.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2019-07-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
