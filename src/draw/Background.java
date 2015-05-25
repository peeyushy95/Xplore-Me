package draw;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Background extends JPanel {
	
	private Image image = null;
	
	// background image of app
	private ImageIcon img =  new ImageIcon(getClass().getResource("/resources/background.jpg"));   
	
	private int length;
    
	
	public Background(int length) {  	
    	this.length = length;
        this.image = img.getImage();        
    }

    @Override
    protected void paintComponent(Graphics g) {
    	
        super.paintComponent(g);
        
        //Number of times Image will be printed
        int counter = 1 + length/(13*880/170) ;
        
        // Drawing Image
        for(int i = 0 ; i<counter ;i++ ){
        
        	g.drawImage(image,0,i*image.getHeight(null),image.getWidth(null),image.getHeight(null),null);
        }
      
    }
}
