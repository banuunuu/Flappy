package game;

import javax.swing.*;

public class Start implements Runnable {
	Game g;
	public Start(JFrame menu) {
		g = new Game(menu);
		
		// Initialise game objects
		Pipes p = new Pipes();
		Bird b = new Bird(p);
		
		// Add updatables and renderables
		g.addRenderable(p);
		g.addUpdatable(p);
		
		g.addRenderable(b);
		g.addUpdatable(b);
		
		// Start
		//g.start();
	}

	@Override
	public void run() {
		g.start();
		// TODO Auto-generated method stub
		
	}
}
