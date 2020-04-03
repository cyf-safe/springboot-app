package com.iokbl.service.impl;

import com.iokbl.config.ConfigConstants;
import com.iokbl.model.TUserInfo;
import com.iokbl.model.common.ResultPage;
import com.iokbl.service.TUserInfoService;
import com.iokbl.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * XXX Service接口 
 * @author chenyufei
 * @date 2020-03-21
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * chenyufei			2020-03-21			新增
 */
@Service
public class TUserInfoServiceImpl implements TUserInfoService {
	
	private Logger logger = LoggerFactory.getLogger(TUserInfoServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ConfigConstants configConstants;

	/**
	 * 根据主键ID查询
	 * @author chenyufei 
	 * @date 2020-03-21
	 * @param 	id				主键ID	
	 * @return	TUserInfo		
	 */
	@Override
	public TUserInfo selectByPrimaryKey(String id) {
		logger.info("根据主键ID查询TUserInfo,TUserInfoServiceImpl.selectByPrimaryKey({})", id);
		TUserInfo userInfo = new TUserInfo();

		try {
			// 定义url
			String url = "http://"+configConstants.getServiceName()+"/tUserInfo/selectByPrimaryKey.htm";
			// 创建httpClient对象
			HttpClientUtil<String> httpClientUtil = new HttpClientUtil<>();
			// 调用http请求
			String result = httpClientUtil.doPost(restTemplate, url, id);

			userInfo = JsonUtils.strToJavaBean(result, TUserInfo.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userInfo;
	}

	/**
	 * 查询列表
	 * @author chenyufei 
	 * @date 2020-03-21
	 * @param 	tUserInfo		参数含义
	 * @return	List<TUserInfo>		
	 */
	@Override
	public ResultPage<TUserInfo> selectList(TUserInfo tUserInfo) {
		logger.info("查询TUserInfo列表,TUserInfoServiceImpl.selectList({})", tUserInfo);
		ResultPage<TUserInfo> page = new ResultPage<>();

		try {
			// 定义url
			String url = "http://"+configConstants.getServiceName()+"/tUserInfo/selectList.htm";
			// 创建httpClient对象
			HttpClientUtil<TUserInfo> httpClientUtil = new HttpClientUtil<>();
			// 调用http请求
			String result = httpClientUtil.doPost(restTemplate, url, tUserInfo);

			page = (ResultPage<TUserInfo>) JsonUtils.strToJavaBean(result, ResultPage.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return page;
	}

	/**
	 * 根据用户名查询用户
	 * @author chenyufei
	 * @date 2020-03-21
	 * @param 	tUserInfo		参数含义
	 * @return	List<TUserInfo>
	 */
	@Override
	public List<TUserInfo> selectUserByUserName(TUserInfo tUserInfo) {
		logger.info("查询TUserInfo列表,TUserInfoServiceImpl.selectUserByUserName({})", tUserInfo);
		List<TUserInfo> userList = new ArrayList<>();

		try {
			// 定义url
			String url = "http://"+configConstants.getServiceName()+"/tUserInfo/selectUserByUserName.htm";
			// 创建httpClient对象
			HttpClientUtil<TUserInfo> httpClientUtil = new HttpClientUtil<>();
			// 调用http请求
			String result = httpClientUtil.doPost(restTemplate, url, tUserInfo);

			userList = JsonUtils.jsonStringToList(result, TUserInfo.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userList;
	}

	/**
	 * 根据用户名查询用户
	 * @author chenyufei
	 * @date 2020-03-21
	 * @param 	user_name		参数含义
	 * @return	List<TUserInfo>
	 */
	@Override
	public List<TUserInfo> selectUserByUserName(String user_name) {
		logger.info("查询TUserInfo列表,TUserInfoServiceImpl.selectUserByUserName({})", user_name);
		List<TUserInfo> userList = new ArrayList<>();

		try {
			TUserInfo tUserInfo = new TUserInfo();
			tUserInfo.setUser_name(user_name);

			// 定义url
			String url = "http://"+configConstants.getServiceName()+"/tUserInfo/selectUserByUserName.htm";
			// 创建httpClient对象
			HttpClientUtil<TUserInfo> httpClientUtil = new HttpClientUtil<>();
			// 调用http请求
			String result = httpClientUtil.doPost(restTemplate, url, tUserInfo);

			userList = JsonUtils.jsonStringToList(result, TUserInfo.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userList;
	}

	/**
	 * 查询所有用户
	 * @author chenyufei
	 * @date 2020-03-21
	 * @return	List<TUserInfo>
	 */
	@Override
	public List<TUserInfo> selectAllUser(TUserInfo tUserInfo) {
		logger.info("查询TUserInfo列表,TUserInfoServiceImpl.selectAllUser()");
		List<TUserInfo> userList = new ArrayList<>();

		try {
			// 定义url
			String url = "http://"+configConstants.getServiceName()+"/tUserInfo/selectAllUser.htm";
			// 创建httpClient对象
			HttpClientUtil<TUserInfo> httpClientUtil = new HttpClientUtil<>();
			// 调用http请求
			String result = httpClientUtil.doPost(restTemplate, url, tUserInfo);

			userList = JsonUtils.jsonStringToList(result, TUserInfo.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userList;
	}

	/**
	 * 插入
	 * @author chenyufei 
	 * @date 2020-03-21
	 * @param 	tUserInfo		参数含义
	 * @return	int						插入成功所影响的行数			
	 */
	@Override
	public int insert(TUserInfo tUserInfo) {
		logger.info("插入TUserInfo,TUserInfoServiceImpl.insert({})", tUserInfo);
		if(tUserInfo != null){
			if(StringUtils.isNotEmpty(tUserInfo.getPassword())){
				String password = BCryptPasswordUtils.getBCryptPassword(tUserInfo.getPassword());
				tUserInfo.setPassword(password);
			}

			try {
				// 定义url
				String url = "http://"+configConstants.getServiceName()+"/tUserInfo/insert.htm";
				// 创建httpClient对象
				HttpClientUtil<TUserInfo> httpClientUtil = new HttpClientUtil<>();
				// 调用http请求
				String result = httpClientUtil.doPost(restTemplate, url, tUserInfo);

				Map<String, String> resultMap = (Map<String, String>) JsonUtils.convertJsonToObjMap(result, String.class, String.class);
				return Integer.parseInt(resultMap.get("obj"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return 0;
	}

	/**
	 * 更新
	 * @author chenyufei 
	 * @date 2020-03-21
	 * @param 	tUserInfo		参数含义
	 * @return	int						更新成功所影响的行数			
	 */
	@Override
	public int update(TUserInfo tUserInfo) {
		logger.info("更新TUserInfo,TUserInfoServiceImpl.update({})", tUserInfo);
		if(tUserInfo != null && tUserInfo.getSid() != null){
			if(StringUtils.isNotEmpty(tUserInfo.getPassword())){
				String password = BCryptPasswordUtils.getBCryptPassword(tUserInfo.getPassword());
				tUserInfo.setPassword(password);
			}
			// 定义url
			String url = "http://"+configConstants.getServiceName()+"/tUserInfo/update.htm";
			// 创建httpClient对象
			HttpClientUtil<TUserInfo> httpClientUtil = new HttpClientUtil<>();
			// 调用http请求
			String result = httpClientUtil.doPost(restTemplate, url, tUserInfo);

			Map<String, String> resultMap = (Map<String, String>) JsonUtils.convertJsonToObjMap(result, String.class, String.class);
			return Integer.parseInt(resultMap.get("obj"));
		}
		
		return 0;
	}

	/**
	 * 删除
	 * @author chenyufei 
	 * @date 2020-03-21
	 * @param 	tUserInfo		参数含义
	 * @return	int						删除成功所影响的行数			
	 */
	@Override
	public int delete(TUserInfo tUserInfo) {
		logger.info("删除TUserInfo,TUserInfoServiceImpl.delete({})", tUserInfo);
		if(tUserInfo != null && tUserInfo.getSid() != null){
			// 定义url
			String url = "http://"+configConstants.getServiceName()+"/tUserInfo/delete.htm";
			// 创建httpClient对象
			HttpClientUtil<TUserInfo> httpClientUtil = new HttpClientUtil<>();
			// 调用http请求
			String result = httpClientUtil.doPost(restTemplate, url, tUserInfo);

			Map<String, String> resultMap = (Map<String, String>) JsonUtils.convertJsonToObjMap(result, String.class, String.class);
			return Integer.parseInt(resultMap.get("obj"));
		}
		
		return 0;
	}
	
	/**
	 * 根据主键ID删除
	 * @author chenyufei 
	 * @date 2020-03-21
	 * @param 	id		主键ID
	 * @return	int		删除成功所影响的行数			
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		logger.info("根据主键ID删除TUserInfo,TUserInfoServiceImpl.deleteByPrimaryKey({})", id);
		// 定义url
		String url = "http://"+configConstants.getServiceName()+"/tUserInfo/deleteByPrimaryKey.htm";
		// 创建httpClient对象
		HttpClientUtil<String> httpClientUtil = new HttpClientUtil<>();
		// 调用http请求
		String result = httpClientUtil.doPost(restTemplate, url, id);

		Map<String, String> resultMap = (Map<String, String>) JsonUtils.convertJsonToObjMap(result, String.class, String.class);
		return Integer.parseInt(resultMap.get("obj"));
	}
	
}