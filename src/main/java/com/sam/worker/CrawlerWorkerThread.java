package com.sam.worker;

import java.util.concurrent.CountDownLatch;

import com.sam.exception.WebCrawlerException;
import com.sam.service.WebCrawlerService;

public class CrawlerWorkerThread implements Runnable {

	private WebCrawlerService webCrawlerService;
	private String startPage;
	private CountDownLatch latch;
	
	public CrawlerWorkerThread(WebCrawlerService webCrawlerService,
			CountDownLatch latch,
			String startPage) {
		
		this.webCrawlerService = webCrawlerService;
		this.latch = latch;
		this.startPage = startPage;
	}
	
	@Override
	public void run() 
			throws WebCrawlerException {
		try {
			this.webCrawlerService.crawlWebPage(startPage);
		} catch (WebCrawlerException we) {
			throw we;
		} finally {
			this.latch.countDown();
		}
	}
	
}
