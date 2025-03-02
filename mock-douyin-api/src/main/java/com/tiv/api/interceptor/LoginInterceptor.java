package com.tiv.api.interceptor;

import com.tiv.api.controller.BaseController;
import com.tiv.common.constant.Constants;
import com.tiv.common.enums.ResponseStatusEnum;
import com.tiv.common.exception.GraceException;
import com.tiv.service.utils.IPUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 */
public class LoginInterceptor extends BaseController implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = IPUtil.getRequestIp(request);
        boolean keyExisted = redis.keyIsExist(Constants.IP_PREFIX + ip);
        if (keyExisted) {
            throw new GraceException(ResponseStatusEnum.SMS_NEED_WAIT_ERROR);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
