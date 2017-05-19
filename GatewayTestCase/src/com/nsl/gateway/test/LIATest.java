package com.nsl.gateway.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nsl.gateway.util.FileUtils;
import com.nsl.gateway.util.GatewayCaller;
import com.nsl.gateway.util.XmlDiffUtils;
import com.nsl.gateway.util.http.ResponseObj;

public class LIATest {

	private GatewayCaller caller;
	private String testDataPath;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	
		caller = new GatewayCaller("dp2wdp.nanshan.com.tw", 8888, "http", "W9011441", "Ginnyabcdefg123");
		testDataPath = "./testData/LIA/";
	}

	@After
	public void tearDown() throws Exception {
	}
	
//Get
	@Test
	public void testSearchHelpSet() throws Exception {
		ResponseObj response = caller.get("/sap/opu/odata/NSL/LIA_TEST_REGIS_ONBOARD_SRV/");
		assertEquals(response.getStatus(), 200);

		XmlDiffUtils diff = new XmlDiffUtils().ignoreAttribute("xml:base").ignoreTag("id").ignoreTag("updated")
				.printLog(false)
				.diff(response.getContent(), FileUtils.readFileToString(testDataPath, "SearchHelpSet.xml"));
		assertNull(diff.getDiff());
		System.out.println("\n");
	}

	@Test
	public void test() throws Exception{
		ResponseObj response = caller.post("/sap/opu/odata/NSL/LIA_TEST_REGIS_ONBOARD_SRV/",
				FileUtils.readFileToString(testDataPath, "BatchUpdate_Candidate.txt"));
		assertEquals(response.getStatus(), 200);

		XmlDiffUtils diff = new XmlDiffUtils()
				.ignoreAttribute("xml:base").ignoreTag("id").ignoreTag("updated")
				.printLog(false)
				.diff(response.getContent(), FileUtils.readFileToString(testDataPath, "SearchHelpSet.xml"));
		assertNull(diff.getDiff());
		System.out.println("\n");
	}


}
