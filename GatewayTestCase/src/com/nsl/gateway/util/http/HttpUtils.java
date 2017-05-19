package com.nsl.gateway.util.http;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;

public class HttpUtils {

	/**
	 * get full URL
	 * 
	 * @param scheme
	 * @param hostname
	 * @param port
	 * @param url
	 * @return
	 */
	public static String getFullUrl(String scheme, String hostname, int port, String url) {
		return scheme + "://" + hostname + ":" + port + url;
	}

	/**
	 * 需替換URL內的空白為%20, 才能被http client執行
	 * 
	 * @param url
	 * @return
	 */
	public static String relpaceSpace(String url) {
		return url.replaceAll(" ", "%20");
	}

	/**
	 * set authorization information to pass Single Sign On (SSO)
	 * 
	 * @param hostname
	 * @param port
	 * @param scheme
	 * @param userName
	 * @param password
	 * @return
	 */
	public static HttpClientContext getHttpClientContext(String scheme, String hostname, int port, String userName,
			String password) {
		// Add AuthCache to the execution context
		HttpClientContext context = HttpClientContext.create();

		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope(hostname, port),
				new UsernamePasswordCredentials(userName, password));
		context.setCredentialsProvider(credsProvider);

		// Generate BASIC scheme object and add it to the local auth cache
		AuthCache authCache = new BasicAuthCache();
		authCache.put(new HttpHost(hostname, port, scheme), new BasicScheme());
		context.setAuthCache(authCache);

		return context;
	}

	public static String getCookieValue(CookieStore cookieStore, String cookieName) {
		String value = null;
		for (Cookie cookie : cookieStore.getCookies()) {
			if (cookie.getName().equals(cookieName)) {
				value = cookie.getValue();
				break;
			}
		}
		return value;
	}

}
