package com.xiaoke.alog.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResultDTO {
    private Long id;                        // 用户id
    private String account;                 // 登录账号
    private String token;                   // 口令
}
