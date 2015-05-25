package features;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import foundation.Structure;

public class RightClickFunctionality {
	
		// Menu will shown after Right Click
	
		// Option for Right Click on Screen
		JPopupMenu menu_panel = new JPopupMenu();
		
		// Option for Right Click on Button
		JPopupMenu menu_button = new JPopupMenu();
		
		private KeyStroke keyStroke;
		private static final String key = "ENTER";
		
		JTextArea handle;
		JLabel temp ;
		public JButton button;
		
		// Main Jframe
		Structure c;
		
		
		//Button to File/Folder Mapping .... here For Renaming/ Delete purpose
	    public HashMap<String,String> buttonMap;
		
		
		//Constructor
		public RightClickFunctionality(Structure c,JTextArea handle,JLabel temp ){
			
			this.handle = handle;
			this.temp = temp;
			this.c = c;
		}
		
		
		//Items to be displayed after Right Click in Free Space
		public JPopupMenu getPanelFeature(){
			
				// 1st Item
				JMenuItem item = new JMenuItem("New File");
			   
				item.addActionListener(new ActionListener() {
				    
					 public void actionPerformed(ActionEvent e) {
				    	  	
						    temp.setVisible(true);
						    
						    // Take Input From Handle JTextArea
				    	  	handle.setVisible(true);
	
							keyStroke = KeyStroke.getKeyStroke(key);
					        Object actionKey1 = handle.getInputMap(JComponent.WHEN_FOCUSED).get(keyStroke);
					        
					        //Create New File when Enter is Pressed
					        handle.getActionMap().put(actionKey1, c.NewFile);
					    	
					    	
				      }
			    });
			    menu_panel.add(item);
			   
			    
			    // 2nd Item
			    item = new JMenuItem("New Folder");
			    
			    item.addActionListener(new ActionListener() {
			      
			    	public void actionPerformed(ActionEvent e) {
			    	  
			    		temp.setVisible(true);
			    		
			    		// Take Input From Handle JTextArea
			    	    handle.setVisible(true);
			    	  
			    	  	keyStroke = KeyStroke.getKeyStroke(key);
				        Object actionKey2 = handle.getInputMap(JComponent.WHEN_FOCUSED).get(keyStroke);
				        
				        //Create New Folder when Enter is Pressed
				        handle.getActionMap().put(actionKey2,c.NewFolder);
			       }
			    });
			    menu_panel.add(item);
			    
			   
			    // 3rd Item
			    item = new JMenuItem("Refresh");
			    
			    item.addActionListener(new ActionListener() {
			      
			    	public void actionPerformed(ActionEvent e) {
			    				c.refresh();
			    	
			       }
			    });
			    menu_panel.add(item);
			   
			    return menu_panel;
		}
		
		
		//Items to be displayed after Right Click in Free Space
		public JPopupMenu getButtonFeature(){
			
			JMenuItem item = new JMenuItem();
		   		
		    
		    //1st Item
		    item = new JMenuItem("Rename");
		    
		    item.addActionListener(new ActionListener() {
		     
		    	public void actionPerformed(ActionEvent e) {
		    	  

		    		temp.setVisible(true);
		    	    handle.setVisible(true);
		    		
		    		c.RenameOperation = button; 
		    		keyStroke = KeyStroke.getKeyStroke(key);
			        Object actionKey2 = handle.getInputMap(JComponent.WHEN_FOCUSED).get(keyStroke);
			        
			        //Create New Folder when Enter is Pressed
			        handle.getActionMap().put(actionKey2,c.Rename);
		    	
		       }
		    });
		    menu_button.add(item);
		    
		    //2nd Item
		    item = new JMenuItem("Delete");
		    
		    item.addActionListener(new ActionListener() {
		      
		    	public void actionPerformed(ActionEvent e) {
		    		
		    		c.RenameOperation = button; 
		    		c.delete();
		        }
		    });
		    menu_button.add(item);
		    
		    
		    return menu_button;
	}
		
	
}
