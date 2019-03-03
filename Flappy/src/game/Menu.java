package game;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

public class Menu implements Observer {
	public final static int WIDTH = 800, HEIGHT = 600;
	private String gameName = "Flaccidbird";
	private Game g;
	private JFrame menu;
	private JPanel panel;
	private JTextArea txt;

	public Menu() {
		makeMenu();
	}

	private void makeMenu() {
		Dimension gameSize = new Dimension(WIDTH, HEIGHT); 
		menu = new JFrame();
		menu.setTitle(gameName);
		menu.setSize(gameSize); 
		//menu.setResizable(false);
		panel = new JPanel();
		panel.setSize(gameSize);
		panel.setLayout(new GridLayout(3,3));
		JButton startButton = new JButton("Start ",new ImageIcon(this.getClass().getResource("/start.jpg")));
		startButton.addActionListener(e -> {
			g = new Game(this);
			init();
			menu.getContentPane().removeAll();
			g.activity.start();	
		});

		panel.add(startButton);
		JButton HS_off = new JButton("HIGHSCORE ",new ImageIcon(this.getClass().getResource("/photo.jpg")));
		HS_off.addActionListener(e -> {
			new Thread(new Highscores()).start();
		});
		panel.add(HS_off);
		JPanel label = new JPanel();
		label.setLayout(new BoxLayout(label, BoxLayout.X_AXIS));
		label.add(new JLabel("Enter username : "));
		txt = new JTextArea(200,10);
		
		//namnet på windows datorn används som standard
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


	public void testMenu(){
		menu.add(panel);
	}	

	public JFrame getFrame() {
		return menu;	
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
			//Sam du kanske kan använda denna score för server
			int LastScore = (Integer) arg1;
			String username = txt.getText();
			System.out.println("Test: " + username + " " + LastScore+ "\n");
			//interrupt game Loop
			g.activity.interrupt();
		}
	}
}