package com.d2c.shop.b_api.base;

import com.d2c.shop.config.security.context.LoginKeeperHolder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Cai
 */
public abstract class BaseController {

    @Autowired
    public LoginKeeperHolder loginKeeperHolder;

}
