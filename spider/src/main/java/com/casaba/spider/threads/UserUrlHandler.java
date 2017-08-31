package com.casaba.spider.threads;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.casaba.spider.dao.IUserUrl;
import com.casaba.spider.model.UserUrl;



public class UserUrlHandler extends Thread {

	private final static Logger logger = LoggerFactory.getLogger(UserUrlHandler.class);
	
	@Autowired
	private IUserUrl iUserUrl;

	private CloseableHttpClient httpClient;
	private HttpContext context;
	private HttpPost httpPost;

	public UserUrlHandler(CloseableHttpClient httpClient, HttpPost httpPost) {
		this.httpClient = httpClient;
		this.httpPost = httpPost;
		this.context = HttpClientContext.create();
	}

	@Override
	public void run() {

		Integer offSet = 0;
		
		UserUrl userUrl = new UserUrl();

		while (true) {

			httpPost.setHeader("Cookie", null);
			httpPost.setHeader("X-Xsrftoken", null);
			// TODO add cookie & token

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("offset", offSet.toString()));

			UrlEncodedFormEntity entityPost = new UrlEncodedFormEntity(params, Consts.UTF_8);
			httpPost.setEntity(entityPost);
			CloseableHttpResponse response = null;
			HttpEntity entity = null;

			try {

				response = httpClient.execute(httpPost, context);
				entity = response.getEntity();

				if (null != entity) {

					String body = EntityUtils.toString(entity, Consts.UTF_8);

					if (body.length() < 100) {

						logger.info("---------------End-------------------");
						break;

					}

					if (offSet > 2000) {

						logger.info("--------------Off Set 大于2000------------------");
						break;

					}

					String regex = "people...[a-zA-z-]{0,200}\">";
					Pattern p = Pattern.compile(regex);
					Matcher m = p.matcher(body);

					while (m.find()) {

						String s = m.group();
						String user = s.substring(8, s.length() - 3);
						logger.info("User:" + user);
						userUrl.setUserName(user);
						iUserUrl.addUserUrl(userUrl);
					}
					
					logger.info("Topic ID: "+httpPost.getURI()+"OffSet="+offSet);

				}
			} catch (ClientProtocolException e) {
				logger.error("ClientProtocolException", e);
			} catch (IOException e) {
				logger.error("IOException", e);
			}finally {
				try {
					response.close();
					EntityUtils.consume(entity);
				} catch (IOException e) {
					logger.error("IOException Error", e);
				}
				offSet=offSet+20;
			}

		}
	}

}
