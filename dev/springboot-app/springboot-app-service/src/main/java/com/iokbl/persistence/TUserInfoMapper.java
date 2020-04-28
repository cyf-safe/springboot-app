package com.iokbl.persistence;

import com.iokbl.model.TUserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * XXX Dao接口实现 
 * @author chenyufei
 * @date 2020-04-26
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * chenyufei			2020-04-26			新增
 */ 
@Repository
public interface TUserInfoMapper {
	
	/**
	 * 根据主键ID查询
	 * @author chenyufei 
	 * @date 2020-04-26
	 * @param 	id				主键ID	
	 * @return	TUserInfo		
	 */
	public TUserInfo selectByPrimaryKey(String id);

	/**
	 * 查询列表数据条数
	 * @author chenyufei
	 * @date 2020-04-26
	 * @param 	tUserInfo		参数含义
	 * @return	Long
	 */
	public Long selectCount(TUserInfo tUserInfo);

	/**
	 * 查询列表
	 * @author chenyufei 
	 * @date 2020-04-26
	 * @param 	tUserInfo		参数含义
	 * @return	List<TUserInfo>		
	 */
	public List<TUserInfo> selectList(TUserInfo tUserInfo);

	/**
	 * 根据用户名查询用户
	 * @author chenyufei
	 * @date 2020-04-26
	 * @param 	tUserInfo		参数含义
	 * @return	List<TUserInfo>
	 */
	public List<TUserInfo> selectUserByUserName(TUserInfo tUserInfo);

	/**
	 * 查询所有用户
	 * @author chenyufei
	 * @date 2020-04-26
	 * @return	List<TUserInfo>
	 */
	public List<TUserInfo> selectAllUser(TUserInfo tUserInfo);

	/**
	 * 插入
	 * @author chenyufei 
	 * @date 2020-04-26
	 * @param 	tUserInfo		参数含义
	 * @return	int						插入成功所影响的行数			
	 */
	public int insert(TUserInfo tUserInfo);

	/**
	 * 批量插入
	 * @author chenyufei 
	 * @date 2020-04-26
	 * @param	tUserInfoList		参数含义
	 */
	public void batchInsert(List<TUserInfo> tUserInfoList);

	/**
	 * 更新
	 * @author chenyufei 
	 * @date 2020-04-26
	 * @param 	tUserInfo		参数含义
	 * @return	int						更新成功所影响的行数			
	 */
	public int update(TUserInfo tUserInfo);

	/**
	 * 删除
	 * @author chenyufei 
	 * @date 2020-04-26
	 * @param 	tUserInfo		参数含义
	 * @return	int						删除成功所影响的行数			
	 */
	public int delete(TUserInfo tUserInfo);
	
	/**
	 * 根据主键ID删除
	 * @author chenyufei 
	 * @date 2020-04-26
	 * @param 	id		主键ID
	 * @return	int		删除成功所影响的行数			
	 */
	public int deleteByPrimaryKey(String id);
	
}