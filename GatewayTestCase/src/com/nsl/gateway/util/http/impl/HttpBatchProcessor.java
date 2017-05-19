package com.nsl.gateway.util.http.impl;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.nsl.gateway.util.http.HttpMethod;
import com.nsl.gateway.util.http.HttpUtils;
import com.nsl.gateway.util.http.ResponseObj;

public class HttpBatchProcessor implements HttpMethod {

	private String scheme;

	private String hostname;

	private int port;

	private HttpClientContext context;

	public HttpBatchProcessor(String scheme, String hostname, int port, HttpClientContext context) {
		this.scheme = scheme;
		this.hostname = hostname;
		this.port = port;
		this.context = context;
	}

	@Override
	public ResponseObj process(String url, String requestBody) throws Exception {
		// TODO by Jason
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String fullUrl = HttpUtils.getFullUrl(this.scheme, this.hostname, this.port, url);
		System.out.println(fullUrl);
		System.out.println(requestBody);
		HttpPost request = new HttpPost(HttpUtils.relpaceSpace(fullUrl));
		request.addHeader("Content-type", "multipart/mixed;boundary=batch");
		request.addHeader("X-Requested-With", "XMLHttpRequest");
		request.addHeader("Accept", "application/atom+xml,application/atomsvc+xml,application/xml");
		request.addHeader("Content-Type", "application/atom+xml");

		// request.addHeader("Content-Type",
		// "application/x-www-form-urlencoded");

		request.addHeader("DataServiceVersion", "2.0");
		request.addHeader("X-CSRF-Token", "Fetch");
		// request.addHeader("Accept", "application/json;odata=verbose");
		// request.addHeader("Content-type", "application/json;odata=verbose");
		// request.addHeader("X-HTTP-Method", "POST");
		StringEntity se = new StringEntity(requestBody);
		request.setEntity(se);

		// List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		// nvps.add(new BasicNameValuePair("username", "W9006357"));
		// nvps.add(new BasicNameValuePair("password", "Qwer1234"));
		// request.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse response = httpclient.execute(request, this.context);

		try {
			System.out.println(response.getStatusLine());
			HttpEntity entity = response.getEntity();
			String responseString = EntityUtils.toString(entity, "big5");
			EntityUtils.consume(entity);

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
