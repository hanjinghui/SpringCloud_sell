package com.java.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 前置filter
 * @author jiangli
 * @date 2019/8/27 19:51
 */
@Component
public class TokenFilter extends ZuulFilter {
	@Override
	public String filterType() {
		return PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return PRE_DECORATION_FILTER_ORDER - 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		//获取当前上下文
		RequestContext currentContext = RequestContext.getCurrentContext();
		HttpServletRequest request = currentContext.getRequest();
		//这里是从url获取token.如果token放在cookie就从cookie获取
		String token = request.getParameter("token");
		Cookie[] cookies = request.getCookies();
		//判断如果没有携带token就拦截
		if (StringUtils.isEmpty(token)) {
			currentContext.setSendZuulResponse(false);
			currentContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
		}
		return null;
	}
	/*
	* http://localhost:8060/api-p/product/list?token=abc123 能访问
	* http://localhost:8060/api-p/product/list              401
	* */
}
