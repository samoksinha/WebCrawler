package com.sam.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import com.sam.exception.WebCrawlerException;
import com.sam.model.WebCrawlerResponse;
import com.sam.threadpool.WebCrawlerThreadPool;
import com.sam.worker.CrawlerWorkerThread;

public class WebCrawlerService {

	private static WebCrawlerService webCrawlerService;
	
	private Map<String,List<String>> webCrawlerMap;
	private WebCrawlerResponse webCrawlerResponse;
	private WebCrawlerThreadPool webCrawlerThreadPool;
	
	private WebCrawlerService(Map<String,List<String>> webCrawlerMap,
			WebCrawlerResponse webCrawlerResponse,
			WebCrawlerThreadPool webCrawlerThreadPool) {
		
		this.webCrawlerMap = webCrawlerMap;
		this.webCrawlerResponse = webCrawlerResponse;
		this.webCrawlerThreadPool = webCrawlerThreadPool;
	}
	
	public static WebCrawlerService getServiceInstance(Map<String,List<String>> webCrawlerMap,
			WebCrawlerResponse webCrawlerResponse,
			WebCrawlerThreadPool webCrawlerThreadPool) {
		
		if(webCrawlerService == null) {
			synchronized (WebCrawlerService.class) {
				if(webCrawlerService == null) {
					webCrawlerService = new WebCrawlerService(webCrawlerMap,
							webCrawlerResponse, 
							webCrawlerThreadPool);
				}
			}
		}
		
		return webCrawlerService;
	}
	
	public void crawlWebPage(String startPage) 
			throws WebCrawlerException {
		
		try {
			if(this.webCrawlerMap.containsKey(startPage)) {
				
				if(this.webCrawlerResponse.getSuccessCrawlSet().contains(startPage)) {
					this.webCrawlerResponse.getSkipCrawlSet().add(startPage);
				} else {
					this.crawlDepthWebPage(startPage);
				}
				
			} else {
				this.webCrawlerResponse.getFailureCrawlSet().add(startPage);
			}
			
		} catch (WebCrawlerException we) {
			throw we;
		} catch (Exception e) {
			throw new WebCrawlerException(303, e.getMessage(), e);
		}
	}
	
	private void crawlDepthWebPage(String startPage) 
			throws WebCrawlerException {
		
		try {
			this.webCrawlerResponse.getSuccessCrawlSet().add(startPage);
			List<String> nextCrawlList = this.webCrawlerMap.get(startPage);
			if(nextCrawlList.size() > 0) {
				
				CountDownLatch depthLatch = new CountDownLatch(nextCrawlList.size());
				for(int i = 0; i < nextCrawlList.size(); i++) {
					Runnable crawlerWorkerThread = new CrawlerWorkerThread(WebCrawlerService.webCrawlerService,
							depthLatch,
							nextCrawlList.get(i));
					this.webCrawlerThreadPool.getExecutorService().execute(crawlerWorkerThread);
				}
				
				depthLatch.await();
			}
			
		} catch (InterruptedException ie) {
			throw new WebCrawlerException(304, ie.getMessage(), ie);
		} catch (Exception e) {
			throw new WebCrawlerException(305, e.getMessage(), e);
		}
	}
}
