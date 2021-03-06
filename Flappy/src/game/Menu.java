package game;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

/** 
 * @author Aleksander Pantic
 * The Menu class is responsible
 * for the main menu
 * the main-menu triggers other functions such
 * as starting the game and viewing highscore
 * @version 2019-03-07
 */
public class Menu implements Observer {
	public final static int WIDTH = 800, HEIGHT = 600;
	private String gameName = "Flaccidbird";
	private Game g;
	private JFrame menu;
	private JPanel panel;
	private JTextArea txt;
	private Highscores h;
	private int avatar = 1; //1 = f�gel, 2 = boll

	/**
	 * Makes the JFrame and components
	 * such as START and Highscore button
	 * start button starts a new game and thread when pressed
	 * starts a new Highscore Thread
	 */
	public Menu() {
		h = new Highscores();
		h.activity.start();
		makeMenu();
	}

	
	/**
	 * Initilizes the Frame
	 * and constructs a main-menu
	 * with start and hishscore buttons
	 */
	private void makeMenu() {
		Dimension gameSize = new Dimension(WIDTH, HEIGHT); 
		menu = new JFrame();
		menu.setTitle(gameName);
		menu.setSize(gameSize); 
		menu.setResizable(false);
		panel = new JPanel();
		panel.setSize(gameSize);
		panel.setLayout(new GridLayout(3,3));
		JButton startButton = new JButton("Start ",new ImageIcon(this.getClass().getResource("/sturt.png")));
		startButton.addActionListener(e -> {
			g = new Game(this);
			init();
			menu.getContentPane().removeAll();
			g.activity.start();	
		});

		panel.add(startButton);
		JButton HS_off = new JButton("HIGHSCORE ",new ImageIcon(this.getClass().getResource("/photo.jpg")));
		HS_off.addActionListener(e -> {
			h.setVisible(true);
		});
		panel.add(HS_off);
		JPanel label = new JPanel();
		label.setLayout(new GridLayout(3,5));
	
		
		JButton bird = new JButton("Bird ",new ImageIcon(this.getClass().getResource("/test_ner.png")));
		bird.addActionListener(e -> {
			avatar = 1;
		});
		
		JButton ball = new JButton("Ball ",new ImageIcon(this.getClass().getResource("/bird_up.png")));
		ball.addActionListener(e -> {
			avatar = 2;
		});
		label.add(bird);
		label.add(ball);
		label.add(new JLabel("Enter username : "));
		txt = new JTextArea(200,10);
		//namnet p� windows datorn anv�nds som standard
		txt.setText(System.getProperty("user.name"));
		label.add(txt);
		label.add(new JLabel(new ImageIcon(this.getClass().getResource("/chs.png"))));
		panel.add(label);
		menu.add(panel);
		menu.pack();
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setSize(800, 600);
		menu.setVisible(true);	
	}

	/**
	 *Puts the Main-menu contentpane
	 *on the JFrame(shows main-menu)
	 */
	public void testMenu(){
		menu.add(panel);
	}	

	/**
	 * When called returns JFrame
	 * @return JFrame of the game
	 */
	public JFrame getFrame() {
		return menu;	
	}
	
	
	/**
	 *Performs crucial setup for the game Loop
	 */
	private void init() {
		// Initialise game objects
		Pipes p = new Pipes();
		Bird b = new Bird(p,avatar);
		b.addObserver(this);
		// Add updatables and renderables
		g.addRenderable(p);
		g.addUpdatable(p);
		g.addRenderable(b);
		g.addUpdatable(b);
	}

	private void sendValue(int score, String name) {
		String highscore = (score + " " + name);
		try {
			DatagramSocket socket = new DatagramSocket();
			InetAddress addr = InetAddress.getLocalHost();
			socket.send(new DatagramPacket(highscore.getBytes(), highscore.length(), addr, 6896));
			socket.close();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "IO error", "IO error", 1);
		}
	}

	/**
	 *Interrupts the game by interrupting the game thread
	 *stores score from the game
	 *Sends score and username to the HighscoreList
	 *Is called by the Observable class Bird
	 * @param arg0 an Bird object
	 * @param arg1 an Integer containing the score
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg0 instanceof Bird && arg1 instanceof Integer) {
			//Sam du kanske kan anv�nda denna score f�r server
			int LastScore = (Integer) arg1;
			String username = txt.getText();
			sendValue(LastScore, username);
			System.out.println("Test: " + username + " " + LastScore+ "\n");
			//interrupt game Loop
			g.activity.interrupt();
		}
	}
}