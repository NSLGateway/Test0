package com.nsl.gateway.util;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;

import com.nsl.gateway.util.http.ResponseObj;
import com.nsl.gateway.util.http.impl.HttpBatchProcessor;
import com.nsl.gateway.util.http.impl.HttpDeleteProcessor;
import com.nsl.gateway.util.http.impl.HttpGetProcessor;
import com.nsl.gateway.util.http.impl.HttpPostProcessor;
import com.nsl.gateway.util.http.impl.HttpPutProcessor;

/**
 * ³z¹Lhttp client call Gateway serviceªºUtils
 * 
 * @author WSNPI05
 *
 */
public class GatewayCaller {
	private String hostname;

	private int port;

	private String scheme;

	private HttpClientContext context;

	/**
	 * 
	 * @param hostname
	 * @param port
	 * @param scheme
	 * @param userName
	 * @param password
	 */
	public GatewayCaller(String hostname, int port, String scheme, String userName, String password) {
		this.hostname = hostname;
		this.port = port;
		this.scheme = scheme;
		this.context = getHttpClientContext(hostname, port, scheme, userName, password);
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
	private HttpClientContext getHttpClientContext(String hostname, int port, String scheme, String userName,
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

	/**
	 * execute http get method
	 * 
	 * @param url
	 * @throws Exception
	 */
	public ResponseObj get(String url) throws Exception {
		return new HttpGetProcessor(scheme, hostname, port, context).process(url, null);
	}

	/**
	 * execute http post method
	 * 
	 * @param url
	 * @throws Exception
	 */
	public ResponseObj post(String url, String requestBody) throws Exception {
		return new HttpPostProcessor(scheme, hostname, port, context).process(url, requestBody);
	}

	/**
	 * execute http post method
	 * 
	 * @param url
	 * @throws Exception
	 */
	public ResponseObj batch(String url, String requestBody) throws Exception {
		return new HttpBatchProcessor(scheme, hostname, port, context).process(url, requestBody);
	}

	/**
	 * execute http put method
	 * 
	 * @param url
	 * @throws Exception
	 */
	public ResponseObj put(String url, String requestBody) throws Exception {
		return new HttpPutProcessor(scheme, hostname, port, context).process(url, requestBody);
	}

	/**
	 * execute http delete method
	 * 
	 * @param url
	 * @throws Exception
	 */
	public ResponseObj delete(String url) throws Exception {
		return new HttpDeleteProcessor(scheme, hostname, port, context).process(url, null);
	}

	public static void main(String args[]) throws Exception {
		GatewayCaller caller = new GatewayCaller("dp2wdp.nanshan.com.tw", 8888, "http", "W9006357", "Qwer1234");
		String testDataPath = "./testData/Candidate360/";

		ResponseObj response = null;

		// response =
		// caller.get("/sap/opu/odata/NSL/CANDIDATE_360_SRV/CandidateSet");
		// System.out.println(response.toString() + "\n");
		//
		// response = caller
		// .get("/sap/opu/odata/NSL/CANDIDATE_360_SRV/SearchCandidate?NameLast='Danaus
		// Tsai'&RoleType='ALL'");
		// System.out.println(response.toString() + "\n");
		//
		// response =
		// caller.get("/sap/opu/odata/NSL/CANDIDATE_360_SRV/AptitudeTestResultList?CandidateId='9000000959'");
		// System.out.println(response.toString() + "\n");

		response = caller.post("/sap/opu/odata/NSL/CANDIDATE_360_SRV/$batch",
				FileUtils.readFileToString(testDataPath, "BatchUpdate_RECRUIT_LEVEL.txt"));
		System.out.println(response.toString() + "\n");

	}
}
