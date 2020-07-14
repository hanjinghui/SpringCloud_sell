package com.java.order.dao;

import com.java.order.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author jiangli
 * @date 2019/8/19 21:50
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail,String>{

	List<OrderDetail> findByOrderId(String orderId);
}
