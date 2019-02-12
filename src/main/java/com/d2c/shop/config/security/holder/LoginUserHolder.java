package com.d2c.shop.config.security.holder;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @author Cai
 */
@Component
public class LoginUserHolder {

    public static UserDetails getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) return null;
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return user;
    }

    public static String getUsername() {
        if (getLoginUser() == null) return null;
        return getLoginUser().getUsername();
    }

}
