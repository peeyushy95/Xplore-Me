package features;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import about.AboutMe;
import foundation.Structure;

public class OneTimeFeatures {

	Structure frame;
	
	public OneTimeFeatures(Structure frame){
		this.frame = frame;
	}
	
	
	// Refresh JFrame
	public void refresh(String ROOT_FILE_PATH){
		
		
	    //Remove all Components
	  	frame.getContentPane().removeAll();
	  	frame.getContentPane().revalidate();
	  	frame.getContentPane().repaint();
		
		//Redraw all Components
		frame.componentDrawing(ROOT_FILE_PATH);
		
	}
	
	
	//About button Actionlistener
	public class About implements ActionListener{
				 
			public void actionPerformed(ActionEvent e) {
				 	
					//Displaying About Frame
					AboutMe Aboutframe = new AboutMe();
					Aboutframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					Aboutframe.setVisible(true);
					Aboutframe.setLocation(350 + 150*(frame.frame_width/900),100);	
					Aboutframe.setResizable(false);
					
	         }
	}
	
	
	//Minimize button Actionlistener
	public class Min implements ActionListener{
			
			public void actionPerformed(ActionEvent e) {
	           
				//Minimise the App
				frame.setState(Frame.ICONIFIED);
				
	         }
	}
	
	
	
	//Maximize button Actionlistener
	public class Max implements ActionListener{
					
			public void actionPerformed(ActionEvent e) {
	           
				//Changing the size of App
				
				//IF button's Text was Max then Increase the size					
				if(frame.Max.getText().compareTo("Max") == 0){
					
					frame.setLocation(0,0);
					frame.setSize(1368, 728);
					frame.Max.setText("Res");
					frame.frame_width = 1320;
					
				}
				else{
					
					frame.setLocation(100,0);
					frame.setSize(950, 728);
					frame.Max.setText("Max");
					frame.frame_width = 880;
				}
				
				frame.Go.doClick();
				
	         }
	}
	
	
	//Exit button Actionlistener
	public class Exit implements ActionListener{
				
			public void actionPerformed(ActionEvent e) {
				
				//Exit the App
				System.exit(frame.NORMAL);
				
			  }	
	}
	
	
	
}
