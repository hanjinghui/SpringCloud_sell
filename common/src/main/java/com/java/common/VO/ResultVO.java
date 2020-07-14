package com.java.common.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jiangli
 * @date 2019/8/19 19:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultVO<T> {
	private Boolean flag;
	/**
	 * 返回码
	 */
	private Integer code;
	/**
	 * 消息
	 */
	private String msg;
	/**
	 * 返回
	 */
	private T data;

	// 返回错误，可以传code和msg
	public static <T> ResultVO<T> error(Integer code, String msg) {
		return new ResultVO<>(false,code, msg, null);
	}

	// 返回错误，可以传msg
	public static <T> ResultVO<T> error(String msg) {
		return new ResultVO<>(false, StatusCode.ERROR, msg, null);
	}

	// 返回成功，可以传data值
	public static <T> ResultVO<T> ok(T data) {
		return new ResultVO<>(true,StatusCode.OK, "成功", data);
	}

	// 返回成功，沒有data值
	public static <T> ResultVO<T> ok() {
		return new ResultVO<>(true,StatusCode.OK, "成功", null);
	}

	// 返回成功，可以传msg，沒有data值
	public static <T> ResultVO<T> ok(String msg) {
		return new ResultVO<>(true,StatusCode.OK, msg, null);
	}

}