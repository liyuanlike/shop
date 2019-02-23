package com.d2c.shop.modules.product.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.common.api.base.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BaiCai
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("p_product_detail")
@ApiModel(description = "商品详情表")
public class ProductDetailDO extends BaseDO {

    @ApiModelProperty(value = "店铺ID")
    private Long shopId;
    @ApiModelProperty(value = "商品ID")
    private Long productId;
    @ApiModelProperty(value = "内容")
    private String content;

}
