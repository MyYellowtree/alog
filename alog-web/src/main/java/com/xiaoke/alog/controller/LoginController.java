package com.xiaoke.alog.controller;

import com.xiaoke.alog.common.ParameterVerifier;
import com.xiaoke.alog.common.Result;
import com.xiaoke.alog.controller.request.LoginRq;
import com.xiaoke.alog.controller.request.RegisterRq;
import com.xiaoke.alog.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/user/register")
    public Result register(@RequestBody @Validated RegisterRq registerRq,
                           BindingResult bindingResult) {
        ParameterVerifier.verify(bindingResult);

        return loginService.register(registerRq.getAccount(),
                registerRq.getPassword(),
                registerRq.getNickname());
    }

    @PostMapping("/user/login")
    public Result login(@RequestBody @Validated LoginRq loginRq,
                        BindingResult bindingResult) {
        ParameterVerifier.verify(bindingResult);

        return loginService.login(loginRq.getAccount(), loginRq.getPassword());
    }
}
