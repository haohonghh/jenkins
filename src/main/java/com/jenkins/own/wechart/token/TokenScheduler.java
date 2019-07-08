package com.jenkins.own.wechart.token;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;

/**
 * @author 成小新
 * @ProjectName wechat
 * @Description: 定时器
 * @date 2018/11/13:38 PM
 * @email zhaoboy9692@163.com
 */
//@Component
//@EnableScheduling
public class TokenScheduler {

    private static String access_token;

    /**
     * 定时获取access_token
     *
     * @throws SQLException
     */
    @Scheduled(fixedDelay = 500)
    public String getAccessToken() {
        String grant_type = "client_credential";
        String AppId = "wx2ecdd2ac6af817bd";
        String secret = "452d3d5b661a16d27c1fe9e414dc55bd";
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=" + grant_type + "&appid=" + AppId + "&secret=" + secret;
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JsonObject returnData = new JsonParser().parse(message).getAsJsonObject();
            access_token = returnData.get("access_token").getAsString();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(access_token);
        return access_token;
    }



}



        
