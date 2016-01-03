package no.osloportalen.storage.model;

import java.io.Serializable;
import java.util.Date;

import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.uci.ics.crawler4j.crawler.Page;

public class NewsContent extends CouchDbDocument implements Serializable {

	private static final long serialVersionUID = -8996012815175562629L;
	private String _id;
	private String _rev;
	private String dateStamp;
	private String content;
	@TypeDiscriminator
	private String url;
	private int shelfLifeHours = 12;
	private int checksum;
	
	public NewsContent() {
		
	}

	public String get_id() {
		return this._id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_rev() {
		return _rev;
	}

	public void set_rev(String _rev) {
		this._rev = _rev;
	}

	@JsonProperty
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
	
	@JsonProperty
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.setChecksum( content.hashCode() );
		this.content = content;
	}

	public int getChecksum() {
		return this.checksum;
	}

	public void setChecksum(int checksum) {
		this.checksum = checksum;
	}

	@JsonProperty
	public int getShelfLifeHours() {
		return shelfLifeHours;
	}

	public void setShelfLifeHours(int shelfLifeHours) {
		this.shelfLifeHours = shelfLifeHours;
	}

	public static NewsContent convertFromPage(Page page) {

		NewsContent content = new NewsContent();
		content.setUrl(page.getWebURL().toString());
		content.set_id(content.getUrl());
		content.setDateStamp(new Date().toString());

		return content;
	}

	@Override
	public String toString() {
		return "NewsContent [dateStamp=" + dateStamp + ", content=" + content + ", url=" + url + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewsContent other = (NewsContent) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	public static class RevInfo {
		private String rev;
		private String status;

		public String getRev() {
			return rev;
		}

		public void setRev(String rev) {
			this.rev = rev;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		@Override
		public String toString() {
			return "RevInfo [rev=" + rev + ", status=" + status + "]";
		}
	} // end RevInfo
	
}
