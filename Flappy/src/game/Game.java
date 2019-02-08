package game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.util.ArrayList;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import java.awt.Graphics2D;


public class Game {
	
	
	public final static int WIDTH = 800, HEIGHT = 600;
			
	private String gameName = "Flaccidbird";
	
	private Canvas game = new Canvas();
	
	private Input input;
	
	private ArrayList<Updatable> updatables = new ArrayList<>();
	private ArrayList<Renderable> renderables = new ArrayList<>();
	
	public void addUpdatable(Updatable u) {
		updatables.add(u);
	}
	
	public void removeUpdatable(Updatable u) {
		updatables.remove(u);
	}
	
	public void addRenderable(Renderable r) {
		renderables.add(r);
	}
	
	public void removeRenderable(Renderable r) {
		renderables.remove(r);
	}
	
	public void start() {
		 //Init window: canvas and jFrame
		Dimension gameSize = new Dimension(Game.WIDTH, Game.HEIGHT); 
		JFrame gameWindow = new JFrame(gameName);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setSize(gameSize); 
		gameWindow.setResizable(false);
		gameWindow.setVisible(true);
		game.setSize(gameSize);
		game.setMinimumSize(gameSize);
		game.setMaximumSize(gameSize);
		game.setPreferredSize(gameSize);
		gameWindow.add(game);
		gameWindow.setLocationRelativeTo (null);
		
		//Init input
		input = new Input();
		game.addKeyListener(input);
		
		//Game loop
		final int TICKS_PER_SECOND = 60; //one update of the game loop, 60fps
		final int TIME_PER_TICK = 1000 / TICKS_PER_SECOND; //in milliseconds
		final int MAX_FRAMESKIPS = 5; //max amount of updates per render
		
		long nextGameTick = System.currentTimeMillis(); //lets us make time-based calculations in-game
		int loops;
		float interpolation; //gives number of times we have updated for each render, sync objects with rendered frame
		
		long timeAtLastFPSCheck = 0; //check frames per second, each second
		int ticks = 0;
		
		boolean running = true;
		while (true) {
			//Updating
			loops = 0; //max 5, after 5 game has to be rendered
			
			while (System.currentTimeMillis() > nextGameTick && loops < MAX_FRAMESKIPS) {
				 update(); //loops through every updatable object
				 ticks++; //update means a game tick
				 
				 nextGameTick+= TIME_PER_TICK; //Predicted time, gives time the next tick SHOULD be done by
				 loops++;
				 
			}
			
			//Rendering
			interpolation = (float) (System.currentTimeMillis() + TIME_PER_TICK - nextGameTick) //comparison between actual time and predicted time
							/ (float) TIME_PER_TICK; //i.e. if computer is slow
			render(interpolation);
			
			//FPS Check
			if (System.currentTimeMillis() - timeAtLastFPSCheck >= 1000) {
				System.out.println("FPS: " + ticks);
				gameWindow.setTitle(gameName + " - FPS:" + ticks);
				ticks = 0; //reset FPS every second
				timeAtLastFPSCheck = System.currentTimeMillis();
			}
			
		}
		//Game ends
	}
	
	private void update() {
		for(Updatable u : updatables) {
			u.update(input);
			}
		}
	
	private void render(float interpolation) {  //renders all game objects
		
		BufferStrategy b = game.getBufferStrategy();
		if (b == null) {
			game.createBufferStrategy(2); //create double buffer to insure user doesn't see screen getting cleared and re-rendered
			return; //double buffer ensures there is a frame shown when screen is cleared
		}
		
		Graphics2D g =(Graphics2D) b.getDrawGraphics();
		g.clearRect(0, 0, game.getWidth(), game.getHeight());
		
		for(Renderable r : renderables) {
			r.render(g, interpolation);
			}
		g.dispose();
		b.show();
		}
}
 