package com.iokbl.controller;

import com.iokbl.config.Constants;
import com.iokbl.model.TUserInfo;
import com.iokbl.model.common.ResultPage;
import com.iokbl.model.common.ResultUtil;
import com.iokbl.service.TUserInfoService;
import com.iokbl.utils.DataUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * XXX Controller接口
 * @author chenyufei
 * @date 2020-04-26
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * chenyufei			2020-04-26			新增
 */
@RequestMapping("/tUserInfo")
@RestController
public class TUserInfoController {

	private Logger logger = LoggerFactory.getLogger(TUserInfoController.class);

	@Autowired
	private TUserInfoService tUserInfoService;

	/**
	 * 根据主键ID查询
	 * @author chenyufei
	 * @date 2020-04-26
	 * @param 	sid				主键ID
	 * @return	TUserInfo
	 */
	@RequestMapping(value = "/selectByPrimaryKey.htm", method = RequestMethod.POST)
	public Map<String,Object> selectByPrimaryKey(@RequestBody String sid) {
		logger.info("接收到请求 ========> 根据主键ID查询TUserInfo,{}", sid);
		try {
			TUserInfo tUserInfo = tUserInfoService.selectByPrimaryKey(sid);
			if(tUserInfo != null){
				tUserInfo.setPassword(null);
			}
			return ResultUtil.creatSuccessResult(tUserInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.creatErrorResult(e.getMessage());
		}
	}

	/**
	 * 查询列表
	 * @author chenyufei
	 * @date 2020-04-26
	 * @param 	tUserInfo		参数含义
	 * @return	List<TUserInfo>
	 */
	@RequestMapping(value = "/selectList.htm", method = RequestMethod.POST)
	public Map<String,Object> selectList(@RequestBody TUserInfo tUserInfo
			, HttpServletRequest request) {
		logger.info("接收到请求 ========> 查询列表TUserInfo,{}", tUserInfo);

		ResultPage<TUserInfo> planUserInfoList = null;
		try {
			planUserInfoList = tUserInfoService.selectList(tUserInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.creatErrorResult(e.getMessage());
		}

		return ResultUtil.creatSuccessResult(planUserInfoList);
	}

	/**
	 * 根据用户名查询用户
	 * @author chenyufei
	 * @date 2020-04-26
	 * @param 	tUserInfo		参数含义
	 * @return	List<TUserInfo>
	 */
	@RequestMapping(value = "/selectUserByUserName.htm", method = RequestMethod.POST)
	public Map<String,Object> selectUserByUserName(@RequestBody TUserInfo tUserInfo) {
		logger.info("接收到请求 ========> 根据用户名查询用户TUserInfo,{}", tUserInfo);

		List<TUserInfo> planUserInfoList = null;
		try {
			planUserInfoList = tUserInfoService.selectUserByUserName(tUserInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.creatErrorResult(e.getMessage());
		}

		return ResultUtil.creatSuccessResult(planUserInfoList);
	}

	/**
	 * 根据用户名查询用户
	 * @author chenyufei
	 * @date 2020-03-13
	 * @param 	tUserInfo		参数含义
	 * @return	List<TPlanUserInfo>
	 */
	@RequestMapping(value = "/queryUserByUserName.htm", method = RequestMethod.POST)
	public List<TUserInfo> queryUserByUserName(@RequestBody TUserInfo tUserInfo) {
		logger.info("接收到请求 ========> 根据用户名查询用户TUserInfo,{}", tUserInfo);

		List<TUserInfo> planUserInfoList = null;
		try {
			planUserInfoList = tUserInfoService.selectUserByUserName(tUserInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return planUserInfoList;
	}

	/**
	 * 查询所有用户
	 * @author chenyufei
	 * @date 2020-04-26
	 * @return	List<TUserInfo>
	 */
	@RequestMapping(value = "/selectAllUser.htm", method = RequestMethod.POST)
	public Map<String,Object> selectAllUser(@RequestBody TUserInfo tUserInfo) {
		logger.info("接收到请求 ========> 查询所有用户TUserInfo,{}", tUserInfo);

		List<TUserInfo> planUserInfoList = null;
		try {
			planUserInfoList = tUserInfoService.selectAllUser(tUserInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.creatErrorResult(e.getMessage());
		}

		return ResultUtil.creatSuccessResult(planUserInfoList);
	}

	/**
	 * 插入
	 * @author chenyufei
	 * @date 2020-04-26
	 * @param 	tUserInfo		参数含义
	 * @return	int						插入成功所影响的行数
	 */
	@RequestMapping(value = "/insert.htm", method = RequestMethod.POST)
	public Map<String,Object> insert(@RequestBody TUserInfo tUserInfo) {
		logger.info("接收到请求 ========> 插入TUserInfo,{}", tUserInfo);
		try {
			int result = tUserInfoService.insert(tUserInfo);

			return ResultUtil.creatSuccessResult(result);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.creatErrorResult(e.getMessage());
		}
	}

	/**
	 * 更新
	 * @author chenyufei
	 * @date 2020-04-26
	 * @param 	tUserInfo		参数含义
	 * @return	int						更新成功所影响的行数
	 */
	@RequestMapping(value = "/update.htm", method = RequestMethod.POST)
	public Map<String,Object> update(@RequestBody TUserInfo tUserInfo) {
		logger.info("接收到请求 ========> 更新TUserInfo,{}", tUserInfo);
		try {
			int result = tUserInfoService.update(tUserInfo);

			return ResultUtil.creatSuccessResult(result);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.creatErrorResult(e.getMessage());
		}
	}

	/**
	 * 删除
	 * @author chenyufei
	 * @date 2020-04-26
	 * @param 	tUserInfo		参数含义
	 * @return	int						删除成功所影响的行数
	 */
	@RequestMapping(value = "/delete.htm", method = RequestMethod.POST)
	public Map<String,Object> delete(@RequestBody TUserInfo tUserInfo) {
		logger.info("接收到请求 ========> 删除TUserInfo,{}", tUserInfo);
		try {
			int result = tUserInfoService.delete(tUserInfo);

			return ResultUtil.creatSuccessResult(result);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.creatErrorResult(e.getMessage());
		}
	}

}