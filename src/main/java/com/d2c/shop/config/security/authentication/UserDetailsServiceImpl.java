package com.d2c.shop.config.security.authentication;

import com.d2c.shop.common.utils.SpringUtil;
import com.d2c.shop.modules.security.model.UserDO;
import com.d2c.shop.modules.security.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author BaiCai
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDO user = SpringUtil.getBean(UserService.class).findByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("该账号不存在，请重新确认");
        }
        return new SecurityUserDetails(user);
    }

}
