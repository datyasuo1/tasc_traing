package com.tass.apigw.utils.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.tass.apigw.security.UserDetailExtend;
import com.tass.common.model.constans.AUTHENTICATION;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ZuulBeforeFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 100000;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        UserDetailExtend userDetailExtend = getUseDetail();

        if (userDetailExtend != null){
            ctx.addZuulRequestHeader(AUTHENTICATION.HEADER.USER_ID, String.valueOf(userDetailExtend.getUserId()));
        }
        return null;
    }

    UserDetailExtend getUseDetail() {
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            if (context == null) return null;
            Authentication authentication = context.getAuthentication();
            if (authentication == null) return null;
            return (UserDetailExtend) authentication.getPrincipal();

        } catch (Exception e) {
            return null;
        }


    }


}
