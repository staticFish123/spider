package com.casaba.spider.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigProperty {
	private final static Logger logger = LoggerFactory.getLogger(ConfigProperty.class);
	private static Properties props;

	public static Properties getInstance() {
		return props;
	};

	static {
		InputStream is = null;
		props = new Properties();
		try {
			is = ConfigProperty.class.getClassLoader().getResource("config.properties").openStream();
			props.load(is);
		} catch (IOException e) {
			logger.error("File Not Found !", e);
		}
	}

}
