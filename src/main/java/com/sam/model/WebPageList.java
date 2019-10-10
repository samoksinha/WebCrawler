package com.sam.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WebPageList implements Serializable {

	private static final long serialVersionUID = -5784881159898202643L;
	
	private List<WebPage> pages;
	
	public WebPageList() {
		this.pages = new ArrayList<WebPage>();
	}
	
	public WebPageList(List<WebPage> pages) {
		this.pages = pages;
	}

	public List<WebPage> getPages() {
		return pages;
	}
	public void setPages(List<WebPage> pages) {
		this.pages = pages;
	}
}
