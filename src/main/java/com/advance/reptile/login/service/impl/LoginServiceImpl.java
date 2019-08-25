package com.advance.reptile.login.service.impl;

import com.advance.reptile.common.CommonUtils;
import com.advance.reptile.common.SysConstant;
import com.advance.reptile.login.constant.LoginConstant;
import com.advance.reptile.login.entity.User;
import com.advance.reptile.login.service.IUserService;
import com.advance.reptile.login.service.LoginService;
import com.advance.reptile.login.utils.LoginUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录服务层
 * @author advance
 */
@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    private IUserService userService;

    /**
     * 调用微信认证中心接口，使用微信返回的code获取openid以及session_key
     * @param code
     * @return
     * @throws IOException
     */
    @Override
    public JSONObject code2Session(String code) throws IOException {
        Map<String, Object> mapParam = new HashMap<String, Object>();
        mapParam.put("appid", LoginConstant.APP_ID);
        mapParam.put("secret",LoginConstant.APP_SECRET);
        mapParam.put("js_code",code);
        mapParam.put("grant_type","authorization_code");
        String pathUrl = LoginConstant.WX_SESSION_URL;
        String result = LoginUtils.postJson(pathUrl, mapParam);
        return JSONObject.parseObject(result);
    }

    /**
     * 微信登录
     * @param param
     * @throws IOException
     */
    @Override
    public void doLogin(Map<String, Object> param) throws IOException {
        String code = CommonUtils.hanldNull(param.get("code"));
        JSONObject resultData = this.code2Session(code);
        String openId = resultData.getString("openid");
        String session_key = resultData.getString("session_key");
        User user = userService.getUserInfo(openId);
        if(CommonUtils.objNotEmpty(user)){
            userService.getUserInfo(openId);
        }else{
            String uuid = CommonUtils.getUuid();
            User userSave = new User();
            userSave.setId(uuid);
            userSave.setName(CommonUtils.hanldNull(param.get("nickName")));
            userSave.setNickName(CommonUtils.hanldNull(param.get("nickName")));
            userSave.setSex(CommonUtils.hanldNull(param.get("sex")));
            userSave.setRegisterTime(LocalDateTime.now());
            userSave.setDataStatus(SysConstant.DATA_STATUS_VALID);
            userSave.setOpenId(openId);
            userSave.setAvaUrl(CommonUtils.hanldNull(param.get("imgUrl")));
            userSave.setCountry(CommonUtils.hanldNull(param.get("country")));
            userSave.setProvince(CommonUtils.hanldNull(param.get("province")));
            userSave.setCity(CommonUtils.hanldNull(param.get("city")));
            userSave.setSessionKey(session_key);
            userService.save(userSave);
        }
    }
}
