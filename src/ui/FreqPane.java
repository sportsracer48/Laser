package ui;

import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;

import api.Tone;  

public class FreqPane extends GoodPane{
	private static final long serialVersionUID = 6859766020587306412L;
	SliderPane f;
	SliderPane phi;
	SliderPane A;
	GoodPane namePanel;
	
	Tone tone;
	
	double maxFreq = 2000;
	
	public FreqPane() {
		phi = new SliderPane("Phase", 0, 2*Math.PI);
		f = new SliderPane("Frequency",0,maxFreq);
		A = new SliderPane("Amplitude",0,4);
		
		tone = new Tone(f.val,phi.val,A.val);
		phi.onChange = ()->tone.setPhase(phi.val);
		f.onChange   = ()->tone.setFrequency(f.val);
		A.onChange   = ()->tone.setAmplitude(A.val);
				
		namePanel = new GoodPane(GridBagConstraints.VERTICAL);
		namePanel.setBorder(BorderFactory.createTitledBorder("Tone"));
		
		namePanel.add(new ControlPane(this));
		namePanel.add(phi);
		namePanel.add(f);
		namePanel.add(A);
		this.add(namePanel);
	}

	public void setRange(double percent) {
		double val = f.val;
		double oldLow = f.low;
		double oldHigh = f.high;
		double delta = oldHigh-oldLow;
		double newDelta = delta*percent;
		double low = val-newDelta/2;
		double high = val+newDelta/2;
		if(low < 0) {
			low = 0;
		}
		if(high > maxFreq) {
			high = maxFreq;
		}
		f.setRange(low, high);
	}
	
	public Tone getTone() {
		return tone;
	}
	
	public void print() {
		System.out.format("%f, %f, %f%n",f.val,phi.val,A.val);
	}
}
