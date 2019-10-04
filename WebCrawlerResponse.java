package com.sam.model;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public final class WebCrawlerResponse {

	private static WebCrawlerResponse webCrawlerResponse;
	
	private Set<String> successCrawlSet;
	private Set<String> skipCrawlSet;
	private Set<String> failureCrawlSet;
	
	public WebCrawlerResponse() {
		successCrawlSet = new ConcurrentSkipListSet<String>();
		skipCrawlSet = new ConcurrentSkipListSet<String>();
		failureCrawlSet = new ConcurrentSkipListSet<String>();
	}
	
	public static WebCrawlerResponse getResponseInstance() {
		
		if(webCrawlerResponse == null) {
			synchronized (WebCrawlerResponse.class) {
				if(webCrawlerResponse == null) {
					webCrawlerResponse = new WebCrawlerResponse();
				}
			}
		}
		
		return webCrawlerResponse;
	}
	
	public void cleanUp() {
		
		if(webCrawlerResponse != null
				&& webCrawlerResponse.getSuccessCrawlSet() != null
					&& webCrawlerResponse.getSuccessCrawlSet().size() > 0) {
			webCrawlerResponse.getSuccessCrawlSet().clear();
		}
		
		if(webCrawlerResponse != null
				&& webCrawlerResponse.getSkipCrawlSet() != null
					&& webCrawlerResponse.getSkipCrawlSet().size() > 0) {
			webCrawlerResponse.getSkipCrawlSet().clear();
		}
		
		if(webCrawlerResponse != null
				&& webCrawlerResponse.getFailureCrawlSet() != null
					&& webCrawlerResponse.getFailureCrawlSet().size() > 0) {
			webCrawlerResponse.getFailureCrawlSet().clear();
		}
	}

	public Set<String> getSuccessCrawlSet() {
		return successCrawlSet;
	}
	public void setSuccessCrawlSet(Set<String> successCrawlSet) {
		this.successCrawlSet = successCrawlSet;
	}

	public Set<String> getSkipCrawlSet() {
		return skipCrawlSet;
	}
	public void setSkipCrawlSet(Set<String> skipCrawlSet) {
		this.skipCrawlSet = skipCrawlSet;
	}

	public Set<String> getFailureCrawlSet() {
		return failureCrawlSet;
	}
	public void setFailureCrawlSet(Set<String> failureCrawlSet) {
		this.failureCrawlSet = failureCrawlSet;
	}
	
}
