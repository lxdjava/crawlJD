package com.lxd.crawlJD.utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class HttpUtil {
    private PoolingHttpClientConnectionManager cm;

    public HttpUtil(){
        this.cm = new PoolingHttpClientConnectionManager();
        //设置最大连接数
        this.cm.setMaxTotal(100);
        //设置每台host的最大连接数
        this.cm.setDefaultMaxPerRoute(10);
    }

    public String doGetHtml(String url){
        //通过连接词获取httpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        //通过创建httpGet对象，设置url对象
        HttpGet httpGet = new HttpGet(url);
        //设置请求信息
        httpGet.setConfig(this.getConfig());
        //使用httpClient发起请求，获取响应
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200){
                //判断响应体Entity是否为空，如果不为空就可以使用EntityUtils
                if (response.getEntity() != null){
                    return EntityUtils.toString(response.getEntity(),"utf8");
                }else {
                    return "响应体为空";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //解析响应，返回结果
        return "error："+response.getStatusLine().getStatusCode();
    }


    public String doGetImage(String url){
        //通过连接词获取httpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        //通过创建httpGet对象，设置url对象
        HttpGet httpGet = new HttpGet(url);
        //设置请求信息
        httpGet.setConfig(this.getConfig());
        //使用httpClient发起请求，获取响应
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200){
                //判断响应体Entity是否为空，如果不为空就可以使用EntityUtils
                if (response.getEntity() != null){
                    //下载图片
                    //获取图片的后缀
                    String extName = url.substring(url.lastIndexOf("."));
                    //创建图片名，重命名图片
                    String picName = UUID.randomUUID().toString()+"."+extName;
                    //下载图片
                    OutputStream outputStream = new FileOutputStream(new File("C:\\Users\\Administrator\\Pictures\\Saved Pictures\\timg.jfif"));
                    response.getEntity().writeTo(outputStream);
                    //返回图片名称
                    return picName;
                }else {
                    return "响应体为空";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //解析响应，返回结果
        return "error："+response.getStatusLine().getStatusCode();
    }
    //设置请求信息
    private RequestConfig getConfig(){
        return RequestConfig.custom()
                .setSocketTimeout(1000)
                .setConnectionRequestTimeout(500)
                .setSocketTimeout(10000)
                .build();
    }
}