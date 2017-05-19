package com.nsl.gateway.util.http.impl;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.nsl.gateway.util.http.HttpMethod;
import com.nsl.gateway.util.http.HttpUtils;
import com.nsl.gateway.util.http.ResponseObj;

public class HttpGetProcessor implements HttpMethod {

	private String scheme;

	private String hostname;

	private int port;

	private HttpClientContext context;

	public HttpGetProcessor(String scheme, String hostname, int port, HttpClientContext context) {
		this.scheme = scheme;
		this.hostname = hostname;
		this.port = port;
		this.context = context;
	}

	@Override
	public ResponseObj process(String url, String requestBody) throws Exception {
		String fullUrl = HttpUtils.getFullUrl(this.scheme, this.hostname, this.port, url);
		System.out.println(fullUrl);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet request = new HttpGet(HttpUtils.relpaceSpace(fullUrl));
		CloseableHttpResponse response = httpclient.execute(request, this.context);

		try {
			HttpEntity entity = response.getEntity();
			String responseString = EntityUtils.toString(entity, "big5");

			ResponseObj responseObj = new ResponseObj();
			responseObj.setStatus(response.getStatusLine().getStatusCode());
			responseObj.setContentLength(entity.getContentLength());
			responseObj.setContentType(entity.getContentType().getValue());
			responseObj.setContent(responseString);
			return responseObj;
		} catch (Exception e) {
			throw e;
		} finally {
			response.close();
		}
	}

}
