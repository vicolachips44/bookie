package org.decatime.bookie.ui;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import org.decatime.bookie.ApplicationFacade;
import org.decatime.bookie.Events;
import org.decatime.bookie.tools.BareBonesBrowserLaunch;
import org.jdesktop.swingx.JXTable;

public class BookmarkList extends JScrollPane implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1388644257242020865L;
	
	JXTable table;
	JPopupMenu popupMenu;
	ManageDlg manageDlg;
	Vector<String> columns;
	
	public BookmarkList() {
		initialize();
	}
	
	public BookmarkList(ManageDlg manageDlg) {
		this.manageDlg = manageDlg;
		initialize();
	}
	
	private void initialize() {
		setViewportBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			
		table = new JXTable() {
			private static final long serialVersionUID = -2596195795972810976L;

			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};
		columns = new Vector<String>();
		columns.add("Category");
		columns.add("Description");
		columns.add("Url");
		
		table.addMouseListener(this);
		
		popupMenu = new JPopupMenu();
		
		JMenuItem mnuNavigate = new JMenuItem("Navigate");
		mnuNavigate.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				doNavigate();
			}
		});
		popupMenu.addSeparator();
		
		JMenuItem mnuEdit = new JMenuItem("Edit...");
		mnuEdit.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				doEdit();
			}
		});
		
		popupMenu.add(mnuNavigate);
		popupMenu.add(mnuEdit);
		
		setViewportView(table);
	}
	
	private void doNavigate() {
		if (table.getSelectedRow() < 0) {
			return;
		}
		
		int row = table.convertRowIndexToModel(table.getSelectedRow());
		String url = (String) table.getModel().getValueAt(row, 2);
		BareBonesBrowserLaunch.openURL(url);
		if (this.manageDlg.getDoCloseOnNavigate()) {
			ApplicationFacade.getInstance().sendNotification(Events.Bookmarks.CLOSE_BOOKMARKS, null, null);
		}
	}
	
	private void doEdit() {
		ApplicationFacade.getInstance().sendNotification(
			Events.Bookmarks.REQ_EDIT_BOOKMARK, 
			new Object[] {table, this.getTopLevelAncestor()}, null
		);
	}
	
	public void load(Vector<Object> content) {
		DefaultTableModel tmodel = new DefaultTableModel (content, columns);
		table.setModel(tmodel);
	}
	
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			this.doNavigate();
		}
	}
	public void mouseEntered(MouseEvent e) {
	}	
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
		if (e.getButton() != MouseEvent.BUTTON3) {
			return;
		}
		this.popupMenu.show(e.getComponent(), e.getX(), e.getY());
	}
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() != MouseEvent.BUTTON3) {
			return;
		}
		int rowUnderMouse = this.table.rowAtPoint(new Point(e.getX(), e.getY()));
		this.table.getSelectionModel().setSelectionInterval(rowUnderMouse, rowUnderMouse);
		this.popupMenu.show(e.getComponent(), e.getX(), e.getY());
	}
}
