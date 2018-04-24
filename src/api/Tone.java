package api;

public class Tone {

	private double frequency, phase, amplitude;
	
	private double time = 0;
	public Tone(double frequency, double phase, double amplitude){
		this.frequency = frequency;
		this.phase = phase;
		this.amplitude = amplitude;
	}
	
	public double sample(double t) {
		time = t;
		double res = Math.sin(frequency*t*2*Math.PI + phase)*Math.pow(10, amplitude);
		return res;
	}

	public double getFrequency() {
		return frequency;
	}

	public void setFrequency(double frequency) {
		double curr = (time*this.frequency*2*Math.PI + phase)%(2*Math.PI);
		double next = (time*frequency*2*Math.PI)%(2*Math.PI);
		this.frequency = frequency;
		this.phase = curr-next;
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
