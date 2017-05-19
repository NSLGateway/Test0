package com.nsl.gateway.util.http;

public interface HttpMethod {
	public ResponseObj process(String url, String requestBody) throws Exception;
}
