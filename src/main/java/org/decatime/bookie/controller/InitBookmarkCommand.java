package org.decatime.bookie.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.decatime.bookie.ApplicationFacade;
import org.decatime.bookie.Events;
import org.decatime.bookie.model.ConfigObj;
import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class InitBookmarkCommand extends SimpleCommand {
	
	@Override
	public void execute(INotification notification) {

		File f        = new File(ApplicationFacade.APP_CONF_FILE);
		XStream s     = new XStream(new DomDriver());
		ConfigObj cfg = null;
		
		s.processAnnotations(ConfigObj.class);
		if (! f.exists()) {
			try {
				cfg = new ConfigObj(ApplicationFacade.APP_BOOKMARK_FILE);
				FileOutputStream foutput = new FileOutputStream(f);
				s.toXML(cfg, foutput);	
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				FileInputStream finput = new FileInputStream(f);
				cfg = (ConfigObj) s.fromXML(finput);
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
		this.getFacade().sendNotification(Events.Navigation.SHOW_ICO_WINDOW, cfg, null);
	
	}
}
