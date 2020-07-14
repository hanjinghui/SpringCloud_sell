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
@Table(name="order_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Id
	private String detailId;
	/**
	 * 
	 */
	private String orderId;
	/**
	 * 
	 */
	private String productId;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 当前价格,单位分
	 */
	private BigDecimal productPrice;
	/**
	 * 数量
	 */
	private Integer productQuantity;
	/**
	 * 小图
	 */
	private String productIcon;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;

}
