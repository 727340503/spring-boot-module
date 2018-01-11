package com.cherrypicks.tcc.cms.api.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cherrypicks.tcc.cms.api.annotation.UserMerchanVerifyAnno;
import com.cherrypicks.tcc.cms.exception.ForbiddenException;
import com.cherrypicks.tcc.cms.system.service.IAuthorizeService;
import com.cherrypicks.tcc.util.Constants;

public class UserMerchantVerifyInterceptor extends HandlerInterceptorAdapter {

    private final Log logger = LogFactory.getLog(this.getClass());
    
    @Autowired
    private IAuthorizeService authorizeService;
    
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        logger.debug("PermissionCheckInterceptor.preHandle run....");
        
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        
        final HandlerMethod handlerMethod = (HandlerMethod) handler;
        final Method method = handlerMethod.getMethod();

        // 判断接口是否需要校验
        final UserMerchanVerifyAnno methodAnnotation = method.getAnnotation(UserMerchanVerifyAnno.class);

        if(methodAnnotation != null) {
        	final Long userId = Long.parseLong(request.getParameter(Constants.USERID));	
        	final String merchantIdStr = request.getParameter(Constants.MERCHANT_ID);
        	final String lang = request.getParameter(Constants.LANG);
        	
        	Long merchantId = null;
        	if(StringUtils.isNotBlank(merchantIdStr)) {
        		merchantId = Long.parseLong(merchantIdStr);
        	}
        	
        	final boolean flag = authorizeService.checkUserMerchantPermission(userId, merchantId, lang);
        	
        	if(flag) {
        		return true;
        	}
        	
        	throw new ForbiddenException();
        }

        return true;
    }
    
}
