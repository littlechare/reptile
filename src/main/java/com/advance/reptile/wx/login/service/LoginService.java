package com.advance.reptile.wx.login.service;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.Map;

public interface LoginService {

    /**
     * 获取openid以及session_key
     * @param code
     * @return
     */
    JSONObject code2Session(String code) throws IOException;

    /**
     * 对接微信登录
     * @param param
     */
    Map<String, Object> doLogin(Map<String, Object> param) throws IOException;

}
