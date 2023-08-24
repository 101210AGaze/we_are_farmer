package com.farmer.dataanalysis.Utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 封装HttpClient工具，方便爬取网页内容
 * @Author HWJ
 * @since 2023/7/26
 */
public abstract class HttpUtil {

    private static PoolingHttpClientConnectionManager cm;//声明HttpClient管理器对象（HttpClient连接池）
    private static List<String> userAgentList = null;
    private static RequestConfig config = null;

    //静态代码块会在类被加载的时候执行
    static {
        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(20);
        config = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setSocketTimeout(10000)
                .setConnectionRequestTimeout(10000)
                //.setProxy(new HttpHost("114.231.41.67",8888))
                .build();
        userAgentList = new ArrayList<>();
        userAgentList.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36 Edg/116.0.0.0");
        userAgentList.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");
        userAgentList.add("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36");
    }

    /**
     * 传入网页url，返回网页源码（String)
     * @param url
     * @return (String)Html
     */
    public static String getHtmlByGet(String url){
        //1、从连接池中获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        //2、创建HttpGet对象
        HttpGet httpGet = new HttpGet(url);
        //3、设置请求头和请求配置对象（可以在获取HttpClient对象的时候设置，也可以在这里设置）
        httpGet.setConfig(config);
        httpGet.setHeader("User-Agent",userAgentList.get(new Random().nextInt(userAgentList.size())));//从userAgent列表中随机取
        //4、发起请求
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            //5、获取响应内容
            if(response.getStatusLine().getStatusCode()==200){
                String html="";
                if(response.getEntity()!=null){
                    html = EntityUtils.toString(response.getEntity(),"UTF-8");
                }
                return html;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                response.close();
                //httpClient.close();//注意，这里的HttpClient是从连接池中获取的，不需要关闭
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public static String getHtmlByPost(String url, List<NameValuePair>params){
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        CloseableHttpResponse httpResponse = null;
        String html = null;
        HttpPost httpPost = new HttpPost(url);
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params,"UTF-8");//创建请求体
            httpPost.setEntity(urlEncodedFormEntity);//设置请求体
            httpPost.setHeader("User-Agent",userAgentList.get(new Random().nextInt(userAgentList.size())));//从userAgent列表中随机取
            httpPost.addHeader("Accept", "*/*");
            httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
            httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
            httpPost.addHeader("Connection", "keep-alive");
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        //发起请求并根据响应状态码获取响应数据
        try {
            httpResponse = httpClient.execute(httpPost);
            if(httpResponse.getStatusLine().getStatusCode()==200) {
                html = EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            httpResponse.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return html;
    }

    public static String sendJsonAndGetHtml(String url, JSONObject jsonObject,String encoding){
        String html = null;
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        CloseableHttpResponse httpResponse = null;
        HttpPost httpPost = new HttpPost(url);
        try{
            StringEntity s = new StringEntity(jsonObject.toString(), "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            httpPost.setEntity(s);
            System.out.println("请求地址："+url);

            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("User-Agent", userAgentList.get(new Random().nextInt(userAgentList.size())));

            //执行请求操作，并拿到结果（同步阻塞）
            httpResponse = httpClient.execute(httpPost);
            //获取结果实体
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                //按指定编码转换结果实体为String类型
                html = EntityUtils.toString(entity, encoding);
            }
            EntityUtils.consume(entity);
            //释放链接
            httpResponse.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return html;
    }

}

