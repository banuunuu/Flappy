package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {

	private boolean spacePressed = false;
	private boolean spaceReleased = true;
	
	public boolean isSpacePressed() {
		boolean s = spacePressed;
		spacePressed = false;
		return s;
	}
	
	/*@Override
	public void actionPerformed(ActionEvent e) {
			
	}*/

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE && spaceReleased) {
			spacePressed = true;
			spaceReleased = false;
			
			System.out.println("testar gör något \n");
		}	
		
		
		if(e.getKeyCode() == KeyEvent.VK_UP ) {
		
			System.out.println("testar gör något \n");
		
		}			
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			spaceReleased = true;
		}			
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}


}