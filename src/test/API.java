package test;

import api.Player;
import api.PlayerThread;
import api.Tone;

public class API {

	public static void main(String[] args) throws InterruptedException{
		Player player = new Player();
		PlayerThread thread = new PlayerThread(player);
		thread.start();
		double freq = 440;
		player.addTone(new Tone(440.0, 0.0, 32.0));
		for(int i = 2; i < 5; i++){
			Thread.sleep(1000);
			player.addTone(new Tone(freq*i, 0.0, 32.0));
		}
	}
}
