package org.decatime.bookie.view;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

import org.decatime.bookie.ApplicationFacade;
import org.decatime.bookie.Events;
import org.decatime.bookie.model.BookmarkProxy;
import org.decatime.bookie.model.ConfigObj;
import org.decatime.bookie.ui.MainWindow;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.mediator.Mediator;

public class IconWindowMediator extends Mediator {
	protected final static String NAME = "IconWindowMediator";
	
	
	public IconWindowMediator() {
		super(NAME, null);
	}
	
	
	
	public String[] listNotificationInterests()
	{
		return new String[]{
			Events.Navigation.SHOW_ICO_WINDOW,
			Events.Bookmarks.NEW
		};
	}
	
	public void handleNotification( INotification notification )
	{
		String notif = notification.getName();
		if (notif.compareTo(Events.Navigation.SHOW_ICO_WINDOW) == 0) {
			ApplicationFacade.getInstance().setConfig((ConfigObj) notification.getBody());
			
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					doRun();
				}			
			});
		} else if (notif.compareTo(Events.Bookmarks.NEW) == 0) {
			BookmarkProxy p = (BookmarkProxy) this.getFacade().retrieveProxy(BookmarkProxy.NAME);
			String url = (String) notification.getBody();
			boolean created = p.create(url);
			
			if (! created) {
				JOptionPane.showMessageDialog(
					null,
					url + " is already in your bookmarks !",
					"Warning",
					JOptionPane.WARNING_MESSAGE
				);
			}
		}
		
	}
	
	private void doRun() {
		MainWindow iconView = new MainWindow();
		iconView.setVisible(true);
		iconView.invalidate();
	}
	
}
