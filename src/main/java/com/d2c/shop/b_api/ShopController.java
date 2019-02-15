package com.d2c.shop.b_api;

import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.extension.api.R;
import com.d2c.shop.c_api.base.BaseController;
import com.d2c.shop.common.api.Asserts;
import com.d2c.shop.common.api.ErrorCode;
import com.d2c.shop.common.api.Response;
import com.d2c.shop.config.security.constant.SecurityConstant;
import com.d2c.shop.modules.core.model.ShopkeeperDO;
import com.d2c.shop.modules.core.service.ShopkeeperService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author Cai
 */
@Api(description = "店铺业务")
@RestController
@RequestMapping("/b_api/shop")
public class ShopController extends BaseController {


}
