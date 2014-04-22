/////////////////////////////////////////////////////////
//  Bare Bones Browser Launch                          //
//  Version 3.1 (June 6, 2010)                         //
//  By Dem Pilafian                                    //
//  Supports:                                          //
//     Mac OS X, GNU/Linux, Unix, Windows XP/Vista/7   //
//  Example Usage:                                     //
//     String url = "http://www.centerkey.com/";       //
//     BareBonesBrowserLaunch.openURL(url);            //
//  Public Domain Software -- Free to Use as You Like  //
/////////////////////////////////////////////////////////
package org.decatime.bookie.tools;

import java.awt.Desktop;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;

import javax.swing.JOptionPane;

public class BareBonesBrowserLaunch {

	static final String[] browsers = { "google-chrome", "firefox", "opera",
			"epiphany", "konqueror", "conkeror", "midori", "kazehakase",
			"mozilla", "chromium-browser" };
	static final String errMsg = "Error attempting to launch web browser";

	public static void openURL(String url) {
		// on ajout http autrement ça plantouille!
		url = "http://" + url;
		try {
			Class<?> d = Class.forName("java.awt.Desktop");
			d.getDeclaredMethod("browse", new Class[] { java.net.URI.class })
					.invoke(d.getDeclaredMethod("getDesktop").invoke(null),
							new Object[] { java.net.URI.create(url) });
		} catch (Exception ignore) {
			String osName = System.getProperty("os.name");
			try {
				if (Desktop.isDesktopSupported()) {
					Desktop desktop = Desktop.getDesktop();
					desktop.browse(new URI(url));
				} else {
					
					if (osName.startsWith("Mac OS")) {
						Class<?> fileMgr = Class
								.forName("com.apple.eio.FileManager");
						Method openURL = fileMgr.getDeclaredMethod("openURL",
								new Class[] { String.class });
						openURL.invoke(null, new Object[] { url });
					} else if (osName.startsWith("Windows"))
						Runtime.getRuntime().exec(
								"rundll32 url.dll,FileProtocolHandler " + url);
					else {
						String browser = null;
						for (String b : browsers)
							if (browser == null
							&& Runtime.getRuntime()
							.exec(new String[] { "which", b })
							.getInputStream().read() != -1)
								Runtime.getRuntime().exec(
										new String[] { browser = b, url });
						if (browser == null)
							throw new Exception(Arrays.toString(browsers));
					}
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,
						errMsg + "\n" + e.toString());
			}
		}
	}

}