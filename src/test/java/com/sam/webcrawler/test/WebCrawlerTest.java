package com.sam.webcrawler.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

import com.sam.model.WebCrawlerResponse;
import com.sam.threadpool.WebCrawlerThreadPool;
import com.sam.webcrawler.WebCrawlerBootstrap;

public class WebCrawlerTest {
	
	private WebCrawlerResponse webCrawlerResponse;
	
	@After
	public void afterEachTest() {
		webCrawlerResponse.cleanUp();
	}

	@Test
	public void testPage01() {
		
		WebCrawlerBootstrap webCrawlerBootstrap = new WebCrawlerBootstrap();
		String args[] = {"C:/config/internet.json", 
				"0",
				"page-01"};
		WebCrawlerThreadPool webCrawlerThreadPool = WebCrawlerThreadPool.getThreadPoolInstance(10);
		webCrawlerResponse = webCrawlerBootstrap.doProcessing(args, webCrawlerThreadPool);
		
		assertNotNull("Success Set should not be null !", webCrawlerResponse.getSuccessCrawlSet());
		assertTrue("Success Set length should be equal to 10!", webCrawlerResponse.getSuccessCrawlSet().size() == 10);
		
		assertNotNull("Skipped Set should not be null !", webCrawlerResponse.getSkipCrawlSet());
		assertTrue("Skipped Set length should be equal to 7!", webCrawlerResponse.getSkipCrawlSet().size() == 7);
		
		assertNotNull("Failure Set should not be null !", webCrawlerResponse.getFailureCrawlSet());
		assertTrue("Failure Set length should be equal to 5!", webCrawlerResponse.getFailureCrawlSet().size() == 5);
	}
	
	@Test
	public void testPage50() {
		
		WebCrawlerBootstrap webCrawlerBootstrap = new WebCrawlerBootstrap();
		String args[] = {"C:/config/internet.json", 
				"0",
				"page-50"};
		WebCrawlerThreadPool webCrawlerThreadPool = WebCrawlerThreadPool.getThreadPoolInstance(10);
		webCrawlerResponse = webCrawlerBootstrap.doProcessing(args, webCrawlerThreadPool);
		
		assertNotNull("Success Set should not be null !", webCrawlerResponse.getSuccessCrawlSet());
		assertTrue("Success Set length should be equal to 3!", webCrawlerResponse.getSuccessCrawlSet().size() == 3);
		
		assertNotNull("Skipped Set should not be null !", webCrawlerResponse.getSkipCrawlSet());
		assertTrue("Skipped Set length should be equal to 1!", webCrawlerResponse.getSkipCrawlSet().size() == 1);
		
		assertNotNull("Failure Set should not be null !", webCrawlerResponse.getFailureCrawlSet());
		assertTrue("Failure Set length should be equal to 1!", webCrawlerResponse.getFailureCrawlSet().size() == 1);
	}

	@Test
	public void testPage60() {
		
		WebCrawlerBootstrap webCrawlerBootstrap = new WebCrawlerBootstrap();
		String args[] = {"C:/config/internet.json", 
				"0",
				"page-60"};
		WebCrawlerThreadPool webCrawlerThreadPool = WebCrawlerThreadPool.getThreadPoolInstance(10);
		webCrawlerResponse = webCrawlerBootstrap.doProcessing(args, webCrawlerThreadPool);
		
		assertNotNull("Success Set should not be null !", webCrawlerResponse.getSuccessCrawlSet());
		assertTrue("Success Set length should be equal to 0!", webCrawlerResponse.getSuccessCrawlSet().size() == 0);
		
		assertNotNull("Skipped Set should not be null !", webCrawlerResponse.getSkipCrawlSet());
		assertTrue("Skipped Set length should be equal to 0!", webCrawlerResponse.getSkipCrawlSet().size() == 0);
		
		assertNotNull("Failure Set should not be null !", webCrawlerResponse.getFailureCrawlSet());
		assertTrue("Failure Set length should be equal to 1!", webCrawlerResponse.getFailureCrawlSet().size() == 1);
	}

}
