package com.d2c.shop.c_api;

import com.d2c.shop.c_api.base.C_BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cai
 */
@Api(description = "拼团业务")
@RestController
@RequestMapping("/c_api/crowd_group")
public class C_CrowdGroupController extends C_BaseController {

}
