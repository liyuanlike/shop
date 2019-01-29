package com.d2c.shop.common.api.base.spec;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.d2c.shop.common.api.base.BaseDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author BaiCai
 */
@Data
public abstract class BaseDelDO extends BaseDO {

    @TableLogic(value = "0", delval = "1")
    @TableField(value = "deleted", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "逻辑删除标志")
    private Integer deleted;

}
