package ui;

import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.NONE;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ControlPane extends GoodPane implements ActionListener{
	private static final long serialVersionUID = 6810811798391308444L;
	
	JButton play = new JButton(">");
	JButton zoomIn = new JButton("+");
	JButton zoomOut = new JButton("-");
	//JButton delete = new JButton("x");
	JButton write  = new JButton("Save");
	FreqPane parent;
	public ControlPane(FreqPane parent) {
		super(HORIZONTAL, NONE, true);
		play.addActionListener(this);
		write.addActionListener(this);
		//delete.addActionListener(this);
		zoomIn.addActionListener(this);
		zoomOut.addActionListener(this);
		this.parent = parent;
		add(play);
		add(zoomIn);
		add(zoomOut);
		add(write);
		//add(delete);
	}
	
	private void togglePlay() {
		if(play.getText() == ">") {
			play.setText("||");
		} else {
			play.setText(">");
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == play) {
			togglePlay();
		}
		else if(e.getSource() == zoomIn) {
			parent.setRange(.1);
		}
		else if(e.getSource() == zoomOut) {
			parent.setRange(10);
		}
		else if(e.getSource() == write) {
			parent.print();
		}
	}
}
