package com.web.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HttpUtil
{
  public static String get(String url, HashMap<String, Object> paramMap)
  {
    String result = "";
    BufferedReader in = null;
    try {
      String line;
      String param = "";
      Iterator it = paramMap.keySet().iterator();

      while (it.hasNext()) {
        String key = (String)it.next();
        param = param + key + "=" + paramMap.get(key) + "&";
      }
      String urlNameString = url + "?" + param;

      URL realUrl = new URL(urlNameString);

      URLConnection connection = realUrl.openConnection();

      connection.setRequestProperty("accept", "*/*");
      connection.setRequestProperty("connection", "Keep-Alive");
      connection.setRequestProperty("user-agent", 
        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

      connection.connect();

      Map map = connection.getHeaderFields();

      in = new BufferedReader(
        new InputStreamReader(connection.getInputStream()));

      while ((line = in.readLine()) != null)
        result = result + line;
    }
    catch (Exception e) {
      System.out.println("发送GET请求出现异常！" + e);
      e.printStackTrace();
      try
      {
        if (in != null)
        in.close();
      }
      catch (Exception e2) {
        e2.printStackTrace();
      }
    }
    finally
    {
      try
      {
        if (in != null)
          in.close();
      }
      catch (Exception e2) {
        e2.printStackTrace();
      }
    }
    return result;
  }

  public static void main(String[] args)
  {
    String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";

    HashMap map = new HashMap();
    map.put("appid", "wxc0a422ba9287706a");
    map.put("secret", "a6c93320cad4374cbf58eeaf5505bed8");
    map.put("js_code", "1111");
    map.put("grant_type", "authorization_code");
    String data = get(requestUrl, map);
    System.out.println(data);
  }
}