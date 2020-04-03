package com.iokbl.service;

import com.iokbl.model.TUserInfo;
import com.iokbl.model.common.ResultPage;

import java.util.List;

/**
 * XXX Service接口 
 * @author chenyufei
 * @date 2020-03-21
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * chenyufei			2020-03-21			新增
 */ 
public interface TUserInfoService {
	/**
	 * 根据主键ID查询
	 * @author chenyufei 
	 * @date 2020-03-21
	 * @param 	id				主键ID	
	 * @return	TUserInfo		
	 */
	TUserInfo selectByPrimaryKey(String id);

	/**
	 * 查询列表
	 * @author chenyufei 
	 * @date 2020-03-21
	 * @param 	tUserInfo		参数含义
	 * @return	List<TUserInfo>		
	 */
	ResultPage<TUserInfo> selectList(TUserInfo tUserInfo);

	/**
	 * 根据用户名查询用户
	 * @author chenyufei
	 * @date 2020-03-21
	 * @param 	tUserInfo		参数含义
	 * @return	List<TUserInfo>
	 */
	public List<TUserInfo> selectUserByUserName(TUserInfo tUserInfo);

	/**
	 * 根据用户名查询用户
	 * @author chenyufei
	 * @date 2020-03-21
	 * @param 	user_name		参数含义
	 * @return	List<TUserInfo>
	 */
	public List<TUserInfo> selectUserByUserName(String user_name);

	/**
	 * 查询所有用户
	 * @author chenyufei
	 * @date 2020-03-21
	 * @return	List<TUserInfo>
	 */
	public List<TUserInfo> selectAllUser(TUserInfo tUserInfo);

	/**
	 * 插入
	 * @author chenyufei 
	 * @date 2020-03-21
	 * @param 	tUserInfo		参数含义
	 * @return	int						插入成功所影响的行数			
	 */
	int insert(TUserInfo tUserInfo);

	/**
	 * 更新
	 * @author chenyufei 
	 * @date 2020-03-21
	 * @param 	tUserInfo		参数含义
	 * @return	int						更新成功所影响的行数			
	 */
	int update(TUserInfo tUserInfo);

	/**
	 * 删除
	 * @author chenyufei 
	 * @date 2020-03-21
	 * @param 	tUserInfo		参数含义
	 * @return	int						删除成功所影响的行数			
	 */
	int delete(TUserInfo tUserInfo);
	
	/**
	 * 根据主键ID删除
	 * @author chenyufei 
	 * @date 2020-03-21
	 * @param 	id		主键ID
	 * @return	int		删除成功所影响的行数			
	 */
	int deleteByPrimaryKey(String id);
	
}