package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Pipes implements Renderable, Updatable {

	private int pipeWidth = 100;
	private int pipeHorizontalSpacing = 210;
	private int pipeVerticalSpacing = 180;
	
	private float xVel = -5.0f;
	private float x1, x2, x3;
	private float y1, y2, y3;
	
	//Pipe closest to bird
	private int currentPipe;
	//Array to keep pipe coordinates
	private float[][] pipeCoords = new float[3] [2]; //3 pipes each with x and y  coords
	
	private Random rand;
	
	public Pipes() {
		rand = new Random();
		
		resetPipes();
	}
	
	public void resetPipes() {
		currentPipe = 0; //First pipe
		
		x1 = Game.WIDTH * 2;
		x2 = x1 + pipeWidth + pipeHorizontalSpacing;
		x3 = x2 + pipeWidth + pipeHorizontalSpacing;
		
		y1 = getRandomY();
		y2 = getRandomY();
		y3 = getRandomY();
	}
	
	private int getRandomY() {
		return rand.nextInt((int)(Game.HEIGHT * 0.4f)) + (Game.HEIGHT / 10);
	}
	
	@Override
	public void update(Input input) {
		x1 += xVel;
		x2 += xVel;
		x3 += xVel;
		
		if (x1 + pipeWidth < 0) {
			x1 = Game.WIDTH;
			y1 = getRandomY();
			currentPipe = 1;
		}
		if (x2 + pipeWidth < 0) {
			x2 = Game.WIDTH;
			y2 = getRandomY();
			currentPipe = 2;
		}
		if (x3 + pipeWidth < 0) {
			x3 = Game.WIDTH;
			y3 = getRandomY();
			currentPipe = 0;
		}
		
		//Update pipe coordinates
		switch(currentPipe) {
			case 0:
				pipeCoords[0][0] = x1;
				pipeCoords[0][1] = y1;
				break;
			case 1:
				pipeCoords[1][0] = x2;
				pipeCoords[1][1] = y2;
				break;
			case 2:
				pipeCoords[2][0] = x3;
				pipeCoords[2][1] = y3;
				break;
		}

		
	}

	@Override
	public void render(Graphics2D g, float interpolation) {
		g.setColor(Color.GREEN);
		
		// 1
		g.fillRect((int) (x1 + (xVel * interpolation)), 0, pipeWidth, (int) y1);
		g.fillRect((int) (x1 + (xVel * interpolation)), (int) (y1 + pipeVerticalSpacing), pipeWidth, (int) Game.HEIGHT);
		
		// 2
		g.fillRect((int) (x2 + (xVel * interpolation)), 0, pipeWidth, (int) y2);
		g.fillRect((int) (x2 + (xVel * interpolation)), (int) (y2 + pipeVerticalSpacing), pipeWidth, (int) Game.HEIGHT);
		
		// 3
		g.fillRect((int) (x3 + (xVel * interpolation)), 0, pipeWidth, (int) y3);
		g.fillRect((int) (x3 + (xVel * interpolation)), (int) (y3 + pipeVerticalSpacing), pipeWidth, (int) Game.HEIGHT);
		
	}
	
	public float[] getCurrentPipe() {
		return pipeCoords[currentPipe];
	}
	
	public int getCurrentPipeID() {
		return currentPipe;
	}
	
	public int getPipeWidth() {
		return pipeWidth;
	}
	
	public int getPipeHorizontalSpacing() {
		return pipeHorizontalSpacing;
	}
	
	public int getPipeVertitalSpacing() {
		return pipeVerticalSpacing;
	}
	
}
