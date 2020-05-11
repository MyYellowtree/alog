package com.xiaoke.alog.controller;

import com.xiaoke.alog.common.Result;
import com.xiaoke.alog.controller.annotation.TokenVerify;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @TokenVerify
    @GetMapping("/hello")
    public Result hello(){
        return Result.ok("hello world!");
    }
}
