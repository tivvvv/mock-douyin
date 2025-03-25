package com.tiv.api.interceptor;

import com.tiv.api.controller.BaseController;
import com.tiv.common.constant.Constants;
import com.tiv.common.enums.ResponseStatusEnum;
import com.tiv.common.exception.GraceException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 会话拦截器
 */
public class TokenInterceptor extends BaseController implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader("userId");
        String userToken = request.getHeader("userToken");

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(userToken)) {
            throw new GraceException(ResponseStatusEnum.UN_LOGIN);
        }

        // 获取缓存的会话信息
        String redisToken = redis.get(Constants.USER_TOKEN_PREFIX + userId);
        if (StringUtils.isBlank(redisToken)) {
            throw new GraceException(ResponseStatusEnum.UN_LOGIN);
        }

        if (!redisToken.equalsIgnoreCase(userToken)) {
            throw new GraceException(ResponseStatusEnum.TICKET_INVALID);
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
