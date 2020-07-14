package com.java.order.dao;

import com.java.order.entity.OrderDetail;
import com.java.order.entity.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jiangli
 * @date 2019/8/19 21:50
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String>{
}
