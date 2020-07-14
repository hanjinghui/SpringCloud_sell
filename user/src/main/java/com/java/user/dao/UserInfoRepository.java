package com.java.user.dao;

import com.java.user.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jiangli
 * @date 2019/8/27 21:27
 */
public interface UserInfoRepository extends JpaRepository<UserInfo,String> {

	UserInfo findByOpenid(String openid);
}
