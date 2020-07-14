package com.java.config.busConfig;

import java.io.IOException;
 
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
 

/**
 * @author jiangli
 * @date 2019/8/19 22:55
 */
@WebFilter(filterName = "JsonFilter",urlPatterns = "/*")
public class JsonFilter implements Filter {
 
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
 
	}
 
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
 
		String url = new String(httpServletRequest.getRequestURI());
 
		// 只过滤/actuator/bus-refresh请求
		if (!url.endsWith("/bus-refresh")) {
			filterChain.doFilter(servletRequest, servletResponse);
			return;
		}
 
		// 使用HttpServletRequest包装原始请求达到修改post请求中body内容的目的
		CustometRequestWrapper requestWrapper = new CustometRequestWrapper(httpServletRequest);
 
		filterChain.doFilter(requestWrapper, servletResponse);
	}
 
	@Override
	public void destroy() {
 
	}
 
}