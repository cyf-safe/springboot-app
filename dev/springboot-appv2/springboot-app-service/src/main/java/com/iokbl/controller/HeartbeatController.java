package com.iokbl.controller;

import com.iokbl.model.common.ResultUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/env")
public class HeartbeatController {

	private static Logger logger = LoggerFactory.getLogger(HeartbeatController.class);

	@RequestMapping("/ping")
	@ResponseBody
	@HystrixCommand(fallbackMethod = "fallback")
	public Map<String,Object> ping(HttpServletRequest request){
		logger.info("正在检测plan-web应用是否正常运行");

		try {
			String strBackUrl = "http://" + request.getServerName() //服务器地址
					+ ":" + request.getServerPort() + request.getContextPath() ;

			logger.info(strBackUrl+" 正常运行");

			return ResultUtil.creatSuccessResult(strBackUrl+" 正常运行");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.creatErrorResult(e.getMessage());
		}
	}

	/**
	 * 方法simpleHystrixClientCall的回退方法，可以指定将hystrix执行失败异常传入到方法中
	 * @param request hystrix执行失败的异常对象
	 * @param t
	 * @return
	 */
	public Map<String,Object> fallback(HttpServletRequest request, Throwable t) {
		String msg = "服务拥挤，请稍后再试！";
		return ResultUtil.creatErrorResult(msg);
	}

}
