package com.java.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 
 * @author jiangli
 * @email 31346337@qq.com
 * @date 2019-08-18 23:17:00
 */
@Entity
@Table(name="product_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Id
	private String productId;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 单价
	 */
	private BigDecimal productPrice;
	/**
	 * 库存
	 */
	private Integer productStock;
	/**
	 * 描述
	 */
	private String productDescription;
	/**
	 * 小图
	 */
	private String productIcon;
	/**
	 * 商品状态,0正常1下架
	 */
	private Integer productStatus;
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
