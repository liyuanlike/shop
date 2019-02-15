package com.d2c.shop.modules.core.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.modules.core.model.support.IMemberBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author Cai
 */
@Data
@TableName("core_shopkeeper")
@ApiModel(description = "店铺员工表")
public class ShopkeeperDO extends IMemberBO {

    @ApiModelProperty(value = "店铺ID")
    private Long shopId;
    @ApiModelProperty(value = "手机")
    private String mobile;
    @ApiModelProperty(value = "角色")
    private String role;
    @ApiModelProperty(value = "状态 1,0")
    private Integer status;
    @ApiModelProperty(value = "最后登录")
    private Date loginDate;
    @ApiModelProperty(value = "备注")
    private String remark;

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
