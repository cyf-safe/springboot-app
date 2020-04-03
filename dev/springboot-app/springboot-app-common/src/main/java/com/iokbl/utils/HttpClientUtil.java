package com.iokbl.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpClientUtil<T> {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private static int connectTimeout=60000;//设置连接超时时间,单位毫秒  1分钟

    private static int socketTimeout=60000;//请求获取数据的超时时间,单位毫秒 如果访问一个接口,多少时间内无法返回数据，就直接放弃此次调用  1分钟

    private static CloseableHttpClient httpClient ;

	public String doPost(RestTemplate restTemplate, String url, T t){
		String result = null;

		if(!StringUtils.isEmpty(url)) {
			try {
				HttpHeaders headers = new HttpHeaders();
				//定义请求参数类型，这里用json所以是MediaType.APPLICATION_JSON
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<T> entity = new HttpEntity<>(t, headers);

				ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);

				//获取3方接口返回的数据通过entity.getBody();它返回的是一个字符串；
				result = responseEntity.getBody();
			} catch (Exception e) {
				logger.error("HttpClientUtil.doPost： {} exceptionAbstract：{} exceptionDetail：{}",new Object[]{url,e.toString(),e.getStackTrace()});
			}
		} else {
			logger.error("HttpClientUtil.doPost params is null, url：{} jsonStr：{}",new Object[]{url,t});
		}

		return result;
	}

    public static String doPost(String url, String jsonStr){
    	String result = null;
    	if(!StringUtils.isEmpty(url)&&!StringUtils.isEmpty(jsonStr)) {
    		HttpPost httpPost = new HttpPost(url);
            try {
    	        httpPost.addHeader("Content-Type", "application/json");
    	        StringEntity postEntity = new StringEntity(jsonStr, "UTF-8");
    	        httpPost.setEntity(postEntity);
    	        connectTimeout=1200000;//设置连接超时时间,单位毫秒  20分钟
    	        socketTimeout=1200000;//请求获取数据的超时时间,单位毫秒 如果访问一个接口,多少时间内无法返回数据，就直接放弃此次调用  20分钟
    	        // 构建请求客户端
    	        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
    	        httpPost.setConfig(requestConfig);
    	        httpClient = HttpClients.custom().build();
    	        // 执行请求，获取结果
                HttpResponse response = httpClient.execute(httpPost);
                logger.info("HttpClientUtil.doPost： {} json: {} 返回HTTP状态码: {}",new Object[]{url,jsonStr,response.getStatusLine().getStatusCode()});
                if(HttpStatus.OK.value() == response.getStatusLine().getStatusCode()){
                     result = EntityUtils.toString(response.getEntity(), "UTF-8");
                }
            } catch (Exception e) {
                logger.error("HttpClientUtil.doPost： {} exceptionAbstract：{} exceptionDetail：{}",new Object[]{url,e.toString(),e.getStackTrace()});
            } finally {
                httpPost.releaseConnection();
            }
    	} else {
    		logger.error("HttpClientUtil.doPost params is null, url：{} jsonStr：{}",new Object[]{url,jsonStr});
    	}
        return result;
    }

    public static String doGet(String url, Map<String, String> paramsMap){
    	String result = null;
    	if(!StringUtils.isEmpty(url)) {
    		HttpGet httpGet = null;
            try {
                // 构建请求url
                if( !CollectionUtils.isEmpty(paramsMap)){
                	List<NameValuePair> params = new ArrayList<>();
                    for (Entry<String, String> entry : paramsMap.entrySet()) {
                        NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue());
                        params.add(pair);
                    }
                    String paramStr = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
                    httpGet = new HttpGet(url + "?" + paramStr);
                }else{
                    httpGet = new HttpGet(url);
                }
                connectTimeout=1200000;//设置连接超时时间,单位毫秒  20分钟
                socketTimeout=1200000;//请求获取数据的超时时间,单位毫秒 如果访问一个接口,多少时间内无法返回数据，就直接放弃此次调用  20分钟
                // 构建请求客户端
                RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
                httpGet.setConfig(requestConfig);
                httpClient = HttpClients.custom().build();
                // 执行请求，获取结果
                HttpResponse response = httpClient.execute(httpGet);
                logger.info("HttpClientUtil.doGet： {} json: {} 返回HTTP状态码: {}",new Object[]{url,paramsMap,response.getStatusLine().getStatusCode()});
                if(HttpStatus.OK.value() == response.getStatusLine().getStatusCode()){
                    result = EntityUtils.toString(response.getEntity(), "UTF-8");
                }
            } catch (IOException e) {
            	logger.error("HttpClientUtil.doGet： {} exceptionAbstract：{} exceptionDetail：{}",new Object[]{url,e.toString(),e.getStackTrace()});
            } finally {
                httpGet.releaseConnection();
            }
    	} else {
    		logger.error("HttpClientUtil.doGet params is null, url：{} paramsMap：{}",new Object[]{url,paramsMap});
    	}
        return result;
    }
    
    /**
     * Post请求Http接口调用     泛型clz一定是接口返回数据的jsonstr格式匹配
     * @param url 请求地址  
     * @param params 入参
     * @param clz 接口返回json字符串对应的对象的泛型 如:A.class ;
     * @return
     * @throws Exception
     */
	@SuppressWarnings("unchecked")
	public static <T> T send(String url , Object params,Class<T> clz ) throws Exception{
		T result=null;
		if(StringUtils.isEmpty(url) || null==params){
    		logger.error("HttpClientUtil.send 参数异常[null]...url:{}",url);
    	}else {
        	HttpPost httpPost = new HttpPost(url);
    	    try {
    	    	long start = System.currentTimeMillis();
    	    	httpPost.addHeader("Content-Type", "application/json");
    	    	String contents = JsonUtils.convertObjToJson(params) ;
    	    	StringEntity postEntity = new StringEntity(contents, "UTF-8");
    	    	httpPost.setEntity(postEntity);
    	    	//设置请求器的配置
    	    	socketTimeout =1800000;//请求获取数据的超时时间,单位毫秒 如果访问一个接口,多少时间内无法返回数据，就直接放弃此次调用  30分钟
    	    	connectTimeout=1800000;//设置连接超时时间,单位毫秒  30分钟
    	    	RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
    		    httpPost.setConfig(requestConfig);
    		    httpClient = HttpClients.custom().build();
    	    	HttpResponse response = httpClient.execute(httpPost);
    	    	logger.info("HttpClientUtil.send： {} params: {} 返回HTTP状态码: {} 请求耗时:{}",new Object[]{url,JsonUtils.convertObjToJson(params),response.getStatusLine().getStatusCode(),System.currentTimeMillis()-start});
            	if(HttpStatus.OK.value() == response.getStatusLine().getStatusCode()){
            		String responseStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                	logger.info("HttpClientUtil.send 请求返回数据>>> responseStr:{}",responseStr) ;
                	result=(T)JsonUtils.convertJsonToObj(responseStr, clz);
            	}
    		} catch (Exception e) {
    			 logger.error("HttpClientUtil.send： {} exceptionAbstract：{} exceptionDetail：{}",new Object[]{url,e.toString(),e.getStackTrace()});
    		} finally {
                httpPost.releaseConnection();
            }
    	}
    	return result;
    }
    
    public static String HttpPost(String url, Map<String, String> paramsMap){
    	String result = null;
    	if(!StringUtils.isEmpty(url)&&null!=paramsMap&&!paramsMap.isEmpty()) {
    		HttpPost httpPost = new HttpPost(url);
            try {
            	/*httpPost.addHeader("Content-Type", "application/json");邮件发送接口特殊不能使用json定义头*/
    	        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
    	        for (Entry<String, String> entry : paramsMap.entrySet()) {
    	           nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
    	        }
    	        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
    	        connectTimeout=1200000;//设置连接超时时间,单位毫秒  20分钟
    	        socketTimeout=1200000;//请求获取数据的超时时间,单位毫秒 如果访问一个接口,多少时间内无法返回数据，就直接放弃此次调用  20分钟
    	        // 构建请求客户端
    	        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
    	        httpPost.setConfig(requestConfig);
    	        httpClient = HttpClients.custom().build();
    	        // 执行请求，获取结果
                HttpResponse response = httpClient.execute(httpPost);
                logger.info("HttpClientUtil.HttpPost： {} paramsMap: {} 返回HTTP状态码: {}",new Object[]{url,paramsMap,response.getStatusLine().getStatusCode()});
                if(HttpStatus.OK.value() == response.getStatusLine().getStatusCode()){
                     result = EntityUtils.toString(response.getEntity(), "UTF-8");
                }
            } catch (Exception e) {
                logger.error("HttpClientUtil.HttpPost： {} exceptionAbstract：{} exceptionDetail：{}",new Object[]{url,e.toString(),e.getStackTrace()});
            } finally {
                httpPost.releaseConnection();
            }
    	} else {
    		logger.error("HttpClientUtil.HttpPost params is null, url：{} paramsMap：{}",new Object[]{url,paramsMap});
    	}
        return result;
    }

}
