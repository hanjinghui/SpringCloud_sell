package com.java.order.controller;

import com.java.common.VO.ResultVO;
import com.java.common.exception.SellException;
import com.java.order.converter.OrderForm2OrderDTOConverter;
import com.java.order.dto.OrderDTO;
import com.java.order.form.OrderForm;
import com.java.order.service.OrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangli
 * @date 2019/8/19 22:55
 */
@RestController
@RequestMapping("/order")
@Slf4j
@RefreshScope
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Value("${env}")
	private String env;

	@GetMapping("/test")
	public String test() {
		return "hello "+env;
	}

	@PostMapping("/create")
	public ResultVO create(@Valid OrderForm orderForm,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			log.error("创建订单参数不正确,{}",orderForm);
			throw new SellException(bindingResult.getFieldError().getDefaultMessage());
		}
		//orderForm转OrderDTO
		OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
		//判断购物车集合不能为空
		if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
			log.error("创建订单失败,购物车为空");
			throw new SellException("创建订单失败,购物车为空");
		}

		orderDTO = orderService.create(orderDTO);
		Map<String,String> map = new HashMap<>();
		map.put("orderId",orderDTO.getOrderId());
		return ResultVO.ok(map);
	}

	@PostMapping("/finish")
	public ResultVO finish(@RequestParam("orderId") String orderId) {
		OrderDTO orderDTO = orderService.finish(orderId);
		return ResultVO.ok(orderDTO);
	}
}
