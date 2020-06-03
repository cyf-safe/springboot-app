package com.iokbl.config;

import com.iokbl.model.TUserInfo;
import com.iokbl.service.TUserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 启动加载类
 */
@Component
public class InitApplicationRunner implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(InitApplicationRunner.class);

    @Autowired
    private TUserInfoService tUserInfoService;

    @Autowired
    private LoginBloomFilter loginBloomFilter;

    @Override
    public void run(ApplicationArguments var1){
        try{
            TUserInfo tUserInfo = new TUserInfo();
            List<TUserInfo> planUserList = tUserInfoService.selectAllUser(tUserInfo);

            boolean flag = loginBloomFilter.setUserByUserNames(planUserList);
            if(flag){
                logger.info("LoginBloomFilter first finish init data!");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
