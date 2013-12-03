package org.decatime.bookie.view;

import org.decatime.bookie.Events;
import org.decatime.bookie.model.BookmarkProxy;
import org.decatime.bookie.ui.ManageDlg;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.mediator.Mediator;


public class BookmarksMediator extends Mediator {
	protected final static String NAME = "BookmarksMediator";
	
	ManageDlg view;
	
	public BookmarksMediator() {
		super(NAME, null);
	}
	
	public String[] listNotificationInterests()
	{
		return new String[] {
			Events.Bookmarks.SHOW_BOOKMARKS,
			Events.Bookmarks.CLOSE_BOOKMARKS,
			Events.Bookmarks.BOOKMARK_DELETED,
			Events.Bookmarks.BOOKMARK_SAVED,
			Events.Bookmarks.SAVE_BOOKMARK
		};
	}
	
	public void handleNotification( INotification notification )
	{
		String notif = notification.getName();
		if (notif.compareTo(Events.Bookmarks.SHOW_BOOKMARKS) == 0) {
			this.showBookmarks();
		} else if (notif.compareTo(Events.Bookmarks.CLOSE_BOOKMARKS) == 0) {
			this.closeBookmarks();
		} else if (
				notif.compareTo(Events.Bookmarks.BOOKMARK_DELETED) == 0
				|| notif.compareTo(Events.Bookmarks.BOOKMARK_SAVED) == 0
			) {
			this.updateView();
		} else if (notification.getName().compareTo(Events.Bookmarks.SAVE_BOOKMARK) == 0) {
			BookmarkProxy p = (BookmarkProxy) this.getFacade().retrieveProxy(BookmarkProxy.NAME);
			Object[] args = (Object[]) notification.getBody();
			p.update(args);
		}
	}
	
	private void showBookmarks() {
		view = new ManageDlg();
		view.setLocationRelativeTo(null);
		BookmarkProxy p = (BookmarkProxy) this.getFacade().retrieveProxy(BookmarkProxy.NAME);
		view.getBookmarkList().load(p.load());
		view.setVisible(true);
	}
	
	private void closeBookmarks() {
		if (view != null) {
			view.dispose();
			view = null;
		}
	}
	
	private void updateView() {
		if (view != null) {
			BookmarkProxy p = (BookmarkProxy) this.getFacade().retrieveProxy(BookmarkProxy.NAME);
			view.getBookmarkList().load(p.load());
		}
	}
}
