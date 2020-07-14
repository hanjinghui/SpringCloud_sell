package com.java.product.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author jiangli
 * @date 2019/8/19 19:21
 */
@Data
public class ProductVO {

	/**
	 * @JsonProperty("name") 给前端输出时key=name
	 */
	@JsonProperty("name")
	private String categoryName;

	@JsonProperty("type")
	private Integer categoryType;

	@JsonProperty("foods")
	List<ProductInfoVO> productInfoVOList;
}
