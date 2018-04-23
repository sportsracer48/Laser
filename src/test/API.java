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
		player.addTone(new Tone(440.0, 0.0, 512.0));
		player.addTone(new Tone(441.0, 0.0, 512.0));
		/*for(int i = 2; i < 5; i++){
			Thread.sleep(1000);
			player.addTone(new Tone(freq*i, 0.0, 512.0));
		}*/
		Thread.sleep(1000);
		Tone tone = player.getTones().get(1);
		double phase = 0;
		while(true){
			Thread.sleep(10);
			tone.setPhase(phase+=(Math.PI*2/100));
		}
	}
}
