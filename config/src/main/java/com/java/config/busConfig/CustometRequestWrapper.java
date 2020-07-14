package com.java.config.busConfig;

import java.io.ByteArrayInputStream;
import java.io.IOException;
 
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
 
/**
 * 解决使用spring cloud config bus使用webhook自动刷新出现的400问题
 *
 * @author jiangli
 * @date 2019/8/19 22:55
 */
public class CustometRequestWrapper extends HttpServletRequestWrapper {
 
	public CustometRequestWrapper(HttpServletRequest request) {
		super(request);
	}
 
	@Override
	public ServletInputStream getInputStream() throws IOException {
		byte[] bytes = new byte[0];
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
 
		return new ServletInputStream() {
			@Override
			public boolean isFinished() {
				return byteArrayInputStream.read() == -1 ? true : false;
			}
 
			@Override
			public boolean isReady() {
				return false;
			}
 
			@Override
			public void setReadListener(ReadListener readListener) {
 
			}
 
			@Override
			public int read() throws IOException {
				return byteArrayInputStream.read();
			}
		};
	}
 
}