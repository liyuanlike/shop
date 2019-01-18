package com.d2c.shop.config.security.handler;

import com.d2c.shop.common.api.ErrorCode;
import com.d2c.shop.common.api.Response;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author BaiCai
 */
@Component
public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof UsernameNotFoundException || exception instanceof BadCredentialsException) {
            Response.out(response, Response.failed(ErrorCode.SERVER_EXCEPTION, "账号或密码错误"));
        } else if (exception instanceof DisabledException) {
            Response.out(response, Response.failed(ErrorCode.SERVER_EXCEPTION, "账号被禁用"));
        } else {
            Response.out(response, Response.failed(ErrorCode.SERVER_EXCEPTION));
        }
    }

}
