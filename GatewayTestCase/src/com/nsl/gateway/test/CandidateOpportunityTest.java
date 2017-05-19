package com.nsl.gateway.test;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;

import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.junit.Before;
import org.junit.Test;

import com.nsl.gateway.util.XmlDiffUtils;
import com.nsl.gateway.util.http.ResponseObj;
import com.nsl.gateway.util.FileUtils;
import com.nsl.gateway.util.GatewayCaller;


public class CandidateOpportunityTest {

	private GatewayCaller caller;
	private String testDataPath;

	@Before
	public void setUp() throws Exception {
		caller = new GatewayCaller("dp2wdp.nanshan.com.tw", 8888, "http", "W9006357", "Qwer1234");
		testDataPath = "./testData/CandidateOpportunity/";
	}  

	@Test
	public void testBatchUpdateRECRUIT_LEVEL() throws Exception {
		ResponseObj response = caller.post("/sap/opu/odata/NSL/CANDIDATE_OPPORTUNITY_SRV/OpportunityDetailSet",
				FileUtils.readFileToString(testDataPath, "BatchUpdate_RECRUIT_LEVEL.txt"));
		assertEquals(response.getStatus(), 200);

		XmlDiffUtils diff = new XmlDiffUtils().ignoreAttribute("xml:base").ignoreTag("id").ignoreTag("updated")
				.printLog(false)
				.diff(response.getContent(), FileUtils.readFileToString(testDataPath, "SearchHelpSet.xml"));
		assertNull(diff.getDiff());
		System.out.println("\n");
	}

	@Test
	public void testCheckOpportunityByCandidate() throws Exception {
		ResponseObj response = caller.post("/sap/opu/odata/NSL/CANDIDATE_OPPORTUNITY_SRV/CheckOpportunityByCandidate?CandidateId='7000000551'", "");
		assertEquals(response.getStatus(), 200);

		XmlDiffUtils diff = new XmlDiffUtils()
				.diff(response.getContent(), FileUtils.readFileToString(testDataPath, "CheckOpportunityByCandidate.xml"));
		assertNull(diff.getDiff());
		System.out.println("\nb"); 
	}
	
	@Test
	public void testUpdate() throws Exception{
//		String mystr = FileUtils.readFileToString(testDataPath, "/GatewayTestCase/testData/CandidateOpportunity/UpdateOpportunityDetail.xml");
//		byte[] arr = System.Text.Encoding.UTF8.GetBytes(mystr);
//		HttpPut request = new HttpPut("/sap/opu/odata/NSL/CANDIDATE_OPPORTUNITY_SRV/OpportunityDetailSet(guid'00505697-6D81-1ED5-ABD3-4FF097843AE0')");
//		request.setEntity(new StringEntity(mystr));
	
		ResponseObj response = caller.put("/sap/opu/odata/NSL/CANDIDATE_OPPORTUNITY_SRV/OpportunityDetailSet(guid'00505697-6D81-1ED5-ABD3-4FF097843AE0')",
				FileUtils.readFileToString(testDataPath, "UpdateOpportunityDetail.xml"));

				
				
		assertEquals(response.getStatus(), 200);

		XmlDiffUtils diff = new XmlDiffUtils()
				.ignoreAttribute("xml:base").ignoreTag("id").ignoreTag("updated")
				.printLog(false)
				.diff(response.getContent(), FileUtils.readFileToString(testDataPath, ""));
		assertNull(diff.getDiff());
		
		System.out.println("\n");
	}

}
