package com.d2c.shop.modules.member.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.modules.member.model.support.IAddressBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Cai
 */
@Data
@TableName("m_address")
@ApiModel(description = "地址表")
public class AddressDO extends IAddressBO {

    @ApiModelProperty(value = "默认")
    private Integer defaults;

}
