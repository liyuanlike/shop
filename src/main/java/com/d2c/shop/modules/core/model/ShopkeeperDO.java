package com.d2c.shop.modules.core.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.common.api.base.BaseDO;
import com.d2c.shop.modules.core.model.support.IMember;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author Cai
 */
@Data
@Builder
@TableName("core_shopkeeper")
@ApiModel(description = "店铺员工表")
public class ShopkeeperDO extends BaseDO implements IMember {

    @ApiModelProperty(value = "账号")
    private String account;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "令牌")
    private String accessToken;
    @ApiModelProperty(value = "店铺ID")
    private Long shopId;
    @ApiModelProperty(value = "角色")
    private String role;
    @ApiModelProperty(value = "状态 1,0")
    private Integer status;
    @ApiModelProperty(value = "最后登录")
    private Date loginDate;
    @ApiModelProperty(value = "备注")
    private String remark;

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public String getAccessToken() {
        return accessToken;
    }

    @JsonProperty
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public enum RoleEnum {
        //
        BOSS("店主"), CLERK("店员");
        // 角色描述
        private String description;

        RoleEnum(String description) {
            this.description = description;
        }
    }

}
