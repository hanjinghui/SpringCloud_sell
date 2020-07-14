package com.java.product.service;

import com.java.common.VO.ResultVO;
import com.java.product.VO.ProductVO;
import com.java.product.dto.CartDTO;
import com.java.product.entity.ProductInfo;

import java.util.List;

/**
 * @author jiangli
 * @date 2019/8/19 19:13
 */
public interface ProductService {

	ResultVO<List<ProductVO>> list();

	List<ProductInfo> getListByIds(List<String> productIds);

	void decreaseStock(List<CartDTO> cartDTOList);
}
