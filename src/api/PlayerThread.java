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
		int bytesPerSample = (format.getSampleSizeInBits()/8);
		double secondsPerFrame = 1.0 / format.getFrameRate();
		
		int[] intBuff = new int[4];
		byte[] buff = new byte[intBuff.length*bytesPerSample];
		
		
		
		while (true) {
			Arrays.fill(intBuff, 0);
			
			for (Tone tone : tones) {
				for (int i = 0; i < intBuff.length; i ++) {
					double time = secondsPerFrame*(samples + i);
					int amp = (int) Math.round(tone.sample(time));
					intBuff[i] += amp;
				}
			}
			for(int i = 0; i < buff.length; i++){
				buff[i] = (byte) ((intBuff[i/bytesPerSample]>>((bytesPerSample - i%bytesPerSample - 1)*8))&0xFF);
			}
			samples += intBuff.length;
			speaker.write(buff, 0, buff.length);
		}
	}
}
