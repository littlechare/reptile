package com.advance.reptile.login.service;

import com.advance.reptile.login.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2019-08-25
 */
public interface IUserService extends IService<User> {

    User getUserInfo(String openId);

}
