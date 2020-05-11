package com.xiaoke.alog.controller.handler;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.xiaoke.alog.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ValidationException;

/**
 * Spring MVC 全局异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Controller参数校验异常
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public Result paramExceptionHandler(ValidationException e) {
        return Result.buildByCode(Result.Code.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(JWTVerificationException.class)
    @ResponseBody
    public Result tokenExceptionHandler(JWTVerificationException e) {
        return Result.buildByCode(Result.Code.BAD_AUTHORIZATION, "Token不合法");
    }
}