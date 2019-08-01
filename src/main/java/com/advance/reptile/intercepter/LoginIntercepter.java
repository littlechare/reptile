package com.advance.reptile.intercepter;

import com.advance.reptile.common.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName LoginIntercepter
 * @Description TODO 登录拦截器，暂时未处理用户信息
 * @Author zhouh
 * @Date 2019/8/1 15:46
 * @Version V1.0
 **/
public class LoginIntercepter implements HandlerInterceptor {

    Logger logger = Logger.getLogger(LoginIntercepter.class);

    /*
     * Controller方法调用前，返回true表示继续处理
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HandlerMethod method = (HandlerMethod) handler;
        return true;
    }

    /*
     * Controller方法调用后，视图渲染前
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        HandlerMethod method = (HandlerMethod) handler;
        response.getOutputStream().write("append content".getBytes());
    }

    /*
     * 整个请求处理完，视图已渲染。如果存在异常则Exception不为空
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        HandlerMethod method = (HandlerMethod) handler;
        logger.info(method.getMethod().getName());
    }

}
