package com.iokbl.controller;

import com.iokbl.config.ConfigConstants;
import com.iokbl.config.Constants;
import com.iokbl.utils.LoginBloomFilter;
import com.iokbl.feignapi.springbootservice.SpringBootServiceApi;
import com.iokbl.model.TUserInfo;
import com.iokbl.service.TUserInfoService;
import com.iokbl.utils.BCryptPasswordUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class LoginController {

    @Autowired
    private SpringBootServiceApi springBootServiceApi;

    @Autowired
    private TUserInfoService tUserInfoService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private LoginBloomFilter loginBloomFilter;

    @Autowired
    private ConfigConstants configConstants;

    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request){
        return "login";
    }

    @RequestMapping(value = "/toLogin")
    @ResponseBody
    public String toLogin(String user_name, String password,
                          HttpServletRequest request){
        if(StringUtils.isNotEmpty(user_name) && StringUtils.isNotEmpty(password)
            && loginBloomFilter.mightContainUserByUserName(user_name)){
            HttpSession session = request.getSession();
            TUserInfo userInfo = new TUserInfo();
            userInfo.setUser_name(user_name);
            List<TUserInfo> planUserList = springBootServiceApi.queryUserByUserName(userInfo);
            if(planUserList.size() == 1){
                userInfo = planUserList.get(0);

                if(BCryptPasswordUtils.matches(password, userInfo.getPassword())){
                    redisTemplate.opsForValue().set(userInfo.getUser_name(), session.getId(),
                            configConstants.getLoginTimeoutMinutes(), TimeUnit.MINUTES);
                    session.setAttribute(Constants.USER_SESSION_KEY,userInfo);
                    session.setMaxInactiveInterval(30*60);
                    return "success";
                }
            }
        }

        return "error";
    }

    @RequestMapping(value = "/login_out")
    public String login_out(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute(Constants.USER_SESSION_KEY);
        return "login";
    }

    @RequestMapping(value = "/selectUserByUserName")
    @ResponseBody
    public List<TUserInfo> selectUserByUserName(String user_name){
        if(StringUtils.isNotEmpty(user_name)){
            return tUserInfoService.selectUserByUserName(user_name);
        }

        return null;
    }

}
