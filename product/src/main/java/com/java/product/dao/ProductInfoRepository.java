package com.java.product.dao;

import com.java.product.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 数据访问接口
 * @author jiangli
 *
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String>,JpaSpecificationExecutor<ProductInfo>{

	List<ProductInfo> findAllByProductStatus(Integer productStatus);

	List<ProductInfo> findAllByProductIdIn(List<String> productIds);
	
}
