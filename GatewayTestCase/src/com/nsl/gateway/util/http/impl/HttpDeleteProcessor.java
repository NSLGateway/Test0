package com.nsl.gateway.util.http.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.nsl.gateway.util.http.HttpMethod;
import com.nsl.gateway.util.http.HttpUtils;
import com.nsl.gateway.util.http.ResponseObj;

public class HttpDeleteProcessor implements HttpMethod {

	private String scheme;

	private String hostname;

	private int port;

	private HttpClientContext context;

	public HttpDeleteProcessor(String scheme, String hostname, int port, HttpClientContext context) {
		this.scheme = scheme;
		this.hostname = hostname;
		this.port = port;
		this.context = context;
	}

	@Override
	public ResponseObj process(String url, String requestBody) throws Exception {
		// TODO Ginny
		return null;
	}

}
