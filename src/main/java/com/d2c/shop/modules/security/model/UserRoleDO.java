package com.d2c.shop.modules.security.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.common.api.base.BaseDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author BaiCai
 */
@Data
@TableName("sys_user_role")
public class UserRoleDO extends BaseDO {

    @ApiModelProperty(value = "用户ID")
    private Long userId;
    @ApiModelProperty(value = "角色ID")
    private Long roleId;

}
