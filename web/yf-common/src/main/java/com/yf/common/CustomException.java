package com.yf.common;

import org.springframework.web.client.RestClientException;

/**
 * 自定义异常捕获的类
 * @author lxw
 *
 */
public class CustomException  extends RestClientException{


	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RestClientException restClientException;
	  private String body;

	  public RestClientException getRestClientException() {
	    return restClientException;
	  }

	  public void setRestClientException(RestClientException restClientException) {
	    this.restClientException = restClientException;
	  }

	  public String getBody() {
	    return body;
	  }

	  public void setBody(String body) {
	    this.body = body;
	  }


	  public CustomException(String msg, RestClientException restClientException, String body) {
	    super(msg);
	    this.restClientException = restClientException;
	    this.body = body;
	  }

	
}
