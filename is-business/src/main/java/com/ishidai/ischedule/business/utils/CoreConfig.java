package com.ishidai.ischedule.business.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @note 核心配置
 * @author wangmeng
 * @date 2015年9月1日 下午4:49:31
 */
public class CoreConfig {

	private static Properties properties = new Properties();

	/**
	 * @note 初始化资源文件
	 * @author wangmeng
	 * @date 2015年9月1日 下午4:49:31
	 */
	static {
		try {
			InputStream inputStream = CoreConfig.class.getClassLoader().getResourceAsStream("core_api_key.properties");
			properties.load(inputStream);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 系统识别码
	public static String SOURCE_CODE = properties.getProperty("source_code");
}
