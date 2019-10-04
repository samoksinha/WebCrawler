package com.sam.webcrawler;

import java.util.List;
import java.util.Map;

import com.sam.exception.WebCrawlerException;
import com.sam.jsonReader.JsonReader;
import com.sam.model.WebCrawlerResponse;
import com.sam.model.WebPageList;
import com.sam.service.WebCrawlerService;
import com.sam.threadpool.WebCrawlerThreadPool;

public class WebCrawlerBootstrap {
	
	public WebCrawlerBootstrap() {
	}
	
	public static void main(String args[]) {
		
		WebCrawlerThreadPool webCrawlerThreadPool = null;
		WebCrawlerResponse webCrawlerResponse = null;
		try {
			
			WebCrawlerBootstrap webCrawlerBootstrap = new WebCrawlerBootstrap();
			
			if(Integer.parseInt(args[1]) == 0) {
				webCrawlerThreadPool = WebCrawlerThreadPool.getThreadPoolInstance();
			} else {
				webCrawlerThreadPool = WebCrawlerThreadPool.getThreadPoolInstance(Integer.parseInt(args[1]));
			}
			
			webCrawlerResponse = webCrawlerBootstrap.doProcessing(args, webCrawlerThreadPool);
			
			System.out.println("Success Set : " +webCrawlerResponse.getSuccessCrawlSet());
			System.out.println("Skipped Set : " +webCrawlerResponse.getSkipCrawlSet());
			System.out.println("Failured Set : " +webCrawlerResponse.getFailureCrawlSet());
			
		} catch (WebCrawlerException wce) {
			wce.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(webCrawlerThreadPool != null) {
				try {
					webCrawlerThreadPool.shutDownService();
				} catch (WebCrawlerException wce) {
					wce.printStackTrace();
				}
			}
			
			if(webCrawlerResponse != null) {
				webCrawlerResponse.cleanUp();
			}
		}
	}
	
	public WebCrawlerResponse doProcessing(String args[],
			WebCrawlerThreadPool webCrawlerThreadPool) 
			throws WebCrawlerException {
		
		JsonReader jsonReader = null;
		Map<String, List<String>> webCrawlerMap = null;
		WebCrawlerService webCrawlerService = null;
		WebCrawlerResponse webCrawlerResponse = null;
		WebPageList webPageList = null;
		try {
			if(args != null
					&& args.length == 3) {
				
				jsonReader = JsonReader.getReaderInstance(args[0]);
				
				if(args[2] != null
						&& args[2].length() > 0) {
					
					webCrawlerResponse = WebCrawlerResponse.getResponseInstance();
					
					webPageList = jsonReader.readJsonFromFile();
					webCrawlerMap = jsonReader.convertListToMap(webPageList.getPages());
					
					webCrawlerService = WebCrawlerService.getServiceInstance(webCrawlerMap,
							webCrawlerResponse,
							webCrawlerThreadPool);
					webCrawlerService.crawlWebPage(args[2]);
				}
			}
			
		} catch (WebCrawlerException wce) {
			throw wce;
		} catch (Exception e) {
			throw new WebCrawlerException(500, e.getMessage(), e);
		}
		
		return webCrawlerResponse;
	}

}
