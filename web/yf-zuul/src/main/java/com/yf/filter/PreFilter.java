package com.yf.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class PreFilter  extends ZuulFilter{
	private Logger logger = LoggerFactory.getLogger(PreFilter.class);
	//过滤器的类型，它决定过滤器在请求的哪个生命周期中执行，这里定义为pre，代表会在请求被路由之前执行。
    @Override
    public String filterType() {
        return "pre";
    }
	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        logger.info("send {} request to {}",request.getMethod(),request.getRequestURI().toString());

        Object accessToken = request.getParameter("accessToken");
        if(null !=accessToken&&accessToken.equals("123")){
        	ctx.setSendZuulResponse(true);// 对该请求进行路由
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess", true);// 设值，让下一个Filter看到上一个Filter的状态
            return null;
        }else{
        	 ctx.setSendZuulResponse(false); //令zuul过滤该请求，不对其进行路由
             ctx.setResponseStatusCode(401); //设置返回的错误码
             ctx.setResponseBody("{\"result\":\"accessToken is not correct!\"}");// 返回错误内容
             ctx.set("isSuccess", false);
             return null;
        }
	}

	//判断该过滤器是否需要被执行。这里我们直接返回了true，因此该过滤器对所有的请求都生效。实际运行中我们可以利用该函数
    //来指定过滤器的有效范围。
    @Override
    public boolean shouldFilter() {
        return true;
    }

	//过滤器的执行顺序。当请求在一个阶段中存在多个过滤器时，需要根据该方法返回的值来依次执行
    @Override
    public int filterOrder() {
        return 0;// 优先级为0，数字越大，优先级越低
    }
}
