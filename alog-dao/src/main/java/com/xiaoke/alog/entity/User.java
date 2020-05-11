package com.xiaoke.alog.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class User {
    private Long id;                        // 用户id
    private String account;                 // 登录账号
    private String password;                // 密码
    private String avatar;                  // 头像图片url
    private String nickname;                // 昵称
    private Integer sex;                    // 性别：0 女，1 男
    private Date birthday;                  // 生日
    private String profile;                 // 用户简介
    private Integer countSubscriptions;     // 关注数
    private Integer countFans;              // 粉丝数
}
