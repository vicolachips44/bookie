package org.decatime.bookie;

public final class Events {
	/**
	 * Object related to navigation events
	 * 
	 * @author vga.nantes@gmail.com
	 *
	 */
	public final class Navigation {
		public static final String SHOW_ICO_WINDOW = "show_ico_window";
		public static final String STARTUP 		   = "startup";
		public static final String APP_CLOSE 	   = "app_close";
		public static final String CLOSE_BM_EDIT   = "close_bm_edit";
		public static final String CONFIGURE       = "configure";
	}
	
	/**
	 * Object related to bookmark events
	 * 
	 * @author vga.nantes@gmail.com
	 *
	 */
	public final class Bookmarks {
		public static final String INIT_BOOKMARKS    = "Bookmarks_init";
		public static final String SHOW_BOOKMARKS 	 = "Bookmarks_show";
		public static final String NEW               = "Bookmarks_new";
		public static final String CLOSE_BOOKMARKS 	 = "Bookmarks_close";
		public static final String REQ_EDIT_BOOKMARK = "Bookmarks_req_edit";
		public static final String EDIT_BOOKMARK 	 = "Bookmarks_edit";
		public static final String DELETE_BOOKMARK 	 = "Bookmarks_delete";
		public static final String BOOKMARK_DELETED  = "Bookmarks_deleted";
		public static final String SAVE_BOOKMARK 	 = "Bookmarks_save";
		public static final String BOOKMARK_SAVED    = "Bookmarks_saved";
		public static final String BOOKMARK_EXISTS   = "Bookmarks_exists";
		public static final String SOURCE_UPDATED    = "Boorkmarks_source_updated";
	}
}