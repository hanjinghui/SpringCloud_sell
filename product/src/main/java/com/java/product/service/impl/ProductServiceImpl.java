package com.java.product.service.impl;

import com.google.gson.Gson;
import com.java.common.VO.ResultVO;
import com.java.common.exception.SellException;
import com.java.product.VO.ProductInfoVO;
import com.java.product.VO.ProductVO;
import com.java.product.dao.ProductCategoryRepository;
import com.java.product.dao.ProductInfoRepository;
import com.java.product.dto.CartDTO;
import com.java.product.entity.ProductCategory;
import com.java.product.entity.ProductInfo;
import com.java.product.enums.ProductStatusEnum;
import com.java.product.enums.ResultEnum;
import com.java.product.service.ProductService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author jiangli
 * @date 2019/8/19 19:15
 */
@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductInfoRepository productInfoRepository;
	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Override
	public ResultVO<List<ProductVO>> list() {
		//1.查询所有上架的商品
		List<ProductInfo> productInfoList = productInfoRepository.findAllByProductStatus(ProductStatusEnum.UP.getCode());
		//2.获取已上架商品的类目列表
		List<Integer> categoryTypeList = productInfoList.stream().map(ProductInfo::getCategoryType).collect(Collectors.toList());
		//3.查询商品类目列表
		List<ProductCategory> productCategoryList = productCategoryRepository.findAllByCategoryTypeIn(categoryTypeList);
		/*4.构造前端数据*/
		//5.先组装商品类目
		List<ProductVO> productVOs = new ArrayList<>();
		for (ProductCategory productCategory : productCategoryList) {
			ProductVO productVO = new ProductVO();
			productVO.setCategoryName(productCategory.getCategoryName());
			productVO.setCategoryType(productCategory.getCategoryType());

			//6.再组装每个类目中的商品
			List<ProductInfoVO> productInfoVOs = new ArrayList<>();
			for (ProductInfo productInfo : productInfoList) {
				if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
					ProductInfoVO productInfoVO = new ProductInfoVO();
					BeanUtils.copyProperties(productInfo, productInfoVO);
					productInfoVOs.add(productInfoVO);
				}
			}

			productVO.setProductInfoVOList(productInfoVOs);
			productVOs.add(productVO);
		}
		return ResultVO.ok(productVOs);
	}

	@Override
	public List<ProductInfo> getListByIds(List<String> productIds) {
		return productInfoRepository.findAllByProductIdIn(productIds);
	}

	@Override
	public void decreaseStock(List<CartDTO> cartDTOList) {
		List<ProductInfo> productInfos = decreaseStockProcess(cartDTOList);
		//发送消息
		rabbitTemplate.convertAndSend("productInfo", new Gson().toJson(productInfos));
	}

	@Transactional
	public List<ProductInfo> decreaseStockProcess(List<CartDTO> cartDTOList) {
		List<ProductInfo> productInfos = new ArrayList<>();
		for (CartDTO cartDTO : cartDTOList) {
			Optional<ProductInfo> optional = productInfoRepository.findById(cartDTO.getProductId());
			//商品不存在
			if (!optional.isPresent()) {
				throw new SellException(ResultEnum.PRODUCT_NOT_EXIST.getMessage());
			}
			ProductInfo productInfo = optional.get();
			int count = productInfo.getProductStock() - cartDTO.getProductQuantity();
			//库存不足
			if (count < 0) {
				throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR.getMessage());
			}
			productInfo.setProductStock(count);
			productInfoRepository.save(productInfo);

			productInfos.add(productInfo);
		}
		return productInfos;
	}

}
