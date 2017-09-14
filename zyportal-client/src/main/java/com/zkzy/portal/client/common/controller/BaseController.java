package com.zkzy.portal.client.common.controller;

import com.zkzy.portal.common.utils.BeanValidators;
import com.zkzy.portal.common.utils.Collections3;
import com.zkzy.portal.common.web.editor.DateEditor;
import com.zkzy.portal.common.web.editor.StringEditor;
import com.zkzy.portal.client.constant.Message;
import com.zkzy.portal.client.constant.ReturnCode;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.exception.base.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制器支持类
 *
 * @author admin
 */
public abstract class BaseController {

    /**
     * 初始化数据绑定
     * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
     * 2. 将字段中Date类型转换为String类型
     *
     * @param binder the binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        // String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
        binder.registerCustomEditor(String.class, new StringEditor());
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new DateEditor());
    }

    /**
     * Handle business exception map.
     *
     * @param ex the ex
     * @return the map
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleBusinessException(BusinessException ex) {
        return makeErrorMessage(ReturnCode.INTERNAL_SERVER_ERROR, "Business Error", ex.getMessage());
    }

    /**
     * Handle constraint violation exception map.
     * hibernate 全局验证方法异常处理
     * @param ex the ex
     * @return the map
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> list = BeanValidators.extractMessage(ex);
        return makeErrorMessage(ReturnCode.INVALID_FIELD, "Invalid Field", Collections3.convertToString(list, ";"));
    }

    /**
     * Handle constraint violation exception map.
     * hibernate 全局验证方法异常处理
     * @param ex the ex
     * @return the map
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, Object> handleAccessDeniedException(AccessDeniedException ex){
        return makeErrorMessage(com.zkzy.zyportal.system.api.constant.ReturnCode.NO_AUTHORITY,ex.getMessage());
    }

    /**
     * Make error message map.
     *
     * @param code  the code
     * @param error the error
     * @param desc  the desc
     * @return the map
     */
    protected Map<String, Object> makeErrorMessage(String code, String error, String desc) {
        Map<String, Object> message = new HashMap<>();
        message.put(Message.RETURN_FIELD_CODE, code);
        message.put(Message.RETURN_FIELD_ERROR, error);
        message.put(Message.RETURN_FIELD_ERROR_DESC, desc);
        return message;
    }

    protected Map<String, Object> makeErrorMessage(CodeObject ro, String desc) {
        Map<String, Object> message = new HashMap<>();
        message.put(Message.RETURN_FIELD_CODE,ro.getCode());
        message.put(Message.RETURN_FIELD_ERROR,desc);
        message.put(Message.RETURN_FIELD_ERROR_DESC, ro.getDesc());
        return message;
    }

    public static Map<String,Object> makeMessage(CodeObject ro){
        Map<String, Object> codeMap=new HashMap<String,Object>();
        codeMap.put(Message.RETURN_FIELD_CODE,ro.getCode());
        codeMap.put(Message.RETURN_FIELD_CODE_DESC,ro.getDesc());
        return  codeMap;
    }

    public static Map<String,Object> makeMessage(CodeObject ro,Object o){
        Map<String, Object> codeMap=new HashMap<String,Object>();
        codeMap.put(Message.RETURN_FIELD_CODE,ro.getCode());
        codeMap.put(Message.RETURN_FIELD_CODE_DESC,ro.getDesc());
        codeMap.put(Message.RETURN_FIELD_DATA,o);
        return  codeMap;
    }

}
