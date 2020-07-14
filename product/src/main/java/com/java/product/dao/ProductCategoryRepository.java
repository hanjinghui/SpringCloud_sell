package com.java.product.dao;

import com.java.product.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 数据访问接口
 * @author jiangli
 *
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,String>,JpaSpecificationExecutor<ProductCategory>{

	List<ProductCategory> findAllByCategoryTypeIn(List<Integer> categoryTypeList);
	
}
