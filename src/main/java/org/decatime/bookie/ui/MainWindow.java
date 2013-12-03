package org.decatime.bookie.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.decatime.bookie.ApplicationFacade;
import org.decatime.bookie.Events;

public class MainWindow extends JDialog implements DropTargetListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3486672153140709696L;

	@SuppressWarnings("unused")
	private DropTarget dropTarget;
	
	private BufferedImage iconPanel;
	
	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setUndecorated(true);
		try {
			this.setShape(new Ellipse2D.Double(1,1,48,48));
		} catch (UnsupportedOperationException e) {
			// e.printStackTrace();
		}
		
		this.setBackground(new Color(0, 0, 0, 0));
		this.getContentPane().setBackground(new Color(0, 0, 0, 0));
		this.setSize(new Dimension(48, 48));
		this.getContentPane().setSize(new Dimension(48, 48));
		this.setAlwaysOnTop(true);
		this.setPosition();
		this.getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2) {
					manageBookmarks();
				}
			}
		});
		buildPopupMenu();
		
		dropTarget = new DropTarget(this, this);
		this.createIconPanel();
		if (this.iconPanel != null) {
			this.setIconImage(this.iconPanel);
		}
		this.getContentPane().setLayout(null);
	}
	
	private void createIconPanel() {
		String filePath = "bookie.png";
		File f = new File(filePath);
		if (f.exists() && f.canRead()) {
			try {
				this.iconPanel = ImageIO.read(new File(filePath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println(String.format("File with path %s does not exists", f.getAbsolutePath()));
		}
	}
	
	public void paint(Graphics g)
	{
		//super.paint(g);
		try {
			g.drawImage(this.iconPanel, 0, 0, 48, 48, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void buildPopupMenu() {
		
		JPopupMenu popupMenu = new JPopupMenu();
		this.addPopup(this.getContentPane(), popupMenu);
		
		JMenuItem mnuConfigure = new JMenuItem("Configure...");
		mnuConfigure.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				configure();
			}
		});
		popupMenu.add(mnuConfigure);
		
		JMenuItem mnuManage = new JMenuItem("Show bookmarks...");
		mnuManage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				manageBookmarks();
			}
		});
		popupMenu.add(mnuManage);
		
		popupMenu.addSeparator();
		
		JMenuItem mnuQuit = new JMenuItem("Quit");
		mnuQuit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				closeApplication(evt);
			}
		});
		popupMenu.add(mnuQuit);
	}
	
	private void closeApplication(ActionEvent evt) {
		ApplicationFacade.getInstance().sendNotification(Events.Navigation.APP_CLOSE, this, null);
	}
	
	private void manageBookmarks() {
		ApplicationFacade.getInstance().sendNotification(Events.Bookmarks.SHOW_BOOKMARKS, null, null);
	}
	
	private void configure() {
		ApplicationFacade.getInstance().sendNotification(Events.Navigation.CONFIGURE, null, null);
	}
	
	private void setPosition() {
		int margin = 10;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(screenSize.width - this.getSize().width - margin, 
            screenSize.height - this.getSize().height - margin
        );
	}
	
	private void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
        Transferable t = dtde.getTransferable();
        try {
            String url = (String) t.getTransferData(DataFlavor.stringFlavor);
            ApplicationFacade.getInstance().sendNotification(Events.Bookmarks.NEW, url, null);
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
	}
	@Override
	public void dragExit(DropTargetEvent dte) {
	}
	@Override
	public void dragOver(DropTargetDragEvent dtde) {	
	}
	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {	
	}
}
