package com.xiaoke.alog.mapper;

import com.xiaoke.alog.entity.User;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {
    /**
     * 存储用户信息
     */
    void insertUser(User user);

    /**
     * 查询昵称是否存在
     * @return true 存在
     */
    Boolean accountExist(String account);

    /**
     * 通过账号查找
     * @param account 账号
     */
    User selectByAccount(String account);
}
