package com.casaba.spider.threads;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.casaba.spider.model.UserInfo;

public class UserInfoHandler extends Thread {

	private final static Logger logger = LoggerFactory.getLogger(UserInfoHandler.class);

	private final CloseableHttpClient httpClient;
	private final HttpContext context;
	private final HttpGet httpget;
	
	private final static int error_code = -1;

	public UserInfoHandler(CloseableHttpClient httpClient, HttpGet httpget) {
		this.httpClient = httpClient;
		this.context = HttpClientContext.create();
		this.httpget = httpget;

	}

	@Override
	public void run() {

		httpget.setHeader("Cookie",
				"d_c0=\"AGBA_aE0wQqPTrC-NfLN7ScZ8w8NOvBz318=|1477544499\"; _zap=c5defd0d-39a4-4add-9ce4-f3f4417174c5; q_c1=bb3f3fa3da6a4f12b13223913518285d|1504591595000|1498656641000; aliyungf_tc=AQAAAHvRq3UV2wEAO0yT083mYbD4+1YB; q_c1=bb3f3fa3da6a4f12b13223913518285d|1507690914000|1498656641000; r_cap_id=\"MDE1MjgzN2M0N2Y5NDgwODhlZjEyYzBmZmE5M2E1YTI=|1507875885|9ddba5e9e51644dc4b252544824e993ffcb2fe9d\"; cap_id=\"NWRiZTQwYzdhOWVhNGJkZDlmMThhMDk2YzMwNGE2NjQ=|1507875885|389cd1ddeefef3513f4014d88e26edc23e6f65a6\"; __utma=51854390.1659996692.1503822652.1506405569.1507875887.8; __utmb=51854390.0.10.1507875887; __utmc=51854390; __utmz=51854390.1507875887.8.6.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; __utmv=51854390.000--|2=registration_date=20170904=1^3=entry_date=20170628=1; z_c0=Mi4xckFQZEJRQUFBQUFBWUVEOW9UVEJDaGNBQUFCaEFsVk5ndWtIV2dBWjEtZWRGUDJsSFY3UVNtVHIybHNzbnl4YXV3|1507875970|d7971c679a5389d53bd2b8c8126f6f5ab4d58f18; _xsrf=759e1b6a-c9a3-4151-a47a-e5697d05a7cc");

		httpget.setHeader("User-Agent",
				"Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_0 like Mac OS X; en-us) AppleWebKit/532.9 (KHTML, like Gecko) Version/4.0.5 Mobile/8A293 Safari/6531.22.7");

		CloseableHttpResponse response = null;
		HttpEntity entity = null;
		
		try {
			 response = httpClient.execute(httpget, context);
			 entity = response.getEntity();

			if (null != entity) {

				String body = EntityUtils.toString(entity, "UTF-8");

//				System.out.println(body);

				String regex = "(<title>.*</title>)|(data-gender=\".\")|(location item\" title=.{0,30}\">)|(business item\" title=.{0,30}\">)|"
						+ "(employment item\" title=.{0,30}\">)|" + "(position item\" title=.{0,30}\">)|"
						+ "(education item\" title=.{0,30}\">)|" + "(education-extra item\" title=.{0,30}>)|"
						+ "([0-9]*</strong>赞同)|" + "([0-9]*</strong>感谢)|" + "(提问\\n<span class=\"num\">[0-9]*)|"
						+ "(回答\\n<span class=\"num\">[0-9]*)|" + "(文章\\n<span class=\"num\">[0-9]*)|"
						+ "(<strong>[0-9]*</strong> 收藏)|" + "(<strong>[0-9]*</strong> 分享)|"
						+ "(关注了</span><br />\\n<strong>[0-9]*)|" + "(关注者</span><br />\\n<strong>[0-9]*)|"
						+ "(<title>.*</title>)|" + "(data-gender=\".\")";

				Pattern p = Pattern.compile(regex);
				Matcher m = p.matcher(body);
				UserInfo userInfo = new UserInfo();

				while (m.find()) {

					String s = m.group();
					s = s.replace("\n", "");

					if (s.startsWith("<title>")) {
						s = s.substring(8, s.length() - 13);
//						System.out.println(s);
						userInfo.setName(s);
					}

					if (s.startsWith("location")) {
						s = s.substring(22, s.length() - 2);
//						System.out.println(s);
						userInfo.setLocation(s);
					}
					if (s.startsWith("business")) {
						s = s.substring(22, s.length() - 2);
//						System.out.println(s);
						userInfo.setBusiness(s);
					}
					if (s.startsWith("employment")) {
						s = s.substring(24, s.length() - 2);
//						System.out.println(s);
						userInfo.setEmployment(s);
					}
					if (s.startsWith("position")) {
						s = s.substring(22, s.length() - 2);
//						System.out.println(s);
						userInfo.setPosition(s);
					}
					if (s.startsWith("education-extra")) {
						s = s.substring(29, s.length() - 2);
//						System.out.println(s);
						userInfo.setEducation_extra(s);
					}

					if (s.startsWith("education ")) {
						s = s.substring(23, s.length() - 2);
//						System.out.println(s);
						userInfo.setEducation(s);
					}

					if (s.endsWith("赞同")) {
						s = s.substring(0, s.length() - 11);
						try {
//							System.out.println(s);
							userInfo.setSuppose(Integer.parseInt(s));
						} catch (NumberFormatException e) {
							logger.error("获取赞同属性失败!");
							userInfo.setSuppose(error_code);
						}
					}

					if (s.endsWith("感谢")) {
						s = s.substring(0, s.length() - 11);
						try {
//							System.out.println(s);
							userInfo.setThanks(Integer.parseInt(s));
						} catch (NumberFormatException e) {
							logger.error("获取感谢属性失败!");
							userInfo.setThanks(error_code);
						}
					}

					if (s.startsWith("data-")) {
						s = s.substring(13, s.length() - 1);
						System.out.println(s);
						if (s.equals("他")) {
							userInfo.setSex("male");
						} else {
							userInfo.setSex("female");
						}

					}

					if (s.startsWith("提问")) {
						s = s.substring(20);
						try {
//							System.out.println(s);
							userInfo.setQuestion(Integer.parseInt(s));
						} catch (NumberFormatException e) {
							logger.error("获取提问属性失败!");
							userInfo.setQuestion(error_code);
						}
					}

					if (s.startsWith("回答")) {
						s = s.substring(20);
						try {
//							System.out.println(s);
							userInfo.setAnswer(Integer.parseInt(s));
						} catch (NumberFormatException e) {
							logger.error("获取回答属性失败!");
							userInfo.setAnswer(error_code);
						}
					}

					if (s.startsWith("文章")) {
						s = s.substring(20);
						try {
//							System.out.println(s);
							userInfo.setArticle(Integer.parseInt(s));
						} catch (NumberFormatException e) {
							logger.error("获取文章属性失败!");
							userInfo.setArticle(error_code);
						}
					}

					if (s.startsWith("关注了")) {
						s = s.substring(24);
						try {
							System.out.println(s);
							userInfo.setFollowing(Integer.parseInt(s));
						} catch (NumberFormatException e) {
							logger.error("获取关注属性失败!");
							userInfo.setFollowing(error_code);
						}
					}

					if (s.startsWith("关注者")) {
						s = s.substring(24);
						try {
							System.out.println(s);
							userInfo.setFollowers(Integer.parseInt(s));
						} catch (NumberFormatException e) {
							logger.error("获取关注者属性失败!");
							userInfo.setFollowers(error_code);
						}
					}
					if (s.endsWith("分享")) {
						s = s.substring(8, s.length() - 12);

						try {
							System.out.println(s);
							userInfo.setShared(Integer.parseInt(s));
						} catch (NumberFormatException e) {
							logger.error("获取分享属性失败!");
							userInfo.setShared(error_code);
						}
					}

					if (s.endsWith("收藏")) {
						s = s.substring(8, s.length() - 12);

						try {
							System.out.println(s);
							userInfo.setCollected(Integer.parseInt(s));
						} catch (NumberFormatException e) {
							logger.error("获取收藏属性失败!");
							userInfo.setCollected(error_code);
						}
					}

				}

				System.out.println(userInfo);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			 try {
				EntityUtils.consume(entity);
				response.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             
		}
	}

}
