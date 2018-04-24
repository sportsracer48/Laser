package ui;

import java.awt.Container;

import javax.swing.JFrame;

import api.Player;
import api.PlayerThread;

public class UI extends JFrame{
	private static final long serialVersionUID = -3378509490747603653L;
	static Player player;
	public UI() {
		super("Tone generator");
		this.setContentPane(new GoodPane());
		
		Container contentPane = this.getContentPane();
		FreqPane test = new FreqPane();
		FreqPane test2 = new FreqPane();
		contentPane.add(test);
		contentPane.add(test2);
		player.addTone(test.getTone());
		player.addTone(test2.getTone());

		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
	public static void main(String[] args) {
		player = new Player();
		PlayerThread thread = new PlayerThread(player);
		thread.start();
		
		new UI();
	}
	
	
}
