package com.d2c.shop.modules.security.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.common.api.base.BaseDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author BaiCai
 */
@Data
@TableName("sys_role_menu")
public class RoleMenuDO extends BaseDO {

    @ApiModelProperty(value = "角色ID")
    private Long roleId;
    @ApiModelProperty(value = "菜单ID")
    private Long menuId;

}
