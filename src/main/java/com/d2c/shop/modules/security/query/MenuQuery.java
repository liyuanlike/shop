package com.d2c.shop.modules.security.query;

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
public class MenuQuery extends BaseQuery {

    @Condition(condition = ConditionEnum.LIKE)
    @ApiModelProperty(value = "名称")
    private String name;
    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "类型")
    private Integer type;
    @Condition(condition = ConditionEnum.EQ)
    @ApiModelProperty(value = "父级ID")
    private Long parentId;

}
