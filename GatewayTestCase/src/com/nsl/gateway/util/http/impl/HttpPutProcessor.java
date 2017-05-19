package com.nsl.gateway.util.http.impl;


import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


import java.io.DataOutputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.awt.Desktop;

import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.nsl.gateway.util.http.HttpMethod;
import com.nsl.gateway.util.http.HttpUtils;
import com.nsl.gateway.util.http.ResponseObj;

public class HttpPutProcessor implements HttpMethod {

	private String scheme;

	private String hostname;

	private int port;

	private HttpClientContext context;

	public HttpPutProcessor(String scheme, String hostname, int port, HttpClientContext context) {
		this.scheme = scheme;
		this.hostname = hostname;
		this.port = port;
		this.context = context;
	
	}

	@Override
	public ResponseObj process(String url, String requestBody) throws Exception {
		//print out the information
		String fullUrl = HttpUtils.getFullUrl(this.scheme, this.hostname, this.port, url);
		System.out.println(fullUrl);
		System.out.println(requestBody);
		
		// TODO Ginny
		
		//SSO, new HttpPut
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPut request = new HttpPut(HttpUtils.relpaceSpace(fullUrl));
		
		//Cookie store
		CookieStore cookieStore = new BasicCookieStore();
		context.setCookieStore(cookieStore);
		List params = new ArrayList();
		params.add(new BasicNameValuePair("_csrf", HttpUtils.getCookieValue(cookieStore, "_csrf")));
		
		// Add any other needed post parameters
		// But I dont know what can be added...
//		UrlEncodedFormEntity paramEntity = new UrlEncodedFormEntity(params);
//		request.setEntity(paramEntity);
//
//		request.addHeader("X-Requested-With", "XMLHttpRequest");
//		request.addHeader("Accept", "application/atom+xml,application/atomsvc+xml,application/xml");
//		request.addHeader("Content-Type", "application/atom+xml;type=entry; charset=utf-8");
//		request.addHeader("Host", "dp2wdp.nanshan.com.tw");
//		request.addHeader("DataServiceVersion", "2.0");
//		request.addHeader("X-CSRF-Token", "Fetch");
//		request.addHeader("X-XHR-Logon", "accept=\"iframe\"");
//		request.addHeader("X-HTTP-Method", "POST");
//		request.addHeader("If-Match", "*");
		
		//Change request type
//		StringEntity se = new StringEntity(requestBody);
//		request.setEntity(se);
		
		//What is it?
		CloseableHttpResponse response = httpclient.execute(request, this.context);
		
//		try {
//			putUrl = new URL(url);
//		} catch (MalformedURLException exception) {
//		    exception.printStackTrace();
//		}

//		try {
//			putUrl = new URL("http://localhost:8080/putservice");
//		} catch (MalformedURLException exception) {
//		    exception.printStackTrace();
//		}
//
//
		HttpURLConnection httpURLConnection = null;
		DataOutputStream dataOutputStream = null;
		try {
		    HttpURLConnection httpCon = (HttpURLConnection) .openConnection();
		    httpCon.setDoOutput(true);
		    httpCon.setRequestMethod("PUT");
		    OutputStreamWriter out = new OutputStreamWriter(
		        httpCon.getOutputStream());
		    out.write("Resource content");
		    out.close();
		    httpCon.getInputStream();
		} catch (MalformedURLException e) {
		    e.printStackTrace();
		} catch (ProtocolException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}

	    ResponseObj responseObj = new ResponseObj();
	    responseObj.setStatus(response.getStatusLine().getStatusCode());
	    return responseObj;
	}

}
