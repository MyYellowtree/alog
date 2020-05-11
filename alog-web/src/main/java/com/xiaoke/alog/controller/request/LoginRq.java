package com.xiaoke.alog.controller.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 登录请求
 */
@Data
public class LoginRq {
    @NotNull(message = "用户名不能为空！")
    private String account;
    @NotNull(message = "密码不能为空！")
    private String password;
}
