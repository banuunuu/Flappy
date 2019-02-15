package game;


import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class start implements ActionListener{
	//private GUI gui = new GUI();
	private Game g = new Game();
	JButton start;
	JButton hS;
	public start() {
    //gui.addObserver(this);
		setup();
	}

	private void setup() {

		// Initialise game objects
		Pipes p = new Pipes();
		Bird b = new Bird(p);
		
		// Add updatables and renderables
		g.addRenderable(p);
		g.addUpdatable(p);
		
		g.addRenderable(b);
		g.addUpdatable(b);
		
		makefram();
		
	}
		
		private void makefram() {
		JFrame frame = new JFrame("FLAPPY BIRDS");
		//frame.setFocusable( true );
	    Container contentPane = frame.getContentPane();
	    
	    contentPane.setLayout(new GridLayout(3,2,3,3));
	    
	    
	 
	start = new JButton("START");
		frame.add(start);
	    
           start.addActionListener(this);
			frame.pack();
//			//storlek på jFRame
			
			
			
			hS = new JButton("HIGH SCORE");
			frame.add(hS);
		    
	           hS.addActionListener(this);
			
			
			
             frame.setSize(1280,850);
			frame.setVisible(true);
//			
			
	}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == start)
			{	g.start();}
			
			
			if(e.getSource() == hS)
				g.hS();
			
		}

  
		

		
	}






