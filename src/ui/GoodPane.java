package ui;

import static java.awt.GridBagConstraints.*;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

public class GoodPane extends JPanel{
	private static final long serialVersionUID = 8133018880399460861L;
	GridBagConstraints cns;
	int dir;
	
	public GoodPane() {
		this(VERTICAL, HORIZONTAL, true);
	}
	
	public GoodPane(int dir) {
		this(dir, HORIZONTAL, true);
	}
	
	public GoodPane(int dir, int fill, boolean pad) {
		this.dir = dir;
		this.setLayout(new GridBagLayout());
		cns = new GridBagConstraints();
		
		cns.anchor = NORTHWEST;
		cns.fill = fill;
		cns.weighty = 1;
		cns.weightx = 1;
		cns.gridx = 0;
		cns.gridy = 0;
		
		if(dir == VERTICAL) {
			if(pad) {
				cns.gridy = Short.MAX_VALUE;
				cns.insets = new Insets(0,0,0,0);
				JPanel test = new JPanel();
				test.setPreferredSize(new Dimension(0,0));
				this.add(test,cns);
			}
			
			cns.weighty = 0;
			cns.gridy = 0;
			cns.insets = new Insets(5,5,5,5);
		} else {
			if(pad) {
				cns.gridx = Short.MAX_VALUE;
				cns.insets = new Insets(0,0,0,0);
				JPanel test = new JPanel();
				test.setPreferredSize(new Dimension(0,0));
				this.add(test,cns);
			}
			
			cns.weightx = 0;
			cns.gridx = 0;
			cns.insets = new Insets(5,5,5,5);
		}
		
	}
	
	public Component add(Component c) {
		super.add(c,cns);
		if(dir == VERTICAL) {
			cns.gridy++;
		} else {
			cns.gridx++;
		}
		//cns.weighty = 1;
		
		return c;
	}
	
	public void addW(Component c, double weight) {
		if(dir == VERTICAL) {
			cns.weighty = weight;
		} else {
			cns.weightx = weight;
		}
		add(c);
		if(dir == VERTICAL) {
			cns.weighty = 1;
		} else {
			cns.weightx = 1;
		}
	}
}
