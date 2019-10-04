package com.sam.model;

import java.io.Serializable;
import java.util.List;

public class WebPage implements Serializable {

	private static final long serialVersionUID = -4129697473851384897L;
	
	private String address;
	private List<String> links;
	
	public WebPage() {
	}
	
	public WebPage(String address, List<String> links) {
		this.address = address;
		this.links = links;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public List<String> getLinks() {
		return links;
	}
	public void setLinks(List<String> links) {
		this.links = links;
	}
	
}
