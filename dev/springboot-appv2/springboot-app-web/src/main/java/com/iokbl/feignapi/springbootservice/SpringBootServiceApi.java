package com.iokbl.feignapi.springbootservice;

import com.iokbl.config.DemoServicesFeignConfig;
import com.iokbl.model.TUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient(name = "springboot-service", configuration = DemoServicesFeignConfig.class)
public interface SpringBootServiceApi {

    /**
     * 根据用户名查询用户
     * @author chenyufei
     * @date 2020-03-13
     * @param 	tUserInfo		参数含义
     * @return	List<TUserInfo>
     */
    @RequestMapping(value = "/tUserInfo/queryUserByUserName.htm")
    List<TUserInfo> queryUserByUserName(@RequestBody TUserInfo tUserInfo);

}
