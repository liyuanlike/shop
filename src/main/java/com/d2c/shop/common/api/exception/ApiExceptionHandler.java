package com.d2c.shop.common.api.exception;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.d2c.shop.common.api.Response;
import com.d2c.shop.common.api.ResultCode;
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
        if (e.getErrorCode() == null) {
            return Response.failed(ResultCode.SERVER_EXCEPTION, e.getMessage());
        }
        return Response.failed(e.getErrorCode());
    }

}
