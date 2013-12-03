package org.decatime.bookie.model;

import java.util.Vector;

public interface IBookmarkProvider {
	public boolean create(String url);
	public void update(Object[] args);
	public void delete(String url);
	public Vector<Object> load();
}
