package com.web.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeChatUtil {
	public static String getOpenIdByCode(String jscode) {
		String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";

		HashMap map = new HashMap();
		map.put("appid", "wxc0a422ba9287706a");
		map.put("secret", "a6c93320cad4374cbf58eeaf5505bed8");
		map.put("js_code", jscode);
		map.put("grant_type", "authorization_code");
		String data = HttpUtil.get(requestUrl, map);

		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode node = (JsonNode) mapper.readValue(data, JsonNode.class);
			JsonNode errcode = node.get("errcode");
			if (errcode != null) {
				JsonNode errmsg = node.get("errmsg");
				System.out.println("调用微信接口失败：" + errmsg.asText());
				return null;
			}

			JsonNode openId = node.get("openid");
			return openId.asText();
		} catch (Exception e) {
			System.out.println("解析微信返回结果失败" + e);
			e.printStackTrace();

			return null;
		}
	}

//	public static String getTicket(String accessToken, String expireSeconds ,int sceneId) {
//		
//	}
	
	public static String createWxacode(String accessToken, String scene, String page, String wxacodeSavePath){
		String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken;
		CloseableHttpClient  httpClient = HttpClientBuilder.create().build();
		//		scene	String	
		//		page	String	
		//		width	Int	430
		//		auto_color	Bool	false
		//		line_color	Object	{"r":"0","g":"0","b":"0"}
		//		is_hyaline	Bool	false
		Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("scene",scene);
        paramMap.put("page",page);
        paramMap.put("width",430);
        
        String body = JSON.toJSONString(paramMap);
        StringEntity entity;
        HttpResponse response;
        HttpPost httpPost;
        InputStream inputStream = null;
        FileOutputStream out = null;
		try {
			entity = new StringEntity(body);
	        entity.setContentType("image/png");
	
	        httpPost = new HttpPost(url);
	        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json"); 
	        httpPost.setEntity(entity);
        
			response = httpClient.execute(httpPost);
	        inputStream = response.getEntity().getContent();
	        out = new FileOutputStream(wxacodeSavePath + File.separator + scene + ".png");

            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while((bytesRead = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return "D:\\scene.png";

  }

	public static void main(String[] argc) {
//		System.out.println(createWxacode(AccessTokenUtils.getAccessToken(null),"les001", "page.jsp", "D:\\"));
//		String token = "10_BR1tU9nYP7uV5gmQY2CsojFdzgHoT_ZH5LyVgan2YHI3SsLw3hv8ccxJsWV_-flgg5RfFke5qNkxpnKRBnZRy4aek3H0nXo8GnujhGoRrcXqVxw37R7BkAxcTWcMDGfAHAQHK";
//		createWxacode(token,"les001", "page/abc/ddd", "D:\\");
	}
}