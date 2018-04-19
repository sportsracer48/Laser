package test;

import api.Player;
import api.PlayerThread;
import api.Tone;

public class API {

	public static void main(String[] args){
		Player player = new Player();
		PlayerThread thread = new PlayerThread(player);
		thread.start();
		player.addTone(new Tone(440.0, 0.0, 128.0));
	}
}
