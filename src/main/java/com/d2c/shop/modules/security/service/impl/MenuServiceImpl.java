package com.d2c.shop.modules.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.d2c.shop.modules.security.mapper.MenuMapper;
import com.d2c.shop.modules.security.model.MenuDO;
import com.d2c.shop.modules.security.service.MenuService;
import org.springframework.stereotype.Service;

/**
 * @author BaiCai
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuDO> implements MenuService {

}
