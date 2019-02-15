package com.d2c.shop.modules.member.model;

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
@TableName("m_member")
@ApiModel(description = "会员表")
public class MemberDO extends IMemberBO {

    @ApiModelProperty(value = "状态 1,0")
    private Integer status;
    @ApiModelProperty(value = "最后登录")
    private Date loginDate;
    @ApiModelProperty(value = "QQ昵称")
    private String qqNickname;
    @ApiModelProperty(value = "QQ头像")
    private String qqAvatar;
    @ApiModelProperty(value = "QQ的OpenId")
    private String qqOpenId;
    @ApiModelProperty(value = "微信昵称")
    private String wechatNickname;
    @ApiModelProperty(value = "微信头像")
    private String wechatAvatar;
    @ApiModelProperty(value = "微信的OpenId")
    private String wechatOpenId;
    @ApiModelProperty(value = "微信的UnionId")
    private String wechatUnionId;

}
