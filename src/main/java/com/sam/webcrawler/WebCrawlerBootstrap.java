package com.sam.webcrawler;

import java.util.List;
import java.util.Map;

import com.sam.exception.ExceptionCodes;
import com.sam.exception.WebCrawlerException;
import com.sam.jsonReader.JsonReader;
import com.sam.model.Response;
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
		
		String jsonFilePath = null;
		int threadPoolSize = -1;
		String startPage = null;
		try {
			if(args == null 
					|| args.length != 3) {
				throw new WebCrawlerException(ExceptionCodes.INVALID_RUNTIME_ARGUMENTS);
			} else {
				
				jsonFilePath = args[0];
				threadPoolSize = Integer.parseInt(args[1]);
				startPage = args[2];
			}
			
			WebCrawlerBootstrap webCrawlerBootstrap = new WebCrawlerBootstrap();
			if(threadPoolSize == 0) {
				webCrawlerThreadPool = WebCrawlerThreadPool.getThreadPoolInstance();
			} else {
				webCrawlerThreadPool = WebCrawlerThreadPool.getThreadPoolInstance(threadPoolSize);
			}
			
			webCrawlerResponse = webCrawlerBootstrap.doProcessing(jsonFilePath, startPage, webCrawlerThreadPool);
			
			System.out.println(Response.SUCCESS.getValue() + " " +webCrawlerResponse.getSuccessCrawlSet());
			System.out.println(Response.SKIP.getValue() +" " +webCrawlerResponse.getSkipCrawlSet());
			System.out.println(Response.ERROR.getValue() + " " +webCrawlerResponse.getFailureCrawlSet());
			
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
	
	public WebCrawlerResponse doProcessing(String jsonFilePath,
			String startPage,
			WebCrawlerThreadPool webCrawlerThreadPool) 
			throws WebCrawlerException {
		
		JsonReader jsonReader = null;
		Map<String, List<String>> webCrawlerMap = null;
		WebCrawlerService webCrawlerService = null;
		WebCrawlerResponse webCrawlerResponse = null;
		WebPageList webPageList = null;
		try {
			if(jsonFilePath != null
					&& jsonFilePath.length() > 0) {
				
				jsonReader = JsonReader.getReaderInstance(jsonFilePath);
				
				if(startPage != null
						&& startPage.length() > 0) {
					
					webCrawlerResponse = WebCrawlerResponse.getResponseInstance();
					
					webPageList = jsonReader.readJsonFromFile();
					webCrawlerMap = jsonReader.convertListToMap(webPageList.getPages());
					
					webCrawlerService = WebCrawlerService.getServiceInstance(webCrawlerMap,
							webCrawlerResponse,
							webCrawlerThreadPool);
					webCrawlerService.crawlWebPage(startPage);
				} else {
					throw new WebCrawlerException(ExceptionCodes.INVALID_START_PAGE);
				}
				
			} else {
				throw new WebCrawlerException(ExceptionCodes.INVALID_FILE_PATH);
			}
			
		} catch (WebCrawlerException wce) {
			throw wce;
		} catch (Exception e) {
			throw new WebCrawlerException(ExceptionCodes.PROCESSING_INTERNAL_ERROR, e);
		}
		
		return webCrawlerResponse;
	}

}
