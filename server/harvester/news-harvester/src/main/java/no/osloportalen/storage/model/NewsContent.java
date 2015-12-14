package no.osloportalen.storage.model;

import java.io.Serializable;

public class NewsContent implements Serializable {

	private static final long serialVersionUID = -8996012815175562629L;
	private String dateStamp;
	private String content;
	private String url;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDateStamp() {
		return dateStamp;
	}
	public void setDateStamp(String dateStamp) {
		this.dateStamp = dateStamp;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
