package com.iokbl.controller;

import com.iokbl.config.Constants;
import com.iokbl.model.TUserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @RequestMapping(value = "/")
    public String index(HttpServletRequest request){
        HttpSession session = request.getSession();
        TUserInfo tUserInfo = (TUserInfo)session.getAttribute(Constants.USER_SESSION_KEY);
        request.setAttribute("userInfo", tUserInfo);

        return "index";
    }

}
