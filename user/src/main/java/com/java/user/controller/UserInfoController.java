package com.java.user.controller;

import com.java.common.VO.ResultVO;
import com.java.common.constants.Constants;
import com.java.common.util.CookieUtils;
import com.java.common.util.RedisUtil;
import com.java.user.entity.UserInfo;
import com.java.user.service.UserInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author jiangli
 * @date 2019/8/27 21:30
 */
@RestController
@RequestMapping("/login")
public class UserInfoController {
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private RedisUtil redisUtil;

	@GetMapping("/buyer")
	public ResultVO<UserInfo> buyer(@RequestParam String openid, HttpServletRequest request, HttpServletResponse response) {
		UserInfo userInfo = userInfoService.findByOpenid(openid);
		if (userInfo == null) {
			return ResultVO.error("登录失败,用户不存在");
		}
		//1买家2卖家
		if (userInfo.getRole() != 1) {
			return ResultVO.error("登录失败,用户角色异常");
		}
		//验证通过,,设置cookie, key='openid',value=uuid
		CookieUtils.setCookie(request,response, Constants.BUYER_COOKIE_NAME,openid,7200);
		return ResultVO.ok();
	}

	@GetMapping("/seller")
	public ResultVO<UserInfo> seller(@RequestParam String openid, HttpServletRequest request, HttpServletResponse response) {
		//判断是否已经登录
		String cookieValue = CookieUtils.getCookieValue(request, Constants.SELLER_COOKIE_NAME);
		if (StringUtils.isNotEmpty(cookieValue) && StringUtils.isNotEmpty(redisUtil.getString(cookieValue))) {
			//已经登录,返回成功
			return ResultVO.ok();
		}

		UserInfo userInfo = userInfoService.findByOpenid(openid);
		if (userInfo == null) {
			return ResultVO.error("登录失败,用户不存在");
		}
		//1买家2卖家
		if (userInfo.getRole() != 2) {
			return ResultVO.error("登录失败,用户角色异常");
		}
		//设置redis,key=uuid,value=openid
		String uuid = UUID.randomUUID().toString();
		redisUtil.setString(uuid,openid,7200L);
		//设置cookie, key='token',value=uuid
		CookieUtils.setCookie(request,response,Constants.SELLER_COOKIE_NAME,uuid,7200);
		return ResultVO.ok();
	}
}
