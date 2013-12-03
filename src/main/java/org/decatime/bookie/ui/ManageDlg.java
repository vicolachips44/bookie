package org.decatime.bookie.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.decatime.bookie.ApplicationFacade;
import org.decatime.bookie.Events;

public class ManageDlg extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7139380787631485645L;
	private JPanel contentPanel = null;
	private JCheckBox chckbxCloseOnNavigate = null;
	private BookmarkList bookmarkList = null;
	
	/**
	 * Create the dialog.
	 */
	public ManageDlg() {
		this.initialize();		
	}
	
	private void initialize() {
		setBounds(100, 100, 800, 600);
		contentPanel = new JPanel();
		contentPanel.setBounds(0, 0, 784, 525);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);
		
		bookmarkList = new BookmarkList(this);
		bookmarkList.setBounds(5, 5, 774, 503);
		contentPanel.add(bookmarkList);
		{
			JButton okButton = new JButton("OK");
			okButton.setBounds(671, 520, 108, 42);
			contentPanel.add(okButton);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					doClose(evt);
				}
			});
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		getContentPane().add(contentPanel);
		
		chckbxCloseOnNavigate = new JCheckBox("Close on navigate");
		chckbxCloseOnNavigate.setSelected(true);
		chckbxCloseOnNavigate.setBounds(492, 530, 166, 23);
		contentPanel.add(chckbxCloseOnNavigate);
	}

	private void doClose(ActionEvent evt) {
		ApplicationFacade.getInstance().sendNotification(Events.Bookmarks.CLOSE_BOOKMARKS, this, null);
	}
	
	public boolean getDoCloseOnNavigate() {
		return this.chckbxCloseOnNavigate.isSelected();
	}

	public BookmarkList getBookmarkList() {
		return this.bookmarkList;
	}
}
