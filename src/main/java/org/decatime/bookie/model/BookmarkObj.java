package org.decatime.bookie.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("bookmark")
public class BookmarkObj {
	
	@XStreamAsAttribute
	private String url;
	
	private String category;
	private String description;
	
	public BookmarkObj() {
	}
	
	public BookmarkObj(String url, String category, String description) {
		this.url = url;
		this.category = category;
		this.description = description;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
