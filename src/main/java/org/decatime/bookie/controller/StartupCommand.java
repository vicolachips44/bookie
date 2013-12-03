package org.decatime.bookie.controller;

import org.decatime.bookie.Events;
import org.decatime.bookie.model.BookmarkProxy;
import org.decatime.bookie.view.BookmarkListMediator;
import org.decatime.bookie.view.BookmarksMediator;
import org.decatime.bookie.view.ConfigureMediator;
import org.decatime.bookie.view.EditBookmarkMediator;
import org.decatime.bookie.view.IconWindowMediator;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

/**
 * The startup command object is creating
 * all the mediators that manage views and
 * also create all the proxy objects that interact with
 * data.
 * 
 * @author vga.nantes@gmail.com
 *
 */
public class StartupCommand extends SimpleCommand {
	
	public void execute(INotification notification) {
		// mediator registration
		this.getFacade().registerMediator(new IconWindowMediator());
		this.getFacade().registerMediator(new BookmarksMediator());
		this.getFacade().registerMediator(new BookmarkListMediator());
		this.getFacade().registerMediator(new EditBookmarkMediator());
		this.getFacade().registerMediator(new ConfigureMediator());
		
		// proxy data registration
		this.getFacade().registerProxy(new BookmarkProxy());
		this.getFacade().sendNotification(Events.Bookmarks.INIT_BOOKMARKS, null, null);
	}
}
