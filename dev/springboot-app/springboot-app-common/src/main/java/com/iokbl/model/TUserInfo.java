package com.iokbl.model;

import com.iokbl.model.common.PagePara;

import java.io.Serializable;

/**
 * XXX MODEL 
 * @author chenyufei
 * @date 2020-04-26
 * =================Modify Record=================
 * @Modifier			@date			@Content
 * chenyufei			2020-04-26			新增
 */ 
public class TUserInfo extends PagePara implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键ID */
	private Long sid;

	/** 用户ID */
	private String user_id;

	/** 用户名称 */
	private String user_name;

	/** 密码 */
	private String password;

	/** 真实名称 */
	private String real_name;

	/** 是否管理员 1、是 0、否 */
	private String isManager;

	/** 状态 1、有效 0、无效 */
	private String status;

	/** 创建人 */
	private String insert_user;

	/** 创建时间 */
	private java.util.Date insert_time;

	/** 修改人 */
	private String update_user;

	/** 修改时间 */
	private java.util.Date update_time;

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public String getUser_id() {
		return this.user_id;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	public String getUser_name() {
		return this.user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public String getIsManager() {
		return isManager;
	}

	public void setIsManager(String isManager) {
		this.isManager = isManager;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setInsert_user(String insert_user) {
		this.insert_user = insert_user;
	}
	
	public String getInsert_user() {
		return this.insert_user;
	}

	public void setInsert_time(java.util.Date insert_time) {
		this.insert_time = insert_time;
	}
	
	public java.util.Date getInsert_time() {
		return this.insert_time;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	
	public String getUpdate_user() {
		return this.update_user;
	}

	public void setUpdate_time(java.util.Date update_time) {
		this.update_time = update_time;
	}
	
	public java.util.Date getUpdate_time() {
		return this.update_time;
	}

}