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
import org.springframework.test.context.ContextConfiguration;

import com.casaba.spider.dao.IUserUrl;
import com.casaba.spider.model.UserUrl;
import com.casaba.spider.utils.SpringContextUtil;


@ContextConfiguration("classpath:/spring-mybatis.xml")
public class UserUrlHandler extends Thread {

	private final static Logger logger = LoggerFactory.getLogger(UserUrlHandler.class);
	
	private IUserUrl iUserUrl = SpringContextUtil.getBean(IUserUrl.class);
	
//	@Autowired
//	private IUserUrl iUserUrl;

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

			httpPost.setHeader("Cookie", "d_c0=\"AGBA_aE0wQqPTrC-NfLN7ScZ8w8NOvBz318=|1477544499\"; _zap=c5defd0d-39a4-4add-9ce4-f3f4417174c5; q_c1=bb3f3fa3da6a4f12b13223913518285d|1501574456000|1498656641000; aliyungf_tc=AQAAAOU3cUrImAIAM0yT0/Nt7FYlZAHu; capsion_ticket=\"2|1:0|10:1504144146|14:capsion_ticket|44:MTJjZDExYzNkNjgzNGFjOTkyNDcxZmI1ZDg1YjI1OGQ=|0d2b4f04c1276780f815d298c34a17d9f8f1391d1afcb4455857fb0ea77c11d5\"; q_c1=bb3f3fa3da6a4f12b13223913518285d|1504488115000|1498656641000; l_cap_id=\"ZWMzZTMxYjY0YjU4NGVhMmIxNWQzZTA5YTg4NTFhNTE=|1504490611|891e0125ca0a9b94af81e79ec27cd650056a746b\"; r_cap_id=\"OTg3YzU0MWZiZjE4NDkyN2IxMjEyZmZmNzY1NjZjYTU=|1504490611|15b301dc74ea89155631f33341063898e465110e\"; cap_id=\"YmVkYmYzOWNjMjVjNDk3ODhhNmUxMGEzOGU4MmQ3YTg=|1504490611|fb7259a99d7b2ec4f03886515653ba5f164c4a68\"; z_c0=Mi4xckFQZEJRQUFBQUFBWUVEOW9UVEJDaGNBQUFCaEFsVk5ya0hVV1FBLWNHUEpzTjV3QTFvYnNFdHUyektrb09VUk13|1504490670|64f60438f84d24a27fc311e82891581671d0c6b1; unlock_ticket=\"QUFCQ0h4MjhVZ3dYQUFBQVlRSlZUYmE3ckZuZ1p6cmpUS1VFSFp4dFVaZWcwOFdrM3M0RnhBPT0=|1504490670|2990a89ac5f4454d6a2249df3f7e550880f45dd0\"; _xsrf=fa550e37-6151-4a94-ab63-13b36676da1d; __utma=51854390.1659996692.1503822652.1504143844.1504490611.4; __utmb=51854390.0.10.1504490611; __utmc=51854390; __utmz=51854390.1504490611.4.4.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; __utmv=51854390.100--|2=registration_date=20170904=1^3=entry_date=20170628=1");
			httpPost.setHeader("X-Xsrftoken", "fa550e37-6151-4a94-ab63-13b36676da1d");

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
