package com.xiaoke.alog.common;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import javax.validation.ValidationException;

/**
 * Controller参数校验器
 */
public class ParameterVerifier {

    public static void verify(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMsg.append(error.getDefaultMessage())
                        .append('\n');
            }
            throw new ValidationException(errorMsg.toString());
        }
    }
}
