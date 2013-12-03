package org.decatime.bookie.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.decatime.bookie.ApplicationFacade;
import org.decatime.bookie.Events;
import org.decatime.bookie.model.BookmarkObj;

public class BookmarkEdit extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2643163990331443481L;
	
	private JPanel contentPanel = new JPanel();
	private BookmarkObj bookmark;
	private JTextField txtCategory;
	private JTextField txtUrl;
	private JTextField txtDescription;
	
	/**
	 * Constructor is here to display the JDialog
	 * object in design mode.
	 */
	public BookmarkEdit() {
		initialize();
	}
	
	/**
	 * Constructor called to display the JDialog object.
	 */
	public BookmarkEdit(Frame parent, BookmarkObj bookmark) {
		super(parent, ModalityType.DOCUMENT_MODAL);
		this.bookmark = bookmark;
		initialize();
	}
	
	/**
	 * Initialize the components of this object.
	 * 
	 * @return void
	 */
	private void initialize() {
		setBounds(100, 100, 411, 180);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblCategory = new JLabel("Category");
			lblCategory.setHorizontalAlignment(SwingConstants.RIGHT);
			lblCategory.setBounds(12, 24, 100, 15);
			contentPanel.add(lblCategory);
		}
		{
			JLabel lblDescription = new JLabel("Description");
			lblDescription.setHorizontalAlignment(SwingConstants.RIGHT);
			lblDescription.setBounds(12, 51, 100, 15);
			contentPanel.add(lblDescription);
		}
		{
			txtDescription = new JTextField();
			txtDescription.setSize(266, 19);
			txtDescription.setLocation(130, 48);
			txtDescription.setColumns(10);
			txtDescription.setText(this.bookmark.getDescription());
			contentPanel.add(txtDescription);
		}
		{
			JLabel lblUrl = new JLabel("Url");
			lblUrl.setHorizontalAlignment(SwingConstants.RIGHT);
			lblUrl.setBounds(12, 78, 100, 15);
			contentPanel.add(lblUrl);
		}
		
		txtCategory = new JTextField();
		txtCategory.setBounds(130, 22, 266, 19);
		txtCategory.setText(this.bookmark.getCategory());
		contentPanel.add(txtCategory);
		txtCategory.setColumns(10);
		{
			txtUrl = new JTextField();
			txtUrl.setBounds(130, 74, 266, 19);
			contentPanel.add(txtUrl);
			txtUrl.setText(this.bookmark.getUrl());
			txtUrl.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnDelete = new JButton("Delete");
				btnDelete.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						doDeleteBookmark();
					}
				});
				buttonPane.add(btnDelete);
			}
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						saveBookmarkChanges();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	/**
	 * Deletion of a bookmark method action handler.
	 */
	private void doDeleteBookmark() {
		ApplicationFacade.getInstance().sendNotification(Events.Bookmarks.DELETE_BOOKMARK, this.bookmark, null);
		ApplicationFacade.getInstance().sendNotification(Events.Navigation.CLOSE_BM_EDIT, this, null);
	}
	
	private void saveBookmarkChanges() {
		
		BookmarkObj newBookmark = new BookmarkObj(
			this.txtUrl.getText(), this.txtCategory.getText(), this.txtDescription.getText()
		);
		ApplicationFacade.getInstance().sendNotification(Events.Bookmarks.SAVE_BOOKMARK, new Object[] {this.bookmark, newBookmark}, null);
		ApplicationFacade.getInstance().sendNotification(Events.Navigation.CLOSE_BM_EDIT, this, null);
	}
}
