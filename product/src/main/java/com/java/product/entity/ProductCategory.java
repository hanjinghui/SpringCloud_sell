package com.java.product.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author jiangli
 * @email 31346337@qq.com
 * @date 2019-08-18 23:17:00
 */
@Entity
@Table(name="product_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Id
	private Integer categoryId;
	/**
	 * 类目名字
	 */
	private String categoryName;
	/**
	 * 类目编号
	 */
	private Integer categoryType;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;

}
