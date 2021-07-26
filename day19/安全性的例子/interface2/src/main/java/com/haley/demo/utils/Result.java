package com.haley.demo.utils;

import com.haley.demo.constant.CommonConstant;
import lombok.Data;

import java.io.Serializable;


@Data
public class Result<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 成功标志
	 */
	private boolean success = true;

	/**
	 * 返回处理消息
	 */
	private String message = "操作成功！";

	/**
	 * 返回代码
	 */
	private Integer code = 0;

	/**
	 * 返回数据对象 data
	 */
	private T result;

	/**
	 * 时间戳
	 */
	private long timestamp = System.currentTimeMillis();

	public Result() {

	}

	public com.haley.demo.utils.Result<T> success(String message) {
		this.message = message;
		this.code = CommonConstant.SC_OK_200;
		this.success = true;
		return this;
	}


	public static com.haley.demo.utils.Result<Object> ok() {
		com.haley.demo.utils.Result<Object> r = new com.haley.demo.utils.Result<Object>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setMessage("成功");
		return r;
	}

	public static com.haley.demo.utils.Result<Object> ok(String msg) {
		com.haley.demo.utils.Result<Object> r = new com.haley.demo.utils.Result<Object>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setMessage(msg);
		return r;
	}

	public static com.haley.demo.utils.Result<Object> ok(Object data) {
		com.haley.demo.utils.Result<Object> r = new com.haley.demo.utils.Result<Object>();
		r.setSuccess(true);
		r.setCode(CommonConstant.SC_OK_200);
		r.setResult(data);
		return r;
	}

	public static com.haley.demo.utils.Result<Object> error(String msg) {
		return error(CommonConstant.SC_INTERNAL_SERVER_ERROR_500, msg);
	}

	public static com.haley.demo.utils.Result<Object> error(int code, String msg) {
		com.haley.demo.utils.Result<Object> r = new com.haley.demo.utils.Result<Object>();
		r.setCode(code);
		r.setMessage(msg);
		r.setSuccess(false);
		return r;
	}

	public com.haley.demo.utils.Result<T> error500(String message) {
		this.message = message;
		this.code = CommonConstant.SC_INTERNAL_SERVER_ERROR_500;
		this.success = false;
		return this;
	}
	/**
	 * 无权限访问返回结果
	 */
	public static com.haley.demo.utils.Result<Object> noauth(String msg) {
		return error(CommonConstant.SC_JEECG_NO_AUTHZ, msg);
	}
}
