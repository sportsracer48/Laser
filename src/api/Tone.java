package api;

public class Tone {

	private double frequency, phase, amplitude;
	
	public Tone(double frequency, double phase, double amplitude){
		this.frequency = frequency;
		this.phase = phase;
		this.amplitude = amplitude;
	}

	public double getFrequency() {
		return frequency;
	}

	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}

	public double getPhase() {
		return phase;
	}

	public void setPhase(double phase) {
		this.phase = phase;
	}

	public double getAmplitude() {
		return amplitude;
	}

	public void setAmplitude(double amplitude) {
		this.amplitude = amplitude;
	}
	

}
