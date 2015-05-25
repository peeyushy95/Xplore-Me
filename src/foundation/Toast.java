package foundation;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;


public class Toast extends JDialog {

	// Text Will be Displayed as Toast Message
    String text;
    JFrame frame;

    
    //Constructor
    public Toast(JFrame frame, boolean modal, String msg) {
    	
        super(frame, modal);
        text = msg;
        this.frame=frame;
        initComponents(); 
        
    }
    
    // Checking,Creating Toast Message Frame
    private void initComponents() {
       
    	setLayout(new FlowLayout(FlowLayout.CENTER));
        
        addComponentListener(new ComponentAdapter() {       	
            
        	public void componentResized(ComponentEvent e) {
                setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
            }
        });
        
        setUndecorated(true);
        
        setSize(250, 35);
        setLocationRelativeTo(frame);
        
        getContentPane().setBackground(new Color( 10,10,10,112 ) );
        
        
        // Determine what the GraphicsDevice can support.
        GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
        
        
        // Determining Graphic Device
        GraphicsDevice gdevice = genv.getDefaultScreenDevice();
        
        
        // checking if Translucent Dialog is sopported or not
        boolean isTranslucencySupported =  gdevice.isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.TRANSLUCENT);

        
        //If supported 
        if(isTranslucencySupported) 	 
        						setOpacity(0.9f);
        
        else							 
        						System.out.println("Translucency is not supported");
        
        
        //JLabel for Displaying Text
        JLabel label = new JLabel();
        label.setForeground(Color.cyan);
        
        label.setText(text);
        label.setFont(new Font( "Serif", Font.BOLD, 16 ));
        add(label);
        
    
    }
    
}