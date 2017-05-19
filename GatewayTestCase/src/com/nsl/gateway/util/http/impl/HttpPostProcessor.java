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

public class HttpPostProcessor implements HttpMethod {

	private String scheme;

	private String hostname;

	private int port;

	private HttpClientContext context;

	public HttpPostProcessor(String scheme, String hostname, int port, HttpClientContext context) {
		this.scheme = scheme;
		this.hostname = hostname;
		this.port = port;
		this.context = context;
	}

	@Override
	public ResponseObj process(String url, String requestBody) throws Exception {
		String fullUrl = HttpUtils.getFullUrl(this.scheme, this.hostname, this.port, url);
		System.out.println(fullUrl);
		System.out.println(requestBody);

		// TODO by Jason
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost request = new HttpPost(HttpUtils.relpaceSpace(fullUrl));

		CookieStore cookieStore = new BasicCookieStore();
		context.setCookieStore(cookieStore);
		List params = new ArrayList();
		params.add(new BasicNameValuePair("_csrf", HttpUtils.getCookieValue(cookieStore, "_csrf")));

		// Add any other needed post parameters
		UrlEncodedFormEntity paramEntity = new UrlEncodedFormEntity(params);
		request.setEntity(paramEntity);

		request.addHeader("X-Requested-With", "XMLHttpRequest");
		request.addHeader("Accept", "application/atom+xml,application/atomsvc+xml,application/xml");
		request.addHeader("Content-Type", "application/atom+xml;type=entry; charset=utf-8");
		request.addHeader("Host", "dp2wdp.nanshan.com.tw");
		request.addHeader("DataServiceVersion", "2.0");
		request.addHeader("X-CSRF-Token", "Fetch");
		request.addHeader("X-XHR-Logon", "accept=\"iframe\"");
		request.addHeader("X-HTTP-Method", "POST");
		request.addHeader("If-Match", "*");
		// request.addHeader("X-RequestDigest", FormDigestValue);

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
