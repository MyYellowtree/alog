package com.xiaoke.alog.service;

import com.xiaoke.alog.common.Result;

public interface LoginService {

    /**
     * 用户注册
     * @param account 登录账号
     * @param password 密码
     * @param nickname 昵称
     */
    Result register(String account, String password, String nickname);

    /**
     * 用户登录
     * @param account 账号
     * @param password 密码
     */
    Result login(String account, String password);
}
