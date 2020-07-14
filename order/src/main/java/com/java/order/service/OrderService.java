package com.java.order.service;

import com.java.order.dto.OrderDTO;

/**
 * @author jiangli
 * @date 2019/8/19 22:05
 */
public interface OrderService {

	OrderDTO create(OrderDTO orderDTO);

	OrderDTO finish(String orderId);
}
