package com.sam.webcrawler.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sam.model.WebCrawlerResponse;
import com.sam.threadpool.WebCrawlerThreadPool;
import com.sam.webcrawler.WebCrawlerBootstrap;

public class WebCrawlerTest {
	
	private WebCrawlerResponse webCrawlerResponse;
	private String testFilePath;
	private int threadPoolSize;
	private String startPage01;
	private String startPage50;
	private String startPage60;
	
	@Before
	public void beforeEachTest() {
		Properties testProperties = new Properties();
		URL jsonFileUrl = null;
		try (InputStream inputStream = this.getClass().getResourceAsStream("/test-config.properties")) {
			testProperties.load(inputStream);
			
			jsonFileUrl = this.getClass().getResource("/internet.json");
			if(jsonFileUrl != null 
					&& jsonFileUrl.getPath() != null
						&& jsonFileUrl.getPath().length() > 0) {
				testFilePath = jsonFileUrl.getPath();
			} else {
				testFilePath = testProperties.getProperty("test-file-location");
			}
			
			threadPoolSize = Integer.parseInt(testProperties.getProperty("thread-pool-size"));
			startPage01 = testProperties.getProperty("start-page-01");
			startPage50 = testProperties.getProperty("start-page-50");
			startPage60 = testProperties.getProperty("start-page-60");
			
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	@After
	public void afterEachTest() {
		webCrawlerResponse.cleanUp();
	}

	@Test
	public void testPage01() {
		
		WebCrawlerBootstrap webCrawlerBootstrap = new WebCrawlerBootstrap();
		
		WebCrawlerThreadPool webCrawlerThreadPool = null;
		if(threadPoolSize == 0) {
			webCrawlerThreadPool = WebCrawlerThreadPool.getThreadPoolInstance();
		} else {
			webCrawlerThreadPool = WebCrawlerThreadPool.getThreadPoolInstance(threadPoolSize);
		}
		webCrawlerResponse = webCrawlerBootstrap.doProcessing(testFilePath, startPage01, webCrawlerThreadPool);
		
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
		
		WebCrawlerThreadPool webCrawlerThreadPool = null;
		if(threadPoolSize == 0) {
			webCrawlerThreadPool = WebCrawlerThreadPool.getThreadPoolInstance();
		} else {
			webCrawlerThreadPool = WebCrawlerThreadPool.getThreadPoolInstance(threadPoolSize);
		}
		webCrawlerResponse = webCrawlerBootstrap.doProcessing(testFilePath, startPage50, webCrawlerThreadPool);
		
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
		
		WebCrawlerThreadPool webCrawlerThreadPool = null;
		if(threadPoolSize == 0) {
			webCrawlerThreadPool = WebCrawlerThreadPool.getThreadPoolInstance();
		} else {
			webCrawlerThreadPool = WebCrawlerThreadPool.getThreadPoolInstance(threadPoolSize);
		}
		webCrawlerResponse = webCrawlerBootstrap.doProcessing(testFilePath, startPage60, webCrawlerThreadPool);
		
		assertNotNull("Success Set should not be null !", webCrawlerResponse.getSuccessCrawlSet());
		assertTrue("Success Set length should be equal to 0!", webCrawlerResponse.getSuccessCrawlSet().size() == 0);
		
		assertNotNull("Skipped Set should not be null !", webCrawlerResponse.getSkipCrawlSet());
		assertTrue("Skipped Set length should be equal to 0!", webCrawlerResponse.getSkipCrawlSet().size() == 0);
		
		assertNotNull("Failure Set should not be null !", webCrawlerResponse.getFailureCrawlSet());
		assertTrue("Failure Set length should be equal to 1!", webCrawlerResponse.getFailureCrawlSet().size() == 1);
	}

}
