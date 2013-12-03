package org.decatime.bookie.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Vector;

import org.decatime.bookie.model.BookmarkContainer;
import org.decatime.bookie.model.BookmarkObj;
import org.decatime.bookie.model.IBookmarkProvider;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public final class XmlBookmarkManager implements IBookmarkProvider {
	private static XmlBookmarkManager instance;
	private String bookmarkFilePath;
	private BookmarkContainer bookmarkContainer;
	
	protected XmlBookmarkManager() {
	}
	
	public static XmlBookmarkManager getInstance() {
		if (instance == null) {
			instance = new XmlBookmarkManager();
		}
		return instance;
	}
	
	public void initialize(String bookmarkFilePath) {
		this.bookmarkFilePath = bookmarkFilePath;
		XStream s     = new XStream(new DomDriver("UTF-8"));
		s.processAnnotations(BookmarkContainer.class);
		File f = new File(bookmarkFilePath);
		if (! f.exists()) {
			BookmarkObj google = new BookmarkObj("www.google.fr", "Moteur de recherche", "Moteur de recherche de google");
			this.bookmarkContainer = new BookmarkContainer();
			this.bookmarkContainer.addBookmark(google);
			this.save();
		}
		this.bookmarkContainer = (BookmarkContainer) s.fromXML(f);
		
	}
	
	public boolean create(String url) {
		if (this.bookmarkContainer.urlExists(url)) {
			return false;
		}
		BookmarkObj b = new BookmarkObj(url, "unknown", "...");
		this.bookmarkContainer.addBookmark(b);
		
		return this.save();
	}
	
	private boolean save() {
		XStream s     = new XStream(new DomDriver("UTF-8"));
		s.processAnnotations(BookmarkContainer.class);
		try {
			FileOutputStream foutput = new FileOutputStream(this.bookmarkFilePath);
			s.toXML(this.bookmarkContainer, foutput);	
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Vector<Object> load() {
		
		Vector<Object> rowData = new Vector<Object>();
		List<BookmarkObj> bookmarks = this.bookmarkContainer.getBookmarks();
		
		for (BookmarkObj bookmark : bookmarks) {
			Vector<String> line = new Vector<String>();
			
			line.add(bookmark.getCategory());
			line.add(bookmark.getDescription());
			line.add(bookmark.getUrl());
			
			rowData.add(line);
		}
		
		return rowData;
	}

	public void delete(String url) {
		this.bookmarkContainer.removeByUrl(url);
		this.save();
	}

	public void update(Object[] args) {
		
		BookmarkObj oldBm = (BookmarkObj) args[0];
		BookmarkObj newBm = (BookmarkObj) args[1];
		this.bookmarkContainer.removeByUrl(oldBm.getUrl());
		this.bookmarkContainer.addBookmark(newBm);
		this.save();	
		
	}
}
