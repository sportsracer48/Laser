package api;

import java.util.ArrayList;
import java.util.Arrays;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class PlayerThread extends Thread {

	private SourceDataLine speaker;
	private AudioFormat format = new AudioFormat(96000, 16, 1, true, true);
	private ArrayList<Tone> tones;
	private long samples = 0;

	public PlayerThread(Player player) {
		this.tones = player.getTones();
		try {
			speaker = (SourceDataLine) AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, format));
			speaker.open(format);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void start() {
		speaker.start();
		super.start();
	}

	@Override
	public void run() {
		int[] intBuff = new int[128];
		int multiplier = (format.getSampleSizeInBits()/8);
		byte[] buff = new byte[intBuff.length*multiplier];
		double frameDuration = 1.0 / format.getFrameRate();
		while (true) {
			Arrays.fill(intBuff, 0);
			for (Tone tone : tones) {
				for (int i = 0; i < intBuff.length; i ++) {
					double time = frameDuration*(samples + i);
					int amp = (int) (Math.sin(time*tone.getFrequency()*Math.PI*2+tone.getPhase())*tone.getAmplitude());
					intBuff[i] += amp;
				}
			}
			for(int i = 0; i < buff.length; i++){
				buff[i] = (byte) ((intBuff[i/multiplier]>>((multiplier - i%multiplier - 1)*8))&0xFF);
			}
			samples += intBuff.length;
			speaker.write(buff, 0, buff.length);
		}
	}
}
