package com.d2c.shop.modules.security.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.d2c.shop.common.api.base.BaseDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author BaiCai
 */
@Data
@TableName("sys_menu")
public class MenuDO extends BaseDO {

    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "路径")
    private String path;
    @ApiModelProperty(value = "类型")
    private Integer type;
    @ApiModelProperty(value = "父级ID")
    private Long parentId;
    @ApiModelProperty(value = "排序")
    private Integer sort;

    public enum TypeEnum {
        DIR, MENU, BUTTON
    }

}
