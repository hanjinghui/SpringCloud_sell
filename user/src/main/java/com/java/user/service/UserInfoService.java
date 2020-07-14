package com.java.user.service;

import com.java.user.entity.UserInfo;

/**
 * @author jiangli
 * @date 2019/8/27 21:29
 */
public interface UserInfoService {

	UserInfo findByOpenid(String openid);
}
