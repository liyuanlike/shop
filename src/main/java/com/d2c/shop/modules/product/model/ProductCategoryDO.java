package com.d2c.shop.modules.product.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.common.api.base.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author BaiCai
 */
@Data
@Builder
@TableName("p_product_category")
@ApiModel(description = "商品类目表")
public class ProductCategoryDO extends BaseDO {

    @ApiModelProperty(value = "店铺ID")
    private Long shopId;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "父级ID")
    private Long parentId;
    @ApiModelProperty(value = "级别")
    private Integer level;
    @ApiModelProperty(value = "排序")
    private Integer sort;

}
