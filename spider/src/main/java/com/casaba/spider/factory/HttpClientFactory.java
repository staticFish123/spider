package com.casaba.spider.factory;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpClientFactory {

	public static CloseableHttpClient getHttpClient(int maxTotal, int defaultMaxPerRoute) {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(maxTotal);
		cm.setDefaultMaxPerRoute(defaultMaxPerRoute);
		return HttpClients.custom().setRetryHandler(new DefaultHttpRequestRetryHandler()).setConnectionManager(cm).build();
	}
}
