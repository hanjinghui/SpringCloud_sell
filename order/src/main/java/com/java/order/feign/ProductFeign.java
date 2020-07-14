package com.java.order.feign;

import com.java.order.dto.CartDTO;
import com.java.order.entity.ProductInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author jiangli
 * @date 2019/8/20 20:24
 */
@FeignClient(name = "product")
public interface ProductFeign {

	@PostMapping("/product/getListByIds")
	List<ProductInfo> getListByIds(@RequestBody List<String> productIds);

	@PostMapping("/product/decreaseStock")
	void decreaseStock(@RequestBody List<CartDTO> cartDTOList);
}
