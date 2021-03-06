package com.d2c.shop.modules.security.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.common.api.base.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BaiCai
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_user_role")
@ApiModel(description = "用户角色关系表")
public class UserRoleDO extends BaseDO {

    @ApiModelProperty(value = "用户ID")
    private Long userId;
    @ApiModelProperty(value = "角色ID")
    private Long roleId;

}
