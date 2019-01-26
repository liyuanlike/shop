package com.d2c.shop.modules.security.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.common.api.base.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author BaiCai
 */
@Data
@TableName("sys_role")
@ApiModel(description = "角色表")
public class RoleDO extends BaseDO {

    @ApiModelProperty(value = "ROLE_开头")
    private String code;
    @ApiModelProperty(value = "名称")
    private String name;

}
