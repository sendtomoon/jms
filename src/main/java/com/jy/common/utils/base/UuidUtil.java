package com.jy.common.utils.base;

import java.util.UUID;

public class UuidUtil {

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String get32UUID() {
		return UUID.randomUUID().toString().trim().replaceAll("-", "");
	}

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间有-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

}
