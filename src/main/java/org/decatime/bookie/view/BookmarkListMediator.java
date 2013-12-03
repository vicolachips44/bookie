package org.decatime.bookie.view;

import java.awt.Frame;

import org.decatime.bookie.ApplicationFacade;
import org.decatime.bookie.Events;
import org.decatime.bookie.model.BookmarkObj;
import org.jdesktop.swingx.JXTable;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.mediator.Mediator;

public class BookmarkListMediator extends Mediator {
	protected final static String NAME = "BookmarkListMediator";
	
	public BookmarkListMediator() {
		super(NAME, null);
	}
	
	public String[] listNotificationInterests()
	{
		return new String[] {
			Events.Bookmarks.REQ_EDIT_BOOKMARK
		};
	}
	
	public void handleNotification( INotification notification ) {
		String action = notification.getName();
		if (action.compareTo(Events.Bookmarks.REQ_EDIT_BOOKMARK)== 0) {
			Object[] args = (Object[]) notification.getBody();
			JXTable table = (JXTable) args[0];
			Frame parent  = (Frame) args[1];
			
			int row = table.convertRowIndexToModel(table.getSelectedRow());

			String category      = (String) table.getModel().getValueAt(row, 0);
			String description   = (String) table.getModel().getValueAt(row, 1);
			String url 		     = (String) table.getModel().getValueAt(row, 2);
			BookmarkObj bookmark = new BookmarkObj(url, category, description);
			
			Object[] outArgs = new Object[] {bookmark, parent};
			
			ApplicationFacade.getInstance().sendNotification(Events.Bookmarks.EDIT_BOOKMARK, outArgs, null);
		}
	}
}
