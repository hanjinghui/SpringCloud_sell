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
@Table(name="order_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Id
	private String orderId;
	/**
	 * 买家名字
	 */
	private String buyerName;
	/**
	 * 买家电话
	 */
	private String buyerPhone;
	/**
	 * 买家地址
	 */
	private String buyerAddress;
	/**
	 * 买家微信openid
	 */
	private String buyerOpenid;
	/**
	 * 订单总金额
	 */
	private BigDecimal orderAmount;
	/**
	 * 订单状态, 默认为新下单
	 */
	private Integer orderStatus;
	/**
	 * 支付状态, 默认未支付
	 */
	private Integer payStatus;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;

}
