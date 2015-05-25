package foundation;

import draw.*;
import features.*;
import features.MouseAction.MoveMouseListener;
import about.AboutMe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.Timer;



public class Structure extends JFrame {

	
	// Images for App
	private ImageIcon back = new ImageIcon(getClass().getResource("/resources/back.png"));
	private ImageIcon forw = new ImageIcon(getClass().getResource("/resources/forw.png"));
	
	
	// For Saving Path Searched So Far
	int index = -1,top = -1;
	
	// Current frame width
	public int frame_width = 880;
	
	File open;
	
	// paths for finding documents
    String ROOT_FILE_PATH,TEMP_PATH;   									 
	
    //Path History
    String [] history = new String[10000];		
	  
	//Button to File/Folder Mapping
    HashMap<String,String> buttonMap;

    //handle will be visible when operations : NEW FILE,FOLDER,RENAME performed
	JTextArea jtext,handle;										 		
	
	// Right click Features
	JPopupMenu menu_panel,menu_button ;													 
	
	private static final String key = "ENTER";
    private KeyStroke keyStroke;
    
    public JButton Go,bck,fwd,About,Min,Max;
	JButton Exit;
    
    //Rename Button ONLY accessible through RightClickFunctionality
    public JButton RenameOperation;
    
    //label will be visible when operations : NEW FILE,FOLDER,RENAME performed
    JLabel  temp;												
	
    // Object of Right Click Feature
    RightClickFunctionality RightClickFeature;
    
    //Object of MouseAction
    MouseAction mouse_panel,mouse_button;
    
    OneTimeFeatures oneTime;
    
    
    //Constructor
	Structure(String input){	
		
			if(input!=null){
				
				history[++index] = input;
				top = index;			
			}
			
			// Decorating the App
			addComponentListener(new ComponentAdapter() {
	           
				public void componentResized(ComponentEvent e) {
	                setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
	            }
	        });
	        setUndecorated(true);
	        
	     
	        // TextArea ( input ) for Operation : NEWFILE,NEWFOLDER,RENAME
	        handle = new JTextArea("",0,15);
			handle.setFont(new Font( "Serif", Font.PLAIN, 13 ));
			handle.setPreferredSize(new Dimension(0,25));
			
			
			// JLabel for handle
			temp = new JLabel("Enter the Name :-");
			
			 
			
			RightClickFeature = new RightClickFunctionality(this,handle, temp);
			menu_panel =  RightClickFeature.getPanelFeature();
			menu_button = RightClickFeature.getButtonFeature();
	        
	        mouse_panel=  new MouseAction(menu_panel ,RightClickFeature);
	        mouse_button= new MouseAction(menu_button,RightClickFeature);
	        
	      //Initialising Structure
	        initialise();
	      	componentDrawing(input);
	      	
	      	
		
	}

	
	void initialise(){
		
		    oneTime = new OneTimeFeatures(this);
		
			// back button features
			bck = new JButton();		
			bck.setIcon(back);
			bck.setPreferredSize(new Dimension(50,50));
			bck.addActionListener(new BackButton());
			
			
			//Forward button features
			fwd = new JButton();		
			fwd.setIcon(forw);
			fwd.setPreferredSize(new Dimension(50,50));
			fwd.addActionListener(new ForButton());	
			
			
			// Text Area for path		
			jtext = new JTextArea("",1,48);
			jtext.setText(ROOT_FILE_PATH);
			jtext.setFont(new Font( "Serif", Font.PLAIN, 15 ));
			jtext.setPreferredSize(new Dimension(1,20));
			
			
			// Handling the case when Enter was pressed in jtext
			keyStroke = KeyStroke.getKeyStroke(key);
	        Object actionKey = jtext.getInputMap(JComponent.WHEN_FOCUSED).get(keyStroke);
	        jtext.getActionMap().put(actionKey, wrapper);
			
			
	        // Button for searching new Address
			Go = new JButton();
			Go.setText("GO");
			Go.getText();
		    Go.setForeground(Color.cyan);
		    Go.setBackground(new Color( 10,10,10,112 ) );
		    Go.addActionListener(new NewPath());
			
			
		    //About Button
			About = new JButton();
			About.setText("About");
			About.setForeground(Color.cyan);
			About.setBackground(new Color( 10,10,10,112 ) );
			About.addActionListener(oneTime.new About());
			
			
			//Minimize Button
			Min = new JButton("Min");
			Min.setText("Min");
			Min.setForeground(Color.cyan);
			Min.setBackground(new Color( 10,10,10,112 ) );
			Min.addActionListener(oneTime.new Min());
		

			//Maximize Button
			Max = new JButton("Max");
			Max.setForeground(Color.cyan);
			Max.setBackground(new Color( 10,10,10,112 ) );
			Max.addActionListener(oneTime.new Max());
			
			
			// Exit Button
			Exit = new JButton("Exit");
			Exit.setText("Exit");
			Exit.setForeground(Color.cyan);
			Exit.setBackground(new Color( 10,10,10,112 ) );
			Exit.addActionListener( oneTime.new Exit());
			
			
			//Click bck button when BACK SPACE IS pressed						
			KeyStroke Escape = KeyStroke.getKeyStroke("BACK_SPACE");
		    InputMap inputMap = bck.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		    inputMap.put(Escape, "ACTION_KEY");
		    ActionMap actionMap = bck.getActionMap();
		    actionMap.put("ACTION_KEY", actionListener);
		    bck.setActionMap(actionMap);
		    
		    
			
	}
	
	
	// checking whether path exist or not
	static boolean check(String ROOT_FILE_PATH){						
		
			File f=new File(ROOT_FILE_PATH);		
			
			if(!f.exists()) 
					return false;											
			
			return true;
			
	}     
	
	
	// Drawing Components
	public void componentDrawing(String input){						
		    		
		
		    ROOT_FILE_PATH = input;
		    jtext.setText(ROOT_FILE_PATH);
		    
		    //---------------------------------- North part of Frame--------------------------------------
			
		    
		    // For Following button
			JPanel p = new JPanel();
			
			
			//Adding Button to the Panel
			p.add(bck);			
			p.add(fwd);		        
			p.add(jtext);
		    p.add(Go);			
			p.add(About);			
			p.add(Min);			
			p.add(Max);			
			p.add(Exit);	
			
			p.setPreferredSize(new Dimension(950,55));
				    
		    //Adding panel to Frame
			getContentPane().add(p, BorderLayout.NORTH);
			
			
			
			
			 //----------------------------------center part of Frame--------------------------------------
			
			
			//Finding files and folder in current Directory
			File f=new File(ROOT_FILE_PATH);			
			File[] allSubFiles=f.listFiles();		
			int len = allSubFiles.length;
			
			//Will contain mapping of each  button to file or folder
	        buttonMap = new HashMap<String,String>();
	        
	        
			// New Jpanel for File and Folder
	        p = new Background(len); 
	        	 
	        // Drawing content of current files and folder and returning the ScrollPane Containing JPanel 
	        JScrollPane scrollBar = new DrawFileFolder().draw(this,RightClickFeature,mouse_button,mouse_panel,p,ROOT_FILE_PATH,buttonMap,frame_width);
			        
	        getContentPane().add(scrollBar);
	        
	        
	        
	        
	        //----------------------------------South part of Frame--------------------------------------
			
	        p = new JPanel();
			
			handle.setText("");
			handle.setVisible(false);
			temp.setVisible(false);
			
			p.add(temp);
			p.add(handle);
		
			p.setPreferredSize(new Dimension(950,30));
			getContentPane().add(p,BorderLayout.SOUTH);
			
			
			
			//----------------------------------------Finalize Frame--------------------------------------
			
			//For adding moving feature in App
			MoveMouseListener drag = mouse_panel.new MoveMouseListener(this);
			getContentPane().addMouseListener(drag);
			getContentPane().addMouseMotionListener(drag);
			
			setTitle(ROOT_FILE_PATH);
			setVisible(true);
			validate();
			
	}
	
	
	
	//Automatically press bck button when BackSpace is pressed
	Action actionListener = new AbstractAction() {
	      
		  public void actionPerformed(ActionEvent actionEvent) {
			  
			  	// Click bck button
	    	  	bck.doClick();
	      }
	};
	
	
	//Creating New file in present directory
	public Action NewFile = new AbstractAction() {
		
	        @Override
	        public void actionPerformed(ActionEvent ae) {
	        	
	        	String text =  handle.getText();
	        	int seperator = text.lastIndexOf(".");
	        	
	        	//Checking Validity of The String
	        	if(text.compareTo("") != 0 && seperator != -1){
	        		
	        		TEMP_PATH = ROOT_FILE_PATH + "/" + text;
	        		open = new File(TEMP_PATH);
	        		
	        		// Creating New File with name of String text
	        		int offset = 1;
	        		
	        		try {
	        			
	        			// IF file already exists then add offset
	        			while(open.exists()){
	        				
	        				TEMP_PATH = ROOT_FILE_PATH + "/" + text.substring(0,seperator) + "(" +Integer.toString(offset) + ")" + text.substring(seperator);
	        				open = new File(TEMP_PATH);
	        				
	        				offset++;
	        				
	        			}
	        			if(offset != 1){
	        				toastMessage("File Alreay Exists");
	        				
	        			}
	        			
	        			offset--;
	        			String message = text;
	        			
	        			if(offset != 0)
	        					message += offset;
	        			
	        			message +=	" is Created";
	        			
	        			toastMessage(message);
	        			
	        			//Creating NewFile
						open.createNewFile();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		
	        	}
	        	else{
	        		
	        		// IF Wrong Input show toast message
					String message = "Wrong Input";
					
					if(seperator == -1) message = "Enter the Extension of File";
					
					final JDialog dialog = new Toast(Structure.this, true,message);
			
					//Setting timer fir 500 msec
					
	                Timer timer = new Timer(500,new ActionListener() {

	                    @Override
	                    public void actionPerformed(ActionEvent e) {
	                        dialog.setVisible(false);
	                        
	                        //Remove Dialog after 500 msec
	                        dialog.dispose();
	                    }
	                });
	                timer.setRepeats(false);
	                
	                //Start timer
	                timer.start();

	                dialog.setVisible(true); 
	        		
	        	}

        		temp.setVisible(false);
	    	  	handle.setVisible(false);
	    	  	
	    	  	//Remove all Components
	    	  	getContentPane().removeAll();
				getContentPane().revalidate();
				getContentPane().repaint();
				
				//Redraw all Components
				componentDrawing(ROOT_FILE_PATH);
	        }
	};
	
	
	//Creating New folder in present directory
	public Action NewFolder = new AbstractAction() {
	       
		    @Override
	        public void actionPerformed(ActionEvent ae) {
	        	
	        	// Getting Text From Handle JTextArea
	        	String text =  handle.getText();
	        	
	        	//Checking Validity of String
	        	if(text.compareTo("") != 0){
	        		
	        		TEMP_PATH = ROOT_FILE_PATH + "/" + text;
	        		open = new File(TEMP_PATH);
	        		
	        		// Creating New File with name of String text
	        		int offset = 1;
	        		
	        		// IF Folder exists then add offset
	        		while(open.exists()){
							
							TEMP_PATH = ROOT_FILE_PATH + "/" + text + "(" +Integer.toString(offset) + ")";					
							open = new File(TEMP_PATH);
						
							offset++;
						
					}
	        		
	        		if(offset != 1){
        				toastMessage("Folder Alreay Exists");
        				
        			}
	        		offset--;
        			String message = text;
        			
        			if(offset != 0)
        					message += offset;
        			
        			message +=	" is Created";
        			
        			toastMessage(message);
	        		// Creating directory
					open.mkdir();											
	        	
	        	}
	        	else{
	        		
	        		// IF Wrong Input show toast message
					
					final JDialog dialog = new Toast(Structure.this, true, "Wrong Input");
					
					//Setting Timer for JDialog 450 msec
	                Timer timer = new Timer(450,new ActionListener() {

	                    @Override
	                    public void actionPerformed(ActionEvent e) {
	                        
	                    	dialog.setVisible(false);
	                    	//Remove Dialog after 450 msec
	                        dialog.dispose();
	                        
	                    }
	                });
	                timer.setRepeats(false);
	                
	                //Timer Start
	                timer.start();

	                dialog.setVisible(true); 
	        		
	        	}

        		temp.setVisible(false);
	    	  	handle.setVisible(false);
	    	  	
	    	    //Remove All Components
	    	  	getContentPane().removeAll();
				getContentPane().revalidate();
				getContentPane().repaint();
				
				//Redraw All Components
				componentDrawing(ROOT_FILE_PATH);
	        }
	};
	
	
	
	
	//Creating New file in present directory
	public Action Rename = new AbstractAction() {
			
		        @Override
	        public void actionPerformed(ActionEvent ae) {
	        	
	        	// Getting Text From Handle JTextArea
	        	String text =  handle.getText();
		        
	        	//Checking Validity of Input String
	        	if(text.compareTo("") != 0 ){
	        		
	        			if(text.compareTo(RenameOperation.getText()) != 0){
	        				
					    		String ButtonIdent = RenameOperation.getActionCommand();
					    		
					    		//Identifying button <-> Folder Mapping
							    String uri = buttonMap.get(ButtonIdent);
							    
							    File old_name = new File(uri);
	
						    	File new_name = new File( old_name.getParent() + "/" + text);
						    	
							    if(old_name.isDirectory()){
							    	
							    	// Creating New File with name of String text
					        		int offset = 1;
					        		
					        		// IF Folder exists then add offset
					        		while(new_name.exists()){
											
					        				new_name = new File( old_name.getParent() + "/" + text + "(" +Integer.toString(offset) + ")");									
											offset++;
										
									}
					        		
					        		// Renaming File/Folder
								    old_name.renameTo(new_name);
							    }
							    else{
							    	
							    	int seperator = text.lastIndexOf(".");
						        	
						        	//Checking Validity of The String
						        	if( seperator != -1 ){
						        		
						        		
						        		// Creating New File with name of String text
						        		int offset = 1;
						        		
						        		// IF file already exists then add offset
										while(new_name.exists()){
											
											new_name = new File(old_name.getParent() + "/" + text.substring(0,seperator) + "(" +Integer.toString(offset) + ")" + text.substring(seperator));
											
											offset++;
											
										}
										
	
										// Renaming File/Folder
										old_name.renameTo(new_name);
						        		
					        	}
					        	else{
					        		
					        		// IF Wrong Input show toast message
									String message = "Wrong Input";
									
									if(seperator == -1) message = "Enter the Extension of File";
									
									final JDialog dialog = new Toast(Structure.this, true,message);
							
									//Setting timer fir 500 msec
									
					                Timer timer = new Timer(500,new ActionListener() {

					                    @Override
					                    public void actionPerformed(ActionEvent e) {
					                        dialog.setVisible(false);
					                        
					                        //Remove Dialog after 500 msec
					                        dialog.dispose();
					                    }
					                });
					                timer.setRepeats(false);
					                
					                //Start timer
					                timer.start();

					                dialog.setVisible(true); 
					        		
					        	}
						    								    	
						    }
						    			    
						    							    
			        	
				    		//Remove all Components
				    	  	getContentPane().removeAll();
							getContentPane().revalidate();
							getContentPane().repaint();
							
							//Redraw all Components
							componentDrawing(ROOT_FILE_PATH);
	        			}
	        	}
	        	else{
	        		
		        		// IF Wrong Input -> show toast message
						
						final JDialog dialog = new Toast(Structure.this, true, "Wrong Input");
						
						//Setting Timer for JDialog 450 msec
		                Timer timer = new Timer(450,new ActionListener() {
	
		                    @Override
		                    public void actionPerformed(ActionEvent e) {
		                        
		                    	dialog.setVisible(false);
		                    	//Remove Dialog after 450 msec
		                        dialog.dispose();
		                        
		                    }
		                });
		                timer.setRepeats(false);
		                
		                //Timer Start
		                timer.start();
	
		                dialog.setVisible(true); 
	        		
	        	}
	        	
        		temp.setVisible(false);
	    	  	handle.setVisible(false);
	    	  	
	    	  	
	        }
	};
	
	
	// Delete File/Folder
	public void delete(){
		
		String ButtonIdent = RenameOperation.getActionCommand();
		
		//Identifying button <-> Folder Mapping
	    String uri = buttonMap.get(ButtonIdent);
	    
	    File f = new File(uri);
	    
	    // Deleting File/Folder
	    f.delete();
		
	    //Remove all Components
	  	getContentPane().removeAll();
		getContentPane().revalidate();
		getContentPane().repaint();
		
		//Redraw all Components
		componentDrawing(ROOT_FILE_PATH);
		
	}
		
	
	// Refresh JFrame
	public void refresh(){
		
		oneTime.refresh(ROOT_FILE_PATH);
		
	}
	
	
	
	// Toast Message
	void toastMessage(String Message){
		
		// IF Wrong Input -> show toast message
		
		final JDialog dialog = new Toast(Structure.this, true, Message);
		
		//Setting Timer for JDialog 600 msec
        Timer timer = new Timer(600,new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
            	dialog.setVisible(false);
            	//Remove Dialog after 600 msec
                dialog.dispose();
                
            }
        });
        timer.setRepeats(false);
        
        //Timer Start
        timer.start();

        dialog.setVisible(true); 
		
	}
	
	
	//Automatically press GO button when Enter is pressed in Jtext JtextArea
	private Action wrapper = new AbstractAction() {
        
		@Override
        public void actionPerformed(ActionEvent ae) {
            
			//Click the Go button
        	Go.doClick();
        }
    };
		
	
	
	//Folder and Files opening button Actionlistener
	public class OpenUrlAction implements ActionListener {						// action on clicking buttons
		
		  public void actionPerformed(ActionEvent e) {
			  
			  try {
				
				    String ButtonIdent = ((JButton) e.getSource()).getActionCommand();
				    
				    //Identifying button <-> Folder Mapping
				    String uri = buttonMap.get(ButtonIdent);
				
				    //Open the Folder
					if( new File(uri).isDirectory()) {
						
						history[++index] = uri;
						top = index;
						
						//Remove All Components
						getContentPane().removeAll();
						getContentPane().revalidate();
						getContentPane().repaint();
						
						//Redraw All Components
						componentDrawing(uri);
						jtext.setText(uri);
						
					}
					else {
						
						//Open the File
						Desktop.getDesktop().open(new File(uri));		     
					}
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		  }		  
    }
	
	
	//GO button Actionlistener
	class NewPath implements ActionListener{
		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				String uri = jtext.getText();	
				
				if( new File(uri).isDirectory()) {
					
					if(uri.compareTo(history[index])!=0){
						
						history[++index] = uri;
						top = index;
						ROOT_FILE_PATH = uri;
						
					}
					
				}
				else{
					
					// IF Invalid Address show toast message
					
					final JDialog dialog = new Toast(Structure.this, true, "Invalid Address");
			
					//Setting Timer for JDialog 450 msec
	                Timer timer = new Timer(450,new ActionListener() {
	
	                    @Override
	                    public void actionPerformed(ActionEvent e) {
	                        
	                    	dialog.setVisible(false);
	                        //After 450 msec Remove Dialog
	                        dialog.dispose();
	                    }
	                });
	                timer.setRepeats(false);
	                timer.start();
	
	                dialog.setVisible(true); 
					
					
					
				}
	
				//Remove All Components
				getContentPane().removeAll();
				getContentPane().revalidate();
				getContentPane().repaint();
				
				//Redraw All Components
				componentDrawing(ROOT_FILE_PATH);
				
				jtext.setText(ROOT_FILE_PATH);
				
			}		
	}

	//Back button Actionlistener
	class BackButton implements ActionListener{
		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
					//If index > 0  then only we have Previous History 
					if(index > 0){	
						
						//Remove All Components
						getContentPane().removeAll();
						getContentPane().revalidate();
						getContentPane().repaint();
						
						//Redraw All Components
						componentDrawing(history[--index]);
						
					}
			}		
   }

   // Forward button Actionlistener
   class ForButton implements ActionListener{
		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
					//If index < top then only we have Previous History 
					if(index < top){
						
						//Remove All Components
						getContentPane().removeAll();
						getContentPane().revalidate();
						getContentPane().repaint();
						
						//Redraw All Components
						componentDrawing(history[++index]);
						
					}
			}		
	}
    
}
