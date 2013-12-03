package org.decatime.bookie;

import org.decatime.bookie.controller.CloseCommand;
import org.decatime.bookie.controller.InitBookmarkCommand;
import org.decatime.bookie.controller.StartupCommand;
import org.decatime.bookie.model.ConfigObj;
import org.decatime.bookie.model.IBookmarkProvider;
import org.decatime.bookie.tools.XmlBookmarkManager;
import org.puremvc.java.multicore.patterns.facade.Facade;

public final class ApplicationFacade extends Facade {
	public static final String APP_KEY_NAME = "appKeyName";
	public static final String APP_CONF_FILE = "bookie-config.xml";
	public static final String APP_BOOKMARK_FILE = "bookie-bookmarks.xml";
	
	private static ApplicationFacade instance = null;
	private ConfigObj config;
	
	protected ApplicationFacade() {
		super(APP_KEY_NAME);
	}
	
	/**
	 * Singleton getter access.
	 * 
	 * @return instance of ApplicationFacade.
	 */
	public static ApplicationFacade getInstance() {
		if (instance == null) {
			instance = new ApplicationFacade();
		}
		return instance;
	}
	
	@Override
	protected void initializeController() {
		super.initializeController();
		this.registerCommand(Events.Navigation.STARTUP, new StartupCommand());
		this.registerCommand(Events.Bookmarks.INIT_BOOKMARKS,
				new InitBookmarkCommand());
		this.registerCommand(Events.Navigation.APP_CLOSE, new CloseCommand());
	}
	
	public void startup() {
		this.sendNotification(Events.Navigation.STARTUP, null, null);
		this.removeCommand(Events.Navigation.STARTUP);
	}

	public IBookmarkProvider getBookmarkManager() {
		return XmlBookmarkManager.getInstance();
	}

	public void setConfig(ConfigObj config) {
		this.config = config;
		XmlBookmarkManager.getInstance().initialize(
				config.getBookmarkFilePath());
	}

	public ConfigObj getConfig() {
		return this.config;
	}

	public void reloadBookmarks() {
		XmlBookmarkManager.getInstance().initialize(
				this.config.getBookmarkFilePath());
	}

}
