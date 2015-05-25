package draw;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;



import features.MouseAction;
import features.RightClickFunctionality;
import foundation.*;

public class DrawFileFolder {

	
	static File[] allSubFiles;
	static String buttons="button";
	
	
	// For Displaying Files and Folder in current Directory
	public JScrollPane  draw(Structure c,RightClickFunctionality RightClickFeature,MouseAction mouse_button,
			MouseAction mouse_panel,JPanel p, String ROOT_FILE_PATH, HashMap<String,String> buttonMap, int frame_width ){
		    
		    //Accessing Files in the given Path
			File f=new File(ROOT_FILE_PATH);			
	        allSubFiles=f.listFiles();										// allSubFiles contains all content inside Path folder
	        
	        p.setLayout(null);
	        
	        // Right Click Feature
	        p.addMouseListener(mouse_panel.new PopupTriggerListener(new JButton()));
	       
	        
			int x = 10,y = 10,buttonOffset=0 ,maxx=0, maxy=0;
			
			if(frame_width == 880) x = 20;
			
			// if no file or Folder OR not accessible
			if(allSubFiles != null){				
				
				
				// Assigning button to each File or Folder
				for (File file : allSubFiles) {
		           	
			        	String s = file.getName();
			        	String uri = file.getAbsolutePath();
			            JButton button = new JButton(buttons + buttonOffset);
			            
			            // Full Horizontal Line Filled With Button
			            if(x>frame_width){				
			           // if(x > c.getHeight()){
			            	
			            	//Incrementing Y
			            	y +=50;
			            	x = 10;
			            	if(frame_width == 880) x = 20;
			            	
			            }
			            
			            if(x > maxx ) 
			            				maxx = x;
			            
			            if(y > maxy )
			            				maxy = y;
			            
			            // Button Position
					    button.setBounds(x,y,143,33);
					    
					    // Colour
					    button.setForeground(Color.cyan);							//button text color
					    button.setBackground(new Color( 10,10,10,112 ) );           // button background colour	
					    
					    // Button Text = File or Folder name
					    button.setText(s);
					    
					    // mapping button to string for identifying it later
					    buttonMap.put(buttons + buttonOffset,uri);				    
					   
					    // string for identifying button it later
					    button.setActionCommand(buttons + buttonOffset++);			
					    	   			   			
					    // action listener for button
					    button.addActionListener(c.new OpenUrlAction());		
					    
						button.setLayout(null);
						
						//Right Click on Button
						button.addMouseListener(mouse_button.new PopupTriggerListener(button));
						p.add(button);
						
						x += 150;													//offset for button
			    
		        }
			}
			
			RightClickFeature.buttonMap = buttonMap;
	        p.setPreferredSize(new Dimension(maxx,maxy+50));
	        
	        JScrollPane scrollBar=new JScrollPane(p,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	        scrollBar.setLocation(300, 300);
	        
	        return scrollBar;
	        
		}
}
