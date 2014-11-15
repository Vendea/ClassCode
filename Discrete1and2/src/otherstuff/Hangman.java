package otherstuff;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Hangman extends JFrame implements ActionListener {
	static int radius = 40;
	static JTextField guessField;
	static JPanel hangmanDrawing;
	public static void main(String[] args) {
		Hangman hangman = new Hangman();
		hangman.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		hangman.setBackground(Color.yellow);
		hangman.setLayout(new BorderLayout());
		
		hangmanDrawing = new JPanel(){
			public void paint(Graphics gg){
				Graphics2D g = (Graphics2D)gg;
				g.setColor(Color.black);
				g.drawArc(100, 70, radius, radius, 0, 360);
			}
		};
		hangmanDrawing.setBackground(Color.blue);
		hangmanDrawing.setPreferredSize(new Dimension(200, 250));
		hangman.add(hangmanDrawing, BorderLayout.EAST);
		
		guessField = new JTextField("New text");		
		hangman.add(guessField, BorderLayout.SOUTH);
		guessField.addActionListener(hangman);
		
		JTextArea usedLettersArea = new JTextArea("New blahblahblabhalbahlskdfja;lsdkfjtext");
		usedLettersArea.setBackground(Color.magenta);
		hangman.add(usedLettersArea, BorderLayout.WEST);
		
		hangman.setPreferredSize(new Dimension(500,300));
		hangman.pack();
		hangman.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		radius = Integer.valueOf(guessField.getText());
		hangmanDrawing.repaint();
	}
}
