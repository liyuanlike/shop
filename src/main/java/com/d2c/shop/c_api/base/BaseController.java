package com.d2c.shop.c_api.base;

import com.d2c.shop.c_api.holder.LoginMemberHolder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Cai
 */
public abstract class BaseController {

    @Autowired
    public LoginMemberHolder loginMemberHolder;

}
