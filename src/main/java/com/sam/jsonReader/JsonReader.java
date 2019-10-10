package com.sam.jsonReader;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sam.exception.ExceptionCodes;
import com.sam.exception.WebCrawlerException;
import com.sam.model.WebPage;
import com.sam.model.WebPageList;

public final class JsonReader {

	private static JsonReader jsonReader;
	private ObjectMapper objectMapper;
	private String jsonFilePath;
	
	private JsonReader(String jsonFilePath) {
		this.jsonFilePath = jsonFilePath;
		this.objectMapper = new ObjectMapper();
	}
	
	public static JsonReader getReaderInstance(String jsonFilePath) {
		
		if(jsonReader == null) {
			synchronized (JsonReader.class) {
				if(jsonReader == null) {
					jsonReader = new JsonReader(jsonFilePath);
				}
			}
		}
		
		return jsonReader;
	}
	
	public WebPageList readJsonFromFile() 
			throws WebCrawlerException {
		
		WebPageList webPageList = new WebPageList();
		try {
			if(this.jsonFilePath != null
					&& this.jsonFilePath.length() > 0) {
				webPageList = objectMapper.readValue(new File(jsonFilePath), WebPageList.class);
			}
			
		} catch (Exception e) {
			throw new WebCrawlerException(ExceptionCodes.JSON_READER_ERROR.getValue(),
					e.getMessage(), e);
		}
		
		return webPageList;
	}
	
	public Map<String, List<String>> convertListToMap(List<WebPage> pages) 
			throws WebCrawlerException {
		
		Map<String, List<String>> webCrawlerMap = null;
		try {
			if(pages != null
					&& pages.size() > 0) {
				
				webCrawlerMap = new ConcurrentHashMap<String, List<String>>();
				for(WebPage webPage : pages) {
					webCrawlerMap.put(webPage.getAddress(), webPage.getLinks());
				}
			}
			
		} catch (Exception e) {
			throw new WebCrawlerException(ExceptionCodes.JSON_CONVERION_ERROR.getValue(),
					e.getMessage(), e);
		}
		
		return webCrawlerMap;
	}

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}
	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

}
