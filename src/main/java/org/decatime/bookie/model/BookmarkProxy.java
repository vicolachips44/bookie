package org.decatime.bookie.model;

import java.util.Vector;

import org.decatime.bookie.ApplicationFacade;
import org.decatime.bookie.Events;
import org.puremvc.java.multicore.patterns.proxy.Proxy;

public class BookmarkProxy extends Proxy implements IBookmarkProvider {
	public static final String NAME = "BookmarkProxy";
	private IBookmarkProvider manager = null;
	
	public BookmarkProxy() {
		super(NAME);
		this.manager = ApplicationFacade.getInstance().getBookmarkManager();
	}

	public boolean create(String url) {
		return this.manager.create(url);
	}
	
	public void update(Object[] args) {
		this.manager.update(args);
		this.sendNotification(Events.Bookmarks.BOOKMARK_SAVED, null, null);
	}
	
	public void delete(String url) {
		try {
			this.manager.delete(url);
			this.sendNotification(Events.Bookmarks.BOOKMARK_DELETED, null, null);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Vector<Object> load() {
		return this.manager.load();
	}

	
}
