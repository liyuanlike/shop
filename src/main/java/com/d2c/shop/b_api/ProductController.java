package com.d2c.shop.b_api;

import com.d2c.shop.b_api.base.BaseControllerB;
import com.d2c.shop.modules.product.service.ProductService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cai
 */
@Api(description = "商品业务")
@RestController
@RequestMapping("/b_api/product")
public class ProductController extends BaseControllerB {

    @Autowired
    private ProductService productService;

}
