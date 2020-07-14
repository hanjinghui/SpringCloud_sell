package com.java.user.service.impl;

import com.java.user.dao.UserInfoRepository;
import com.java.user.entity.UserInfo;
import com.java.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jiangli
 * @date 2019/8/27 21:29
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	private UserInfoRepository userInfoRepository;

	@Override
	public UserInfo findByOpenid(String openid) {
		return userInfoRepository.findByOpenid(openid);
	}
}
