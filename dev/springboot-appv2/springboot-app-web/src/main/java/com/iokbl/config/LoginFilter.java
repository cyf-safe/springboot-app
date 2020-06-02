package com.iokbl.config;

import com.iokbl.model.TUserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@WebFilter(filterName = "loginFilter", urlPatterns = "/*")
public class LoginFilter implements Filter {

    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/login","/toLogin","/env/ping","/static/css/login.css","/static/js/login.js",
                    "/static/images/bg.jpg","/static/images/name.png","/static/images/password.png",
                    "/static/js/jquery-1.11.3.min.js")));

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();

        response.addHeader("Access-Control-Allow-Origin","*");
        try {
            String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
            boolean allowedPath = ALLOWED_PATHS.contains(path);

            // 获取session中的用户信息
            TUserInfo planUserInfo = (TUserInfo)session.getAttribute(Constants.USER_SESSION_KEY);
            String sessionId = session.getId();
            // 获取登录用户的session id
            if(planUserInfo != null && StringUtils.isNotEmpty(planUserInfo.getUser_name())
                    && redisTemplate.hasKey(planUserInfo.getUser_name())){
                sessionId = (String)redisTemplate.opsForValue().get(planUserInfo.getUser_name());
            }
            // 比较登录用户信息和session信息
            if (allowedPath || (planUserInfo != null && session.getId().equals(sessionId))) {
                chain.doFilter(req, res);
            }
            else {
                session.removeAttribute(Constants.USER_SESSION_KEY);
                response.sendRedirect("/login");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
    }

}
