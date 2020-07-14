package com.java.zuul.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.java.common.exception.SellException;
import com.netflix.zuul.ZuulFilter;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVLET_DETECTION_FILTER_ORDER;

/**
 * 令牌桶限流
 * @author jiangli
 * @date 2019/8/27 20:27
 */
@Component
public class RatelimitFilter extends ZuulFilter {
	private static final RateLimiter RATE_LIMITER = RateLimiter.create(100);  //每秒100个

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
	public Object run() {
		if (RATE_LIMITER.tryAcquire()) {
			return new SellException("当前排队人数过多,请稍后重试....");
		}
		return null;
	}
}
