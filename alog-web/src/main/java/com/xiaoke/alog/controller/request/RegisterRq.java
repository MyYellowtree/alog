package com.xiaoke.alog.controller.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 用户注册请求
 */
@Data
public class RegisterRq {
    @NotNull(message = "用户名不能为空！")
    private String account;
    @NotNull(message = "密码不能为空！")
    private String password;
    @NotNull(message = "昵称不能为空")
    private String nickname;
}
