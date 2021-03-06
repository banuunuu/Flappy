package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;

/** 
 * @author
 *This class manages how
 *the Bird should act
 *@version
 */
public class Bird extends Observable implements Updatable, Renderable {
	
	private float x, y; // Position
	private float yVel; // Current y-velocity of bird
	private float baseYVel = -6.0f; //Velocity by default, i.e. going up (java y-axis is inverse
	private float gravity = 0.25f; //gravity pulling bird down
	private final float BASEGRAVITY = 0.25f;
	private Pipes pipes;
	private int scoredPipe = 0; // Highscore
	private int score = 0;
	private Font gameFont = new Font("Arial", Font.BOLD, 30);
	
	private BufferedImage flapUp;
	private BufferedImage flapDown;
	
	public Bird(Pipes pipes,int avatar) {
		resetBird();
		this.pipes = pipes;
		try {
			if(avatar == 1) {
			flapUp =  Sprite.getSprite("/test_ner.png"); //bird_up.png
			flapDown = Sprite.getSprite("/test_up.png"); //bird_down.png
			}
			else if(avatar == 2)
			{
				flapUp =  Sprite.getSprite("/bird_up.png"); //bird_up.png
				flapDown = Sprite.getSprite("/bird_down.png"); //bird_down.png
			}
	
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
			System.exit(1);
		}
	}
	

	/** 
	 *Resets bird position
	 *when collision with pipe
	 */
	public void resetBird() { //at object collision i.e. game-over
		x = 100;
		y = 100;	
		yVel = 0;
		gravity = 0;	
		}
	
	private void flap() {
		yVel = baseYVel;
	}
	
	

	/** 
	 *Manages how the bird moves
	 *when player interacts with it
	 */
	@Override
	public void update(Input input) {
		y += yVel;
		yVel += gravity;
		if (y < 0) {
			y = 0;
			yVel = 0;
		}
		if(input.isSpacePressed()) {
			//start gravity
			gravity = BASEGRAVITY;
			flap();
		}
		float[] pipeCoords = pipes.getCurrentPipe();
		float pipeX = pipeCoords[0];
		float pipeY = pipeCoords[1];
		//collision
		if((x >= pipeX && x <= pipeX + pipes.getPipeWidth() 
			&& (y <= pipeY || y >=pipeY + pipes.getPipeVertitalSpacing()))
			|| y >= Menu.HEIGHT) {	
			pipes.resetPipes();
			resetBird();
			int temp = score;
			score = 0;  // RESETS SCORE
			setChanged();
			notifyObservers(temp);  
		}
		else {
			int currentPipeID = pipes.getCurrentPipeID();
			score = (scoredPipe != currentPipeID) ? score + 1 : score;
			scoredPipe = currentPipeID;
		}
	}
	
	@Override
	public void render(Graphics2D g, float interpolation) {
		g.setColor(Color.BLUE);
		g.drawImage(yVel <= 0 ? flapUp : flapDown, (int) x, (int) (y + (yVel * interpolation)), null);
		g.setFont(gameFont);
		g.drawString("Score :" + score, 20, 50);
	}
}
