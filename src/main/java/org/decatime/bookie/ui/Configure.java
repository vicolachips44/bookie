package org.decatime.bookie.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.decatime.bookie.ApplicationFacade;
import org.decatime.bookie.Events;
import org.decatime.bookie.model.ConfigObj;

public class Configure extends JDialog {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5893770112922963958L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtBoomarkFilePath;
	private ConfigObj config;
	
	/**
	 * Create the dialog.
	 */
	public Configure() {
		initialize();
	}

	public Configure(ConfigObj config) {
		initialize();
		this.config = config;
		txtBoomarkFilePath.setText(config.getBookmarkFilePath());
	}

	private void initialize() {
		setTitle("Bookie - configuration");
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 146);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setAlignmentY(1.0f);
		contentPanel.setAlignmentX(1.0f);
		contentPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblXmlFilePath = new JLabel("Xml file path:");
		lblXmlFilePath.setBounds(12, 12, 104, 15);
		contentPanel.add(lblXmlFilePath);
		
		txtBoomarkFilePath = new JTextField();
		txtBoomarkFilePath.setBounds(111, 10, 323, 19);
		contentPanel.add(txtBoomarkFilePath);
		txtBoomarkFilePath.setColumns(10);
		
		JButton btnBrowse = new JButton("Browse...");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doBrowse();
			}
		});
		btnBrowse.setBounds(317, 41, 117, 25);
		contentPanel.add(btnBrowse);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						saveAndClose();
					}
				});
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						doClose();
					}
				});
			}
		}
	}
	
	private void doBrowse() {
		JFileChooser c = new JFileChooser();
	      int rVal = c.showOpenDialog(this);
	      if (rVal == JFileChooser.APPROVE_OPTION) {
	        this.txtBoomarkFilePath.setText(c.getSelectedFile().getAbsolutePath());
	      }
	}
	
	private void saveAndClose() {
		this.config.setBookmarkFilePath(this.txtBoomarkFilePath.getText());
		this.config.save();
		this.doClose();
		ApplicationFacade.getInstance().sendNotification(Events.Bookmarks.SOURCE_UPDATED, null, null);
	}
	
	private void doClose() {
		this.setVisible(false);
	}
}
