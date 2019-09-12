package com.yf.cloud.aspect;

import java.util.Arrays;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;

@Aspect  //@Aspect将一个类定义为一个切面类
//申明是个spring管理的bean
@Component
@Order(1) //标记切面类的处理优先级,i值越小,优先级别越高.PS:可以注解类,也能注解到方法上
public class AspectForLogging {
	  protected static final Logger logger = LoggerFactory.getLogger(AspectForLogging.class);
	  private Gson gson = new Gson(); 
	  /*ThreadLocal<Long> startTime = new ThreadLocal<Long>();*/
	  //申明一个切点 里面是 execution表达式
	  @Pointcut("execution(public * com.yf.cloud.controller..*Controller.*(..))")
	  private void controllerAspect(){}
	  //请求method前打印内容
	  @Before(value = "controllerAspect()")
	  public void methodBefore(JoinPoint joinPoint){
	   /*startTime.set(System.currentTimeMillis());*/
	   ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	   HttpServletRequest request = (HttpServletRequest) requestAttributes.getRequest();
	   //打印请求内容
	   logger.info("请求地址:"+request.getRequestURL().toString());
	   logger.info("请求api:"+request.getServletPath());
	   logger.info("请求方式:"+request.getMethod());
	   logger.info("请求类方法:"+joinPoint.getSignature());
	   logger.info("请求类方法参数:"+ Arrays.toString(joinPoint.getArgs()));
	   /*logger.info("请求处理时间为:"+(System.currentTimeMillis() - startTime.get()));*/
	  }
     /*
     * 再 controller调用结束之后的返回结果
     * @param object result
     */
	  @AfterReturning(returning = "o",pointcut = "controllerAspect()")
	  public void methodAfterReturing(Object o ){
		  logger.info("Response内容:"+gson.toJson(o));
	  }
}
