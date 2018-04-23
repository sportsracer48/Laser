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
	private AudioFormat format = new AudioFormat(48000, 16, 1, true, true);
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
		byte[] buff = new byte[128];
		short[] shortBuff = new short[64];
		double frameDuration = 1.0 / format.getFrameRate();
		System.out.println(format.getSampleSizeInBits());
		while (true) {
			Arrays.fill(shortBuff, (short)0);
			for (Tone tone : tones) {
				for (int i = 0; i < shortBuff.length; i ++) {
					double time = frameDuration*(samples + i);
					short amp = (short) (Math.sin(time*tone.getFrequency()*Math.PI*2+tone.getPhase())*tone.getAmplitude());
					shortBuff[i] += amp;
				}
			}
			for(int i = 0; i < buff.length; i+=2){
				buff[i] = (byte) (shortBuff[i/2]>>8);
				buff[i+1] = (byte) (shortBuff[i/2] & 0xFF);
			}
			samples += shortBuff.length;
			speaker.write(buff, 0, buff.length);
		}
	}
}
