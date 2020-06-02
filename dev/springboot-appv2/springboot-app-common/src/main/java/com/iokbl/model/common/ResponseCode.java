package com.iokbl.model.common;

/**
 * @desc 返回结果枚举值
 * @author chenyufei
 * @date 2018年10月18日16:35:49
 */
public class ResponseCode {
	public enum ResultCode{
		SUCCESS("00100000","成功"),
		ERROR("00100001","网络连接异常，请稍后再试"),
		FAIL("00100005","参数异常，请检查");

		private String resultCode;
		private String message;
		
		private ResultCode() {};
		private ResultCode(String resultCode,String message) {
			this.resultCode = resultCode;
			this.message = message;
		}
		public String getResultCode() {
			return resultCode;
		}
		public void setResultCode(String resultCode) {
			this.resultCode = resultCode;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		
	}
}
