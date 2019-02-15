//package game;
//
//import java.awt.*;
//import java.awt.event.*;
//import javax.swing.*;
//
//import java.util.Observable;
//import java.util.Random;
//
//
//public class GUI extends Observable  {
//	private JFrame frame;
//	private Container contentPane;
//
//	public GUI() {
//		
//		
//		makefram();
//	}
//
//	private void makefram() {
//		frame = new JFrame("FLAPPY BIRDS");
//		//frame.setFocusable( true );
//		contentPane = frame.getContentPane();
//		//skapa paneler
//		makePanels(contentPane);
//		//lägg  till knapp avlyssnare i JFramen
//		//frame.addKeyListener(this);
//		frame.pack();
//		//storlek på jFRame
//		//frame.setSize(1280,850);
//		frame.setVisible(true);
//		
//		
//	}
//
//	//skapar knapparna till start menyn och 
//	public void makePanels(Container contentPane) {
//		contentPane.setLayout(new GridLayout(3,2,3,3));
//		JButton start = new JButton("START");
//		//procedur när start trycks
//		start.addActionListener(
//				(ActionEvent e) -> {start(e); }
//				);
//		contentPane.add(start);
//
//		JButton HS = new JButton("HIGH SCORE");
//		HS.addActionListener(
//				(ActionEvent e) -> {HS(); }
//				);
//		contentPane.add(HS);
//	}
//
//	//funktion för att trigga igång spelet
//	private void start(ActionEvent e) {
//		//System.out.println("testar START \n");
//		//rensar bort start meny
//		contentPane.removeAll();
//	    
//		 frame.setVisible(false);
//		 frame.setEnabled(false);
//		 frame.dispose();
//
//		 setChanged();
//		 notifyObservers(1);
//		 
//		
//		
//		
//		//contentPane.repaint();
//	}
//
//	//funktion för att trigga igång spelet
//	private void HS() {
//		
//		contentPane.removeAll();
//	    
//		 frame.setVisible(false);
//		 frame.setEnabled(false);
//		 frame.dispose();
//
//		
//	System.out.println("testar HS");
//	//problem verkar vara med en thread som blockar att man kan skriva 
//	}
//
//
//}
