package com.java.order.service.impl;

import com.java.common.exception.SellException;
import com.java.common.util.IdWorker;
import com.java.order.dao.OrderDetailRepository;
import com.java.order.dao.OrderMasterRepository;
import com.java.order.dto.CartDTO;
import com.java.order.dto.OrderDTO;
import com.java.order.entity.OrderDetail;
import com.java.order.entity.OrderMaster;
import com.java.order.entity.ProductInfo;
import com.java.order.enums.OrderStatusEnum;
import com.java.order.enums.PayStatusEnum;
import com.java.order.feign.ProductFeign;
import com.java.order.service.OrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author jiangli
 * @date 2019/8/19 22:06
 */
@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private IdWorker idWorker;
	@Autowired
	private OrderMasterRepository orderMasterRepository;
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	@Autowired
	private ProductFeign productFeign;

	/**
	 * 创建订单
	 * @param orderDTO
	 * @return
	 */
	@Override
	@Transactional
	//超时配置
	@HystrixCommand(fallbackMethod = "fallback",commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")})
	public OrderDTO create(OrderDTO orderDTO) {
		String orderId = String.valueOf(idWorker.nextId());
		//查询商品信息
		List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
		List<String> productIds = orderDetailList.stream().map(OrderDetail::getProductId).collect(Collectors.toList());
		List<ProductInfo> productInfoList = productFeign.getListByIds(productIds);
		//计算总价
		BigDecimal totalAmount = new BigDecimal(BigInteger.ZERO);
		for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
			for (ProductInfo productInfo : productInfoList) {
				if (productInfo.getProductId().equals(orderDetail.getProductId())) {
					//单价*数量 累加
					totalAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(totalAmount);
					BeanUtils.copyProperties(productInfo,orderDetail);
					orderDetail.setOrderId(orderId);
					orderDetail.setDetailId(String.valueOf(idWorker.nextId()));
					//订单详情入库
					orderDetailRepository.save(orderDetail);
				}
			}
		}
		//扣库存
		List<CartDTO> cartDTOS = orderDTO.getOrderDetailList().stream().map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
		productFeign.decreaseStock(cartDTOS);
		//订单入库
		OrderMaster orderMaster = new OrderMaster();
		orderDTO.setOrderId(orderId);
		BeanUtils.copyProperties(orderDTO,orderMaster);
		orderMaster.setOrderAmount(totalAmount);
		orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
		orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
		orderMasterRepository.save(orderMaster);
		return orderDTO;
	}

	private OrderDTO fallback(OrderDTO orderDTO) {
		OrderDTO o = new OrderDTO();
		o.setOrderId("-1");
		return o;
	}

	@Override
	@Transactional
	public OrderDTO finish(String orderId) {
		//查询订单是否存在
		Optional<OrderMaster> optional = orderMasterRepository.findById(orderId);
		if (!optional.isPresent()) {
			throw new SellException("订单不存在");
		}
		OrderMaster orderMaster = optional.get();
		//判断订单状态,只有状态为新下单的才能完结
		if (orderMaster.getOrderStatus() != 0) {
			throw new SellException("订单状态异常");
		}
		orderMaster.setOrderStatus(1);
		orderMasterRepository.save(orderMaster);

		List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
		if (CollectionUtils.isEmpty(orderDetailList)) {
			throw new SellException("订单性情不存在");
		}
		OrderDTO orderDTO = new OrderDTO();
		BeanUtils.copyProperties(orderMaster,orderDTO);
		orderDTO.setOrderDetailList(orderDetailList);
		return orderDTO;
	}
}
