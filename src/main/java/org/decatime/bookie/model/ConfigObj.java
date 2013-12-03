package org.decatime.bookie.model;

import java.io.File;
import java.io.FileOutputStream;

import org.decatime.bookie.ApplicationFacade;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.io.xml.DomDriver;

@XStreamAlias("bookie-config")
public class ConfigObj {
	
	private String bookmarkFilePath;
	
	public ConfigObj(String path) {
		File f = new File(path);
		this.bookmarkFilePath = f.getAbsolutePath();
	}
	
	public String getBookmarkFilePath() {
		return this.bookmarkFilePath;
	}
	
	public void setBookmarkFilePath(String value) {
		this.bookmarkFilePath = value;
	}

	public boolean save() {
		XStream s     = new XStream(new DomDriver());
		s.processAnnotations(ConfigObj.class);
		try {
			FileOutputStream foutput = new FileOutputStream(ApplicationFacade.APP_CONF_FILE);
			s.toXML(this, foutput);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
}
