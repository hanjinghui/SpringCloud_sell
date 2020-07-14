package com.java.order.message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.java.common.exception.SellException;
import com.java.order.entity.OrderDetail;
import com.java.order.entity.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jiangli
 * @date 2019/8/26 21:24
 */
@Component
@Slf4j
public class ProductInfoReceiver {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@RabbitListener(queuesToDeclare = @Queue("productInfo"))
	public void process(String message) {
		List<ProductInfo> productInfos;
		try {
			productInfos = new Gson().fromJson(message, new TypeToken<List<ProductInfo>>() {}.getType());
			for (ProductInfo productInfo : productInfos) {
				//将库存存入redis
				stringRedisTemplate.opsForValue().set("productId_"+productInfo.getProductId(), String.valueOf(productInfo.getProductStock()));
			}
		} catch (Exception e) {
			log.error("【json转换】错误, message={}", message);
			throw new SellException("Converter转换出错");
		}

	}

}
