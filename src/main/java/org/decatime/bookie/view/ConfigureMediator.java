package org.decatime.bookie.view;

import org.decatime.bookie.ApplicationFacade;
import org.decatime.bookie.Events;
import org.decatime.bookie.ui.Configure;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.mediator.Mediator;

public class ConfigureMediator extends Mediator {
	public static final String NAME = "ConfigureMediator";
	
	public ConfigureMediator() {
		super(NAME, null);
	}
	
	@Override
	public String[] listNotificationInterests() {
		return new String[] {
			Events.Navigation.CONFIGURE,
			Events.Bookmarks.SOURCE_UPDATED
		};
	}
	
	@Override
	public void handleNotification(INotification notification) {
		String n = notification.getName();
		if (n.compareTo(Events.Navigation.CONFIGURE) == 0) {
			this.showConfigureDialog();
		} else if (n.compareTo(Events.Bookmarks.SOURCE_UPDATED) == 0) {
			ApplicationFacade.getInstance().reloadBookmarks();
		}
	}
	
	public void showConfigureDialog() {
		Configure dlg = new Configure(ApplicationFacade.getInstance().getConfig());
		dlg.setLocationRelativeTo(null);
		dlg.setVisible(true);
	}
}
