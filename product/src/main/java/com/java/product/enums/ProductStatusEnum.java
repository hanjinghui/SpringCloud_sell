package com.java.product.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 商品状态枚举
 * @author jiangli
 * @date 2019/8/19 19:10
 */
@Getter
@AllArgsConstructor
public enum ProductStatusEnum {
	UP(0,"上架"),
	DOWN(1,"下架");

	private int code;
	private String desc;
}
