package com.hans.hennge;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Base64;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {
	public static String doPostForJson(String url, String jsonParams){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().
                setConnectTimeout(180 * 1000).setConnectionRequestTimeout(180 * 1000)
                .setSocketTimeout(180 * 1000).setRedirectsEnabled(true).build();
///////////////////////////////////////////////////////////////////////////////////         
        long currentTime= new Date().getTime();
        //long T = (currentTime/1000 + 3600000)/30;
        long T = (currentTime/1000)/30;
        String steps = Long.toHexString(T).toUpperCase();
        while(steps.length()<16) {
        	steps = "0" + steps;
        }
        String UserId = "e0253700@u.nus.edu";
        String Token = "653032353337303040752e6e75732e6564754844454348414c4c454e4745303033";
        String Password = TOTP.generateTOTP512(Token,steps,"10");
        String Auth = UserId + ':' + Password;
        Auth = Base64.getEncoder().encodeToString(Auth.getBytes());
        Auth = "Basic" + ' ' + Auth;
///////////////////////////////////////////////////////////////////////////////        
        httpPost.setConfig(requestConfig);
        httpPost.setHeader("Content-Type","application/json");
        httpPost.addHeader("Authorization", Auth);
        try {
            httpPost.setEntity(new StringEntity(jsonParams,ContentType.create("application/json", "utf-8")));
            System.out.println("request parameters" + EntityUtils.toString(httpPost.getEntity()));
            HttpResponse response = httpClient.execute(httpPost);
            System.out.println(" code:"+response.getStatusLine().getStatusCode());
            System.out.println("doPostForInfobipUnsub response"+response.getStatusLine().toString());
            return String.valueOf(response.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            return "post failure :caused by-->" + e.getMessage().toString();
        }finally {
            if(null != httpClient){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
}
}
