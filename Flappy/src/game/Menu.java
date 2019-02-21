package game;
import java.awt.GridLayout;

import javax.swing.*;


public class Menu {
	
	private JFrame menu;
	public Menu() {
		
	menu = new JFrame();
	menu.setLayout(new GridLayout(2,1));
	JButton startButton = new JButton("Start");
	startButton.addActionListener(e -> {
		new Thread(new Start(new JFrame())).start();
	});
	menu.add(startButton);
	JButton highScore = new JButton("Highscore");
	highScore.addActionListener(e -> {
		// Skriv här
		new Thread(new Highscores()).start();
	});
	menu.add(highScore);
	menu.pack();
	menu.setDefaultCloseOperation(1);
	menu.setVisible(true);
}


}
