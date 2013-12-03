package org.decatime.bookie.controller;

import org.puremvc.java.multicore.interfaces.INotification;
import org.puremvc.java.multicore.patterns.command.SimpleCommand;

public class CloseCommand extends SimpleCommand {
	
	public void execute(INotification notification) {
		System.exit(0);
	}
}