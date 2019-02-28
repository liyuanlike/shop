package com.d2c.shop.modules.product.query;

import com.d2c.shop.common.api.annotation.Condition;
import com.d2c.shop.common.api.base.BaseQuery;
import com.d2c.shop.common.api.emuns.ConditionEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BaiCai
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductQuery extends BaseQuery {

    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "店铺ID")
    private Long shopId;
    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "状态")
    private Integer status;
    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "类目ID")
    private Long categoryId;
    @Condition(condition = ConditionEnum.IN, field = "category_id")
    @ApiModelProperty(value = "类目ID")
    private Long[] categoryIds;
    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "拼团 1,0")
    private Integer crowd;

}
