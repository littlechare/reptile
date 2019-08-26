package com.advance.reptile.login.controller;

import com.advance.reptile.common.Response;
import com.advance.reptile.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * 微信小程序登录控制器
 * @author advance
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/do")
    public Response doLogin(@RequestParam Map<String, Object> param){
        try {
            return Response.success(loginService.doLogin(param));
        } catch (IOException e) {
            e.printStackTrace();
            return Response.failed();
        }
    }

}
