package about;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class AboutMe extends JFrame {

	private ImageIcon peeyush = new ImageIcon(getClass().getResource("/resources/creator.jpg"));
	
	String Text = "<html>It a file browser developed in java that supports operation performed <br>" +
			"on files including create, opening(viewing,playing,editing) renaming,<br>" +
			 " and searching of files.<br></html>";
	
	
	public AboutMe(){	
				
		addComponentListener(new ComponentAdapter() {
			
            public void componentResized(ComponentEvent e) {
                setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
            }
        });
        setUndecorated(true);
        
        JPanel p = new JPanel();
        
        
        // Ok Button
		JButton OK = new JButton();
		OK.setText("Ok");
		OK.addActionListener(new OK());
		p.add(OK);
		
		//Adding Button to the South of this Frame
		getContentPane().add(p, BorderLayout.SOUTH);

		
		p = new JPanel();
		p.setBackground(Color.black);
		
		
		//APP name
		JLabel label1 = new JLabel("Xplore Me",SwingConstants.CENTER); 
		label1.setFont(new Font( "Serif", Font.BOLD, 16 ));
		label1.setForeground(Color.RED);
		
		getContentPane().add(label1,BorderLayout.NORTH);
		
		
		// APP explanation
		p = new JPanel();
		p.setBackground(Color.black);
		JLabel label2 = new JLabel(Text);
		label2.setForeground(Color.CYAN);
		p.add(label2);
		
		
		// Developer image
		JLabel label3 = new JLabel(peeyush);
        p.add(label3);
       
        
        //Developer Information
		JLabel label4 = new JLabel();		
        label4.setText("<html>Name :     Peeyush yadav<br>" +
        		"Linkdin:   www.linkedin.com/in/peeyushy95<br>" +
        		"Email    :peeyushy95@gmail.com<br>" +
        		"Facebook    :   www.fb.com/peeyushy95<br></html>");
        
        label4.setFont(new Font( "Serif", Font.PLAIN, 14 ));
        label4.setForeground(Color.CYAN);
        
        p.add(label4);
        
		getContentPane().add(p);
		
        setSize(400, 410);
		setVisible(true);
		validate();
		
	}
	
	//Remove this Frame as soon as Ok button is pressed
	class OK implements ActionListener{
		
		public void actionPerformed(ActionEvent arg0) {
			
			// removing the current frame
			dispose();
		}
	}
}
