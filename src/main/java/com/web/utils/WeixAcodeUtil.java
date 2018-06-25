package com.web.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class WeixAcodeUtil {
	/*
     * 获取二维码
　　　* 这里的 post 方法 为 json post【重点】
     */
    public static String getCodeM() throws Exception {
        
        String imei ="867186032552993";
        String page="page/msg_waist/msg_waist";
//        String token = getToken();   // 得到token
        
        String token = "10_BR1tU9nYP7uV5gmQY2CsojFdzgHoT_ZH5LyVgan2YHI3SsLw3hv8ccxJsWV_-flgg5RfFke5qNkxpnKRBnZRy4aek3H0nXo8GnujhGoRrcXqVxw37R7BkAxcTWcMDGfAHAQHK";
        
        Map<String, Object> params = new HashMap<>();
        params.put("scene", imei);  //参数
        params.put("page", "page/msg_waist/msg_waist"); //位置
        params.put("width", 430);

        CloseableHttpClient  httpClient = HttpClientBuilder.create().build();

        HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+token);  // 接口
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
        String body = JSON.toJSONString(params);           //必须是json模式的 post      
        StringEntity entity;
        entity = new StringEntity(body);
        entity.setContentType("image/png");

        httpPost.setEntity(entity);
        HttpResponse response;

        response = httpClient.execute(httpPost);
        InputStream inputStream = response.getEntity().getContent();
        String name = imei+".png";
        saveToImgByInputStream(inputStream,"D:\\",name);  //保存图片
        return null;
    }

    public static void main(String[] args) throws Exception {
    	getCodeM();
	}




    /*
     * 获取 token
　　　* 普通的 get 可获 token
     */
    public String getToken() {
        try {

    		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    		String replace = url.replace("APPID", Const.APP_ID).replace("APPSECRET", Const.APP_SECRET);

    		CloseableHttpClient client = HttpClients.createDefault();
    		HttpGet get = new HttpGet(replace);

    		CloseableHttpResponse res = client.execute(get);
            JSONObject json = JSONObject.parseObject(EntityUtils.toString(res.getEntity(), "UTF-8"));

            if (json.getString("access_token") != null || json.getString("access_token") != "") {
                return json.getString("access_token");
            } else {
                return null;
            }
} catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }




     /**
     * 将二进制转换成文件保存
     * @param instreams 二进制流
     * @param imgPath 图片的保存路径
     * @param imgName 图片的名称
     * @return 
     *      1：保存正常
     *      0：保存失败
     */
    public static int saveToImgByInputStream(InputStream instreams,String imgPath,String imgName){
        int stateInt = 1;
        if(instreams != null){
            try {
                File file=new File(imgPath,imgName);//可以是任何图片格式.jpg,.png等
                FileOutputStream fos=new FileOutputStream(file);
                byte[] b = new byte[1024];
                int nRead = 0;
                while ((nRead = instreams.read(b)) != -1) {
                    fos.write(b, 0, nRead);
                }
                fos.flush();
                fos.close();                
            } catch (Exception e) {
                stateInt = 0;
                e.printStackTrace();
            } finally {
            }
        }
        return stateInt;
    }
}
