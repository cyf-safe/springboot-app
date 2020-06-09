package com.iokbl.utils;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.iokbl.model.TUserInfo;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 布隆过滤器
 */
@Component
@Scope("singleton")
public class LoginBloomFilter {

    private static Logger logger = LoggerFactory.getLogger(LoginBloomFilter.class);

    private static BloomFilter<CharSequence> bloomFilter = null;

    private final static int expectedInsertions = 100000; // 期望插入的值的个数

    private final static double fpp = 0.0001; // 错误率

    static{
        if(bloomFilter == null){
            bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), expectedInsertions, fpp);
            logger.info("BloomFilter finish create!");
        }
    }

    /**
     * 设置用户名
     * @param userInfoList
     * @return
     */
    public boolean setUserByUserNames(List<TUserInfo> userInfoList){
        for(int i=0;userInfoList!=null && i<userInfoList.size();i++){
            TUserInfo planUserInfo = userInfoList.get(i);
            setUserByUserNames(planUserInfo.getUser_name());
        }

        return true;
    }

    /**
     * 设置用户名
     * @param user_name
     * @return
     */
    public boolean setUserByUserNames(String user_name){
        if(StringUtils.isNotEmpty(user_name)){
            if(!bloomFilter.mightContain(user_name)){
                bloomFilter.put(user_name);
            }
        }

        return true;
    }

    /**
     * 根据用户名判断用户是否存在
     * @param user_name
     * @return
     */
    public boolean mightContainUserByUserName(String user_name){
        if(StringUtils.isNotEmpty(user_name)){
            return bloomFilter.mightContain(user_name);
        }

        return false;
    }

    /**
     * 重新初始化过滤器
     * @return
     */
    public boolean initBloomFilter(){
        if(bloomFilter != null){
            bloomFilter = null;
        }
        bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), expectedInsertions, fpp);
        logger.info("BloomFilter finish create!");

        return true;
    }

}
