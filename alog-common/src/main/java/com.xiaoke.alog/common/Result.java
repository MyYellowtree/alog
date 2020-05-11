package com.xiaoke.alog.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * Controller层通用返回值封装类
 */
@Data
@Builder
public class Result {
    // 状态码
    private int code;
    // 信息
    private String msg;
    // 返回值
    private Object data;

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result ok() {
        return buildByCode(Code.OK, null);
    }

    public static Result ok(Object data) {
        return buildByCode(Code.OK, data);
    }

    /**
     * 根据状态码构建返回值
     *
     * @param code 状态码
     * @param data 返回内容
     */
    public static Result buildByCode(Code code, Object data) {
        return Result.builder()
                .code(code.getCode())
                .msg(code.getMsg())
                .data(data)
                .build();
    }

    // 状态码枚举类
    @Getter
    @AllArgsConstructor
    public enum Code {
        OK(200, "ok"),
        BAD_REQUEST(400, "bad request"),
        BAD_AUTHORIZATION(401, "authorization is wrong");

        public int code;    // 状态码
        public String msg;  // 状态信息
    }
}
