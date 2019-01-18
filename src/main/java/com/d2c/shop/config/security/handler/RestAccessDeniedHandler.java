package com.d2c.shop.config.security.handler;

import com.d2c.shop.common.api.ErrorCode;
import com.d2c.shop.common.api.Response;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author BaiCai
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        Response.out(httpServletResponse, Response.failed(ErrorCode.ACCESS_DENIED));
    }

}
