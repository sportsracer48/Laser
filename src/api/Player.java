package api;

import java.util.ArrayList;

public class Player {

	ArrayList<Tone> tones = new ArrayList<Tone>();
	
	public Player() {
		
	}
	
	public ArrayList<Tone> getTones(){
		return tones;
	}
	
	public void addTone(Tone tone) {
		this.tones.add(tone);
	}
	
	public void removeTone(Tone tone) {
		this.tones.remove(tone);
	}
}
