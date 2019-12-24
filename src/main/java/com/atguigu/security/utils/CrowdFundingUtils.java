package com.atguigu.security.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Map;

public class CrowdFundingUtils {

	/**
	 * 判断Map是否有效
	 * @param map 待验证的Map
	 * @return true表示有效，false表示无效
	 */
	public static <K,V> boolean mapEffective(Map<K, V> map) {

		return map != null && map.size() > 0;
	}

	/**
	 * 判断集合是否有效
	 * @param collection 待验证集合
	 * @return true表示有效，false表示无效
	 */
	public static <E> boolean collectionEffective(Collection<E> collection) {

		return collection != null && collection.size() > 0;
	}

	/**
	 * 判断字符串是否有效
	 * @param source 待验证字符串
	 * @return true表示有效，false表示无效
	 */
	public static boolean stringEffective(String source) {

		return source != null && source.length() > 0;
	}

	/**
	 * 执行MD5加密的工具方法
	 * @param source	明文
	 * @return			密文
	 * @throws Exception
	 */
	public static String md5(String source) {

		// 声明加密算法名称
		String algorithm = "md5";

		try {

			// 获取MessageDigest类的实例
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

			// 获取source的字节数组
			byte[] bytes = source.getBytes();

			// 执行加密
			byte[] digest = messageDigest.digest(bytes);

			// 声明字符数组
			char[] characters = new char[] {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

			// 创建StringBuilder对象
			StringBuilder builder = new StringBuilder();

			// 遍历加密得到的字节数组
			for (int i = 0; i < digest.length; i++) {

				byte b = digest[i];

				// 获取当前字节八个二进制位中的低四位值
				int lowValue = b & 15;

				// 获取当前字节八个二进制位中的高四位值
				int highValue = (b >> 4) & 15;

				// 以高四位值从字符数组中获取对应字符
				char highChar = characters[highValue];

				// 以低四位值从字符数组中获取对应字符
				char lowChar = characters[lowValue];

				// 拼接
				builder.append(highChar).append(lowChar);
			}

			return builder.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void main(String[] args) {
		String source = "456456";
		String md5 = md5(source);
		System.out.println(md5);
	}

}
