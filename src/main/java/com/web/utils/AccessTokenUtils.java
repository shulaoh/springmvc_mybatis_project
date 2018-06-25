package com.web.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.web.data.pojo.AccessToken;
import com.web.service.IAccessTokenService;

import net.sf.json.JSONObject;

public class AccessTokenUtils {
	/**
	 * 获取当前时间戳，10位
	 * 
	 * @return
	 */
	public static long getExpiresCurr() {
		long currentTimeMillis = System.currentTimeMillis() / 1000;
		String expiresCurr = String.format("%010d", currentTimeMillis);
		return Long.parseLong(expiresCurr);
	}

	/**
	 * 获取accessToken
	 * 
	 * @param service
	 * @return
	 */
	public static String getAccessToken(IAccessTokenService service) {
		// 查询数据库
		AccessToken accessToken = null;
		if (service != null) {
			accessToken = service.getAccessToken();
		}
		long expiresCurr = getExpiresCurr();
		// 判空
		if (accessToken != null) {
			long expiresAfter = accessToken.getExpiresAfter();
			// 判断accessToken是否有效
			if (expiresCurr < (expiresAfter - 300)) {
				return accessToken.getAccessToken();
			} else {
				// 更改
				updateToken(service, accessToken);
			}
		} else {
			// 获取accessToken
			accessToken = updateToken(service, null);
		}
		if (accessToken != null) {
			return accessToken.getAccessToken();
		}
		return null;
	}

	/**
	 * 更改accesstoken的方法
	 * 
	 * @param service
	 * @param accessToken
	 */
	private static AccessToken updateToken(IAccessTokenService service, AccessToken accessToken) {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
		String replace = url.replace("APPID", Const.APP_ID).replace("APPSECRET", Const.APP_SECRET);
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(replace);
		try {
			CloseableHttpResponse execute = client.execute(get);
			if (execute.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = execute.getEntity();
				String result = EntityUtils.toString(entity, "UTF-8");
				JSONObject jsonObject = JSONObject.fromObject(result);
				String access_token = jsonObject.getString("access_token");
				Integer expiresIn = jsonObject.getInt("expires_in");
				if (access_token != null && expiresIn != null) {
					long expiresCurr = getExpiresCurr();
					long expiresAfter = expiresCurr + expiresIn;
					if (accessToken == null) {
						accessToken = new AccessToken();
					}
					accessToken.setExpiresAfter(expiresAfter);
					accessToken.setAccessToken(access_token);
					accessToken.setExpiresIn(expiresIn);
					if (service != null) {
						if (accessToken.getId() == null) {
							accessToken.setId(Tools.generateID());
								service.addAccessToken(accessToken);
						} else {
							service.updateAccessToken(accessToken);
						}
					}
				}
			}
			return accessToken;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
