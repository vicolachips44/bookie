package org.decatime.bookie.view;

import java.awt.Frame;

import org.decatime.bookie.ApplicationFacade;
import org.decatime.bookie.Events;
import org.decatime.bookie.model.BookmarkObj;
import org.decatime.bookie.model.BookmarkProxy;
import org.decatime.bookie.ui.BookmarkEdit;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.mediator.Mediator;

public class EditBookmarkMediator extends Mediator {
	protected final static String NAME = "EditBookmarkMediator";
	
	public EditBookmarkMediator() {
		super(NAME, null);
	}
	
	public String[] listNotificationInterests()
	{
		return new String[] {
			Events.Bookmarks.EDIT_BOOKMARK,
			Events.Bookmarks.DELETE_BOOKMARK,
			Events.Bookmarks.BOOKMARK_DELETED,
			Events.Navigation.CLOSE_BM_EDIT
		};
	}
	
	public void handleNotification( INotification notification )
	{
		String action = notification.getName();
		if (action.compareTo(Events.Bookmarks.EDIT_BOOKMARK) == 0) {
			Object[] args = (Object[]) notification.getBody();
			BookmarkObj bookmark = (BookmarkObj) args[0];
			Frame parent = (Frame) args[1];
			this.editBookMark(bookmark, parent);
		} else if (action.compareTo(Events.Bookmarks.DELETE_BOOKMARK) == 0) {
			BookmarkObj bookmark = (BookmarkObj) notification.getBody();
			BookmarkProxy p = (BookmarkProxy) ApplicationFacade.getInstance().retrieveProxy(BookmarkProxy.NAME);
			p.delete(bookmark.getUrl());
		} else if (action.compareTo(Events.Navigation.CLOSE_BM_EDIT) == 0) {
			BookmarkEdit view = (BookmarkEdit) notification.getBody();
			view.dispose();
			view = null;
		}
	}
	
	private void editBookMark(BookmarkObj bookmark, Frame parent) {
		BookmarkEdit edit = new BookmarkEdit(parent, bookmark);
		edit.setLocationRelativeTo(parent);
		edit.setVisible(true);
	}
}
