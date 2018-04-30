package ui;

import static java.awt.GridBagConstraints.*;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;  

public class SliderPane extends GoodPane implements ChangeListener, DocumentListener{
	private static final long serialVersionUID = 5311089623612907207L;
	JSlider slider;
	GoodPane namePanel;
	JTextField valueField;
	double low, high;
	double val;
	
	int outOfBounds = 0;
	
	JLabel lowLabel;
	JLabel highLabel;
	
	Runnable onChange;
	
	public SliderPane(String name, double low, double high) {
		this.low = low;
		this.high = high;

		slider = new JSlider(Integer.MIN_VALUE,Integer.MAX_VALUE);
		valueField = new JTextField(10);
		
		lowLabel = new JLabel(String.format("%.2f",low));
		highLabel = new JLabel(String.format("%.2f",high));

		
		namePanel = new GoodPane(HORIZONTAL, HORIZONTAL, false);
		namePanel.setBorder(BorderFactory.createTitledBorder(name));
		
		namePanel.addW(valueField,0);
		namePanel.addW(lowLabel,0);
		namePanel.add(slider);
		namePanel.addW(highLabel, 0);
		
		slider.addChangeListener(this);
		valueField.getDocument().addDocumentListener(this);
		
		setValue(low);
		
		this.add(namePanel);
	}
	public void setOnChange(Runnable onChange) {
		this.onChange = onChange;
	}
	
	private double rescale(double val, double l0, double h0, double l1, double h1) {
		return (val-l0)/(h0-l0)*(h1-l1)+l1;
	}
	private double clamp(double val, double low, double high) {
		if(val < low) {
			return low;
		}
		if(val > high) {
			return high;
		}
		return val;
	}
	
	public void setRange(double low, double high) {
		this.low = low;
		this.high = high;
		lowLabel.setText(String.format("%.2f",low));
		highLabel.setText(String.format("%.2f",high));
		updateSlider();
	}
	
	private int toInt(double val) {
		return (int)Math.round(rescale(val,low,high,Integer.MIN_VALUE,Integer.MAX_VALUE));
	}
	private double toDouble(int val) {
		return rescale(val, Integer.MIN_VALUE, Integer.MAX_VALUE, low, high);
	}
	public void setValue(double value) {
		val = clamp(value,low,high);
		if(onChange != null) {
			onChange.run();
		}
		updateSlider();
		updateField();
	}
	
	
	private boolean almostEqual(double a, double b) {
		return toInt(a) == toInt(b);
	}
	
	private void updateSlider() {
		if(val < low) {
			slider.setValue(Integer.MIN_VALUE);
		}
		else if(val > high) {
			slider.setValue(Integer.MAX_VALUE);
		}
		else if(!almostEqual(getSliderValue(),val)) {
			slider.setValue(toInt(val));
		}
	}
	private void updateField() {
		if(!almostEqual(getFieldValue(),val)) {
			valueField.setText(String.format("%.2f", val));
		}
	}
	
	private double getSliderValue() {
		return toDouble(slider.getValue());
	}
	private double getFieldValue() {
		try {
			return Double.parseDouble(valueField.getText());
		} catch(NumberFormatException err) {
			return Double.NaN;
		}
	}
	
	private void updateFromField() {
		double n = getFieldValue();
		if(!Double.isNaN(n)) {
			if(n<low || n > high) {
				outOfBounds = 2;
			}
			val = n;
			updateSlider();
		}
		if(onChange != null) {
			onChange.run();
		}
	}
	private void updateFromSlider() {
		if(outOfBounds>0) {
			outOfBounds--;
		} else {
			val = getSliderValue();
			updateField();
		}
		if(onChange != null) {
			onChange.run();
		}
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		updateFromSlider();
	}
	@Override
	public void insertUpdate(DocumentEvent e) {
		updateFromField();
	}
	@Override
	public void removeUpdate(DocumentEvent e) {
		updateFromField();
	}
	@Override
	public void changedUpdate(DocumentEvent e) {
	}
}
