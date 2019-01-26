package com.d2c.shop.config.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.d2c.shop.common.api.constant.FieldConstant;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Object createMan = this.getFieldValByName(FieldConstant.CREATE_MAN, metaObject);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null == createMan && !(authentication instanceof AnonymousAuthenticationToken)) {
            this.setFieldValByName(FieldConstant.CREATE_MAN, authentication.getPrincipal(), metaObject);
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
        Object modifyMan = this.getFieldValByName(FieldConstant.MODIFY_MAN, metaObject);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null == modifyMan && !(authentication instanceof AnonymousAuthenticationToken)) {
            this.setFieldValByName(FieldConstant.MODIFY_MAN, authentication.getPrincipal(), metaObject);
        }
    }

}
