package com.d2c.shop.b_api.base;

import com.d2c.shop.b_api.holder.LoginKeeperHolder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Cai
 */
public abstract class B_BaseController {

    @Autowired
    public LoginKeeperHolder loginKeeperHolder;

}
