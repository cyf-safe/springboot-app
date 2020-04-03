package com.iokbl.service.impl;

import com.iokbl.model.TUserInfo;
import com.iokbl.model.common.ResultPage;
import com.iokbl.persistence.TUserInfoMapper;
import com.iokbl.service.TUserInfoService;
import com.iokbl.utils.DataUtils;
import com.iokbl.utils.MD5;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

	@Resource
	private TUserInfoMapper tUserInfoMapper;

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
		TUserInfo planUserInfo = tUserInfoMapper.selectByPrimaryKey(id);
		return planUserInfo;
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

		long count = tUserInfoMapper.selectCount(tUserInfo);
		tUserInfo.setCount(count);

		List<TUserInfo> list = new ArrayList<>();
		if(count > 0){
			list = tUserInfoMapper.selectList(tUserInfo);
		}

		page.setPageSize(tUserInfo.getPageSize());
		page.setCurrentPage(tUserInfo.getCurrentPage());
		page.setCount(count);
		page.setList(list);
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
		return tUserInfoMapper.selectUserByUserName(tUserInfo);
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
		return tUserInfoMapper.selectAllUser(tUserInfo);
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
				tUserInfo.setPassword(MD5.getMD5(tUserInfo.getPassword()));
			}
			int result =  tUserInfoMapper.insert(tUserInfo);

			tUserInfo.setUser_id(DataUtils.getId("U", tUserInfo.getSid()));
			result = tUserInfoMapper.update(tUserInfo);
			return result;
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
				tUserInfo.setPassword(MD5.getMD5(tUserInfo.getPassword()));
			}
			return tUserInfoMapper.update(tUserInfo);
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
			return tUserInfoMapper.delete(tUserInfo);
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
		return tUserInfoMapper.deleteByPrimaryKey(id);
	}
	
}