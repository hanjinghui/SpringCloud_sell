package com.java.product.controller;

import com.java.common.VO.ResultVO;
import com.java.product.VO.ProductVO;
import com.java.product.dto.CartDTO;
import com.java.product.entity.ProductInfo;
import com.java.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jiangli
 * @date 2019/8/18 22:58
 */
@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService productService;

	@GetMapping("/list")
	public ResultVO<List<ProductVO>> list() {
		return productService.list();
	}

	@PostMapping("/getListByIds")
	public List<ProductInfo> getListByIds(@RequestBody List<String> productIds){
		return productService.getListByIds(productIds);
	}

	@PostMapping("/decreaseStock")
	public void decreaseStock(@RequestBody List<CartDTO> cartDTOList){
		productService.decreaseStock(cartDTOList);
	}
}
