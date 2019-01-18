package com.d2c.shop.config.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.d2c.shop.common.constant.FieldConstant;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author BaiCai
 */
@Component
public class ModelMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Object createDate = this.getFieldValByName(FieldConstant.CREATE_DATE, metaObject);
        if (null == createDate) {
            this.setFieldValByName(FieldConstant.CREATE_DATE, new Date(), metaObject);
        }
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Object createMan = this.getFieldValByName(FieldConstant.CREATE_MAN, metaObject);
        if (null == createMan && user != null) {
            this.setFieldValByName(FieldConstant.CREATE_MAN, user.getUsername(), metaObject);
        }
        Object deleted = this.getFieldValByName(FieldConstant.DELETED, metaObject);
        if (null == deleted) {
            this.setFieldValByName(FieldConstant.DELETED, new Integer(0), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object modifyDate = this.getFieldValByName(FieldConstant.MODIFY_DATE, metaObject);
        this.setFieldValByName(FieldConstant.MODIFY_DATE, new Date(), metaObject);
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Object modifyMan = this.getFieldValByName(FieldConstant.MODIFY_MAN, metaObject);
        if (null == modifyMan && user != null) {
            this.setFieldValByName(FieldConstant.MODIFY_MAN, user.getUsername(), metaObject);
        }
    }

}
