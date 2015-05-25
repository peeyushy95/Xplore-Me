package features;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;

public class MouseAction {

	JPopupMenu menu ;
	RightClickFunctionality RightClickFeature;
	
	//Constructor
	public MouseAction(JPopupMenu menu , RightClickFunctionality RightClickFeature){
		
		this.menu = menu;
		this.RightClickFeature = RightClickFeature;
	}
	
	// Right Click Features
	public class PopupTriggerListener extends MouseAdapter {
		JButton button; 
		
		public PopupTriggerListener(JButton button) {
			
			this.button = button;
			
		}
		
		
		
    	public void mousePressed(MouseEvent ev) {	        
    		
    		if (ev.isPopupTrigger()) {
    			RightClickFeature.button = button;
    			menu.show(ev.getComponent(), ev.getX(), ev.getY());
	        }
    	}

   	    public void mouseReleased(MouseEvent ev) {
	    	
   	    	if (ev.isPopupTrigger()) {
   	    		RightClickFeature.button = button;
   	    		menu.show(ev.getComponent(), ev.getX(), ev.getY());
	        }
	     }
    
	    public void mouseClicked(MouseEvent ev) {
	    	
	    }
	    
    }
	
	
	// Frame Movement Functionality
	public class MoveMouseListener implements MouseListener, MouseMotionListener {
		 
		
		  JFrame target;
		  Point start_drag;
		  Point start_loc;

		  public MoveMouseListener(JFrame target) {
			  
		    this.target = target;
		    
		  }


		  Point getScreenLocation(MouseEvent e) {
		    
			  Point cursor = e.getPoint();
			  Point target_location = this.target.getLocationOnScreen();
		    
			  return new Point((int) (target_location.getX() + cursor.getX()),
					  		   (int) (target_location.getY() + cursor.getY()));
		  
		  }

		  public void mouseClicked(MouseEvent e) {
		  }

		  public void mouseEntered(MouseEvent e) {
		  }

		  public void mouseExited(MouseEvent e) {
		  }

		  public void mousePressed(MouseEvent e) {
		    
			  this.start_drag = this.getScreenLocation(e);
			  this.start_loc =  this.target.getLocation();
			  //this.start_loc = this.getFrame(this.target).getLocation();
		 
		  }

		  public void mouseReleased(MouseEvent e) {
		  }

		  public void mouseDragged(MouseEvent e) {
		    
			  Point current = this.getScreenLocation(e);
			  Point offset = new Point((int) current.getX() - (int) start_drag.getX(),
		    						   (int) current.getY() - (int) start_drag.getY());
		    
			  //JFrame frame = this.getFrame(target);
			  JFrame frame = this.target;
			  
			  Point new_location = new Point((int) (this.start_loc.getX() + offset.getX()), 
					  						 (int) (this.start_loc.getY() + offset.getY()));
			  
			  frame.setLocation(new_location);
		  }

		  public void mouseMoved(MouseEvent e) {
		  }
		  
		}
}
