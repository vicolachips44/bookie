package org.decatime.bookie.model;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("bookie-bookmarks.xml")
public class BookmarkContainer {

	@XStreamImplicit
	private List<BookmarkObj> content;
	
	public BookmarkContainer() {
		init();
	}
	
	private void init() {
		this.content = new ArrayList<BookmarkObj>();
	}
	
	public void addBookmark(BookmarkObj obj) {
		if (this.content == null) { this.init(); }
		this.content.add(obj);
	}

	public List<BookmarkObj> getBookmarks() {
		return this.content;
	}

	public void removeByUrl(String url) {
		for (BookmarkObj bookmark : this.content) {
			if (bookmark.getUrl().compareTo(url) == 0) {
				this.content.remove(bookmark);
				break;
			}
		}
	}

	public boolean urlExists(String url) {
		if (this.content == null) { this.init(); }
		
		for (BookmarkObj bookmark : this.content) {
			if (bookmark.getUrl().compareTo(url) == 0) {
				return true;
			}
		}
		return false;
	}
}
