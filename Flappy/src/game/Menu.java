package game;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

public class Menu implements Observer {
	public final static int WIDTH = 800, HEIGHT = 600;
	private String gameName = "Flaccidbird";
	private Game g;
	private JFrame menu;
	public Menu() {
		makeMenu();
		init();
	}

	
	//fr�ga  om JFrame om det �r b�ttre om olika �ppnas eller inte 
	//det blir mindre kohesion om olika frames anv�nds
	//d� beh�ver jag bara anv�nda setVisible
	//och beh�ver inte �terv�nda till Main eller?
	private void makeMenu() {
		
/*<<<<<<< HEAD
	menu = new JFrame();
	menu.setLayout(new GridLayout(2,1));
	JButton startButton = new JButton("Start");
	startButton.addActionListener(e -> {
		new Thread(new Start(new JFrame())).start();
	});
	menu.add(startButton);
	JButton highScore = new JButton("Highscore");
	highScore.addActionListener(e -> {
		// Skriv h�r
		new Thread(new Highscores()).start();
	});
	menu.add(highScore);
	menu.pack();
	menu.setDefaultCloseOperation(1);
	menu.setVisible(true);
=======*/
		Dimension gameSize = new Dimension(WIDTH, HEIGHT); 
		menu = new JFrame();
		
		menu.setTitle(gameName);
		menu.setSize(gameSize); 
		menu.setResizable(false);
		
		g = new Game(menu);
		menu.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

        
		JButton startButton = new JButton("Start ",new ImageIcon(this.getClass().getResource("/start.jpg")));
		startButton.addActionListener(e -> {
			menu.getContentPane().removeAll();
			g.activity.start();	
		});

		c.ipady = 300;      //make this component tall
		c.ipadx = 800;      //make it width
		c.weightx = 0.0;
		c.gridwidth = 0;
		c.gridx = 0;
		c.gridy = 0;
		menu.add(startButton,c);

		JButton HS_off = new JButton("HIGHSCORE ",new ImageIcon(this.getClass().getResource("/photo.jpg")));
		

		HS_off.addActionListener(e -> {
			
			new Thread(new Highscores()).start();
			
		});
		
		c.ipady = 300;      //make this component tall
		c.ipadx = 800;      //make it width
		c.weightx = 0.0;
		c.gridwidth = 0;
		c.gridx = 0;
		c.gridy = 1;
		
		
		menu.add(HS_off,c);
		menu.pack();
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setSize(800, 600);
		menu.setVisible(true);	
	}


	private void init() {
		// Initialise game objects
		Pipes p = new Pipes();
		Bird b = new Bird(p);
		b.addObserver(this);

		// Add updatables and renderables
		g.addRenderable(p);
		g.addUpdatable(p);
		g.addRenderable(b);
		g.addUpdatable(b);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		
		if(arg0 instanceof Bird && arg1 instanceof Integer) {
			//Sam du kanske kan anv�nda denna score f�r server
			int LastScore = (Integer) arg1;
			//interrupt game Loop
			g.activity.interrupt();
		}
	}
//>>>>>>> branch 'master' of https://github.com/banuunuu/Flappy.git
}




