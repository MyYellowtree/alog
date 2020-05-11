package com.xiaoke.alog.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xiaoke.alog.controller.annotation.TokenVerify;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 直接响应 HTTP OPTIONS 请求
        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
            response.setStatus(HttpStatus.OK.value());
            return true;
        }

        // 登录拦截
        return loginVerify(request, handler);
    }

    private boolean loginVerify(HttpServletRequest request, Object handler) {
        if (!(handler instanceof HandlerMethod))
            return true;

        // 判断访问的方法是否需要登录
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (!method.isAnnotationPresent(TokenVerify.class)) // 不需要登录就直接放行
            return true;
        TokenVerify tokenVerify = method.getAnnotation(TokenVerify.class);
        if (!tokenVerify.required()) // 不需要登录就直接放行
            return true;

        // 获取传过来的Token
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token))
            return false;

        // 通过userId查询redis中该用户的Token
        DecodedJWT decodedJWT = JWT.decode(token);
        Long id = decodedJWT.getClaim("id").asLong();
        String userTokenSecret = stringRedisTemplate.opsForValue().get(id.toString());
        if (StringUtils.isBlank(userTokenSecret))
            return false;

        // 验证Token
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(userTokenSecret))
                .build()
                .verify(token);
        return true;
    }
}
