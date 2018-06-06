package com.yhem.yunmirror.zuul.filter;

import javax.servlet.http.HttpServletRequest;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class AccessFilter extends ZuulFilter {

	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String username = request.getParameter("token");
		if (null != username && username.equals("laji")) {// 暂时简单化测试
			ctx.setSendZuulResponse(true);// 对该请求进行路由
			ctx.setResponseStatusCode(200);
			ctx.set("isSuccess", true);// 设值，可以在多个过滤器时使用
			return null;
		} else {
			ctx.setSendZuulResponse(false);// 过滤该请求，不对其进行路由
			ctx.setResponseStatusCode(403);// 返回错误码
			ctx.setResponseBody("{\"result\":\"Request illegal!the token is null\"}");// 返回错误内容
			ctx.set("isSuccess", false);
			return null;
		}
	}

	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public String filterType() {
		return "pre";
	}

}
