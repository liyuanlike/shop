package com.d2c.shop.modules.security.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.common.api.base.extension.BaseDelDO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author BaiCai
 */
@Data
@TableName("sys_user")
@ApiModel(description = "用户表")
public class UserDO extends BaseDelDO {

    @Excel(name = "账号")
    @ApiModelProperty(value = "账号")
    private String username;
    @ApiModelProperty(value = "密码")
    private String password;
    @Excel(name = "状态", replace = {"正常_1", "禁用_0"})
    @ApiModelProperty(value = "状态")
    private Integer status;
    @TableField(exist = false)
    @ApiModelProperty(value = "用户拥有的角色")
    private List<RoleDO> roles = new ArrayList<>();

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

}
