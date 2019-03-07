package game;

import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import javax.swing.*;



public class Highscores extends JFrame implements Runnable {

	
	public Thread activity = new Thread(this);
	
	JLabel label;
	InetAddress addr;
	DatagramSocket socket;
	DatagramPacket packet;
	JPanel panel;

	// Här är själva GUIet för highscoren
	public Highscores() {
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(800, 600));
		JPanel scoreGUI = new JPanel(new BorderLayout(3, 3));
		panel = new JPanel(new GridLayout(0, 1));
		JScrollPane scroll = new JScrollPane(panel);
		scroll.setPreferredSize(new Dimension(700, 550));
		scoreGUI.add(scroll, BorderLayout.CENTER);

		panel.revalidate();
		int height = (int) panel.getPreferredSize().getHeight();
		Rectangle rect = new Rectangle(0, height, 10, 10);
		panel.scrollRectToVisible(rect);

		JLabel title = new JLabel("Highscores", SwingConstants.CENTER);
		title.setFont(new Font("Serif", Font.BOLD, 34));

		add(title, BorderLayout.NORTH);
		add(scoreGUI, BorderLayout.CENTER);
		pack();
		setVisible(false);

	}

	// Detta loopas konstant och väntar upp värden
	// Från servern
	private String getHighScores() {

		try {
			addr = InetAddress.getLocalHost();
			socket = new DatagramSocket(6894);
			packet = new DatagramPacket(new byte[1024], 1024);
			socket.setSoTimeout(5000);
			socket.receive(packet);
			socket.close();
			return new String(packet.getData(), 0, packet.getLength());

		} catch (java.net.SocketTimeoutException e) {
			socket.close();
			return "";

		} catch (IOException e) {
			e.printStackTrace();
			return "";

		}
	}

	// Detta skickar ett medelande till servern
	// Som får den att skicka hela highscoret från
	// server filen. Detta är alltså enbart gjort för
	// starten.
	private void requestHighscore() {
		String str = "RequestScores";
		try {
			DatagramSocket socket = new DatagramSocket();
			
			
			InetAddress addr = InetAddress.getLocalHost();
			
			
			socket.send(new DatagramPacket(str.getBytes(), str.length(), addr, 6896));
			socket.close();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "IO error", "IO error", 1);
			System.exit(0);
		}
	}

	@Override
	public void run() {
		requestHighscore();
		while (true) {

			String highScore = getHighScores();

			// Här så har vi if satserna + färg koderna
			// För programmet.
			if (highScore != null && !highScore.isEmpty()) {
				String[] scores = highScore.split("\n");
				panel.removeAll();
				int rank = 1;
				for (String score : scores) {
					JLabel temp;
					if (rank == 1) {
						temp = new JLabel("1:st - " + score, SwingConstants.CENTER);
						temp.setFont(new Font("Serif", Font.BOLD, 32));
						temp.setForeground(new Color(218, 165, 32));
					} else if (rank == 2) {
						temp = new JLabel("2:nd - " + score, SwingConstants.CENTER);
						temp.setFont(new Font("Serif", Font.BOLD, 30));
						temp.setForeground(new Color(192, 192, 192));
					} else if (rank == 3) {
						temp = new JLabel("3:rd - " + score, SwingConstants.CENTER);
						temp.setFont(new Font("Serif", Font.BOLD, 28));
						temp.setForeground(new Color(205, 127, 50));
					} else if (rank == 21) {
						temp = new JLabel(rank + ":st - " + score, SwingConstants.CENTER);
						temp.setFont(new Font("Serif", Font.PLAIN, 27));
					} else if (rank == 22) {
						temp = new JLabel(rank + ":nd - " + score, SwingConstants.CENTER);
						temp.setFont(new Font("Serif", Font.PLAIN, 27));
					} else {
						temp = new JLabel(rank + ":th - " + score, SwingConstants.CENTER);
						temp.setFont(new Font("Serif", Font.PLAIN, 27));
					}
					panel.add(temp);
					rank++;
				}
				panel.revalidate();
				int height = (int) panel.getPreferredSize().getHeight();
				Rectangle rect = new Rectangle(0, height, 10, 10);
				panel.scrollRectToVisible(rect);
			}
		}

	}
}
