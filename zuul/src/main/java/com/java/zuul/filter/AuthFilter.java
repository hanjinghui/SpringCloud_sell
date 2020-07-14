package com.java.zuul.filter;

import com.java.common.constants.Constants;
import com.java.common.util.CookieUtils;
import com.java.common.util.RedisUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVLET_DETECTION_FILTER_ORDER;

/**
 * 权限拦截(区分买家和卖家)
 * @author jiangli
 * @date 2019/8/27 23:29
 */
@Component
public class AuthFilter extends ZuulFilter {
	@Autowired
	private RedisUtil redisUtil;

	@Override
	public String filterType() {
		return PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return SERVLET_DETECTION_FILTER_ORDER - 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		//获取当前上下文
		RequestContext currentContext = RequestContext.getCurrentContext();
		HttpServletRequest request = currentContext.getRequest();
		HttpServletResponse response = currentContext.getResponse();
		/**
		 * /order/create  只能买家访问(cookie中有openid)
		 * /order/finish  只能卖家访问(cookie中有token,并且对应的redis有值)
		 */
		if (request.getRequestURI().contains("/order/create")) {
			String cookieValue = CookieUtils.getCookieValue(request, Constants.BUYER_COOKIE_NAME);
			if (StringUtils.isEmpty(cookieValue)) {
				currentContext.setSendZuulResponse(false);
				currentContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
			}
		}

		if (request.getRequestURI().contains("/order/finish")) {
			String cookieValue = CookieUtils.getCookieValue(request, Constants.SELLER_COOKIE_NAME);
			if (StringUtils.isEmpty(cookieValue)|| StringUtils.isEmpty(redisUtil.getString(cookieValue))) {
				currentContext.setSendZuulResponse(false);
				currentContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
			}
		}
		return null;
	}
}
