package com.d2c.shop.common.api.exception;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.d2c.shop.common.api.ErrorCode;
import com.d2c.shop.common.api.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Cai
 */
@ControllerAdvice
public class ApiExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public R handle(ApiException e) {
        return Response.failed(ErrorCode.SERVER_EXCEPTION, e.getMessage());
    }

}
