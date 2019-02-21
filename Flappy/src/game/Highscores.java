package game;

import java.awt.*;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import javax.swing.*;

public class Highscores extends JFrame implements Runnable {
	
	JLabel label;
	InetAddress addr;
	DatagramSocket socket;
	DatagramPacket packet;
	
	public Highscores() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//ArrayList <String> highScores = getHighScores();
		//String highScore = getHighScores();
		setLayout(new GridLayout(1, 1));
		label = new JLabel("");
		//setLayout(new GridLayout(1, highScores.size()));
		//textField = new JTextArea(highScore);
		add(label);
		pack();
		setVisible(true);
		
		/*
		try {
		addr = InetAddress.getLocalHost();
		socket = new DatagramSocket(6789);
		packet = new DatagramPacket(new byte[1024], 1024);
		}
		catch(IOException e) {
			e.printStackTrace();
			return;
		}*/
		
	}
	
	private String getHighScores(){
		
		try {
			//InetAddress addr = InetAddress.getLocalHost();
			//DatagramSocket socket = new DatagramSocket(6788);
			//DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
			addr = InetAddress.getLocalHost();
			socket = new DatagramSocket(6791);
			packet = new DatagramPacket(new byte[1024], 1024);
			socket.setSoTimeout(5000);
			socket.receive(packet);
			socket.close();
			return new String(packet.getData(), 0, packet.getLength());
			
		}
		catch(java.net.SocketTimeoutException e) {
			socket.close();
			return "";
			
		}
		catch(IOException e) {
			e.printStackTrace();
			return "";
			
		}
	}
	
	
	@Override
	public void run() {
		while(true) {
		String highScore = getHighScores();
		
		if(highScore != null && !highScore.isEmpty()) {
		
		label.setText(highScore);
		}
		}

	}
}
