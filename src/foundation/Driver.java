package foundation;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class Driver{									// Driver Class for Program
		
	static String ROOT_FILE_PATH = "F://";   // path for finding documents
	
	
	public static void main(String[] args) {
		    		
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				
		            if ("Nimbus".equals(info.getName())) {
		            	
		                try {
		                	
							UIManager.setLookAndFeel(info.getClassName());						
		                } 
		                catch (ClassNotFoundException | InstantiationException| IllegalAccessException|
		                		UnsupportedLookAndFeelException e) {							
		                	
		                	e.printStackTrace();						
		                }
		                
		                break;
		            }
	        }
			
			
			EventQueue.invokeLater(new Runnable(){
				
	           public void run(){
	        	   
					Structure frame;
					frame = new Structure(ROOT_FILE_PATH);
					
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
					frame.setLocation(100,0);
					frame.setSize(950, 727);
					//frame.setResizable(false);
	           }
	           
		    });
	}
}