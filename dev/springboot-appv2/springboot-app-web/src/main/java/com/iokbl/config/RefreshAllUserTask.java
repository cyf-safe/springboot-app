package com.iokbl.config;

import com.iokbl.model.TUserInfo;
import com.iokbl.service.TUserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class RefreshAllUserTask {

	private Logger logger = LoggerFactory.getLogger(RefreshAllUserTask.class);

	@Autowired
	private TUserInfoService tUserInfoService;

	@Autowired
	private LoginBloomFilter loginBloomFilter;

	//3.添加定时任务
	@Scheduled(cron = "0 0/1 * * * ?")
	//或直接指定时间间隔，例如：5秒
	//@Scheduled(fixedRate=5000)
	private void refreshAddUser() {
		try{
			TUserInfo tUserInfo = new TUserInfo();
			List<TUserInfo> planUserList = tUserInfoService.selectAllUser(tUserInfo);

			boolean flag = loginBloomFilter.setUserByUserNames(planUserList);
			if(flag){
				logger.info("LoginBloomFilter refresh user data!");
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Scheduled(cron = "0 0 0/12 * * ?")
	private void refreshAllUser() {
		try{
			TUserInfo tUserInfo = new TUserInfo();
			List<TUserInfo> planUserList = tUserInfoService.selectAllUser(tUserInfo);

			// 重新初始化过滤器
			boolean flag = loginBloomFilter.initBloomFilter();
			// 重新初始化数据
			flag = loginBloomFilter.setUserByUserNames(planUserList);
			if(flag){
				logger.info("LoginBloomFilter refresh user data!");
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
