package com.sam.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.sam.exception.ExceptionCodes;
import com.sam.exception.WebCrawlerException;

public final class WebCrawlerThreadPool {

	public final int THREAD_POOL_SIZE = 2;
	public final int THREAD_POOL_TERMINATION_TIMEOUT = 10;
	
	private static WebCrawlerThreadPool webCrawlerThreadPool;
	private ExecutorService executorService;
	
	private WebCrawlerThreadPool() {
		this.executorService = Executors.newCachedThreadPool();
	}
	private WebCrawlerThreadPool(int poolSize) {
		this.executorService = Executors.newFixedThreadPool(poolSize);
	}
	
	public static WebCrawlerThreadPool getThreadPoolInstance() {
		if(webCrawlerThreadPool == null) {
			synchronized (WebCrawlerThreadPool.class) {
				if(webCrawlerThreadPool == null) {
					webCrawlerThreadPool = new WebCrawlerThreadPool();
				}
			}
		}
		
		return webCrawlerThreadPool;
	}
	public static WebCrawlerThreadPool getThreadPoolInstance(int threadPool) {
		if(webCrawlerThreadPool == null) {
			synchronized (WebCrawlerThreadPool.class) {
				if(webCrawlerThreadPool == null) {
					webCrawlerThreadPool = new WebCrawlerThreadPool(threadPool);
				}
			}
		}
		
		return webCrawlerThreadPool;
	}
	
	public void shutDownService() 
			throws WebCrawlerException {
		
		try {
			if(executorService != null) {
				if(!executorService.isShutdown()) {
					executorService.shutdown();
					executorService.awaitTermination(THREAD_POOL_TERMINATION_TIMEOUT, TimeUnit.MILLISECONDS);
				}
			}
		} catch (SecurityException se) {
			throw new WebCrawlerException(ExceptionCodes.SECURITY_ERROR, se);
		} catch (InterruptedException ie) {
			Thread.currentThread().interrupt();
			throw new WebCrawlerException(ExceptionCodes.THREAD_INTERRUPT_ERROR, ie);
		} catch (Exception e) {
			throw new WebCrawlerException(ExceptionCodes.SHUTDOWN_SERVICE_ERROR, e);
		} finally {
			executorService = null;
		}
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}
	
}
