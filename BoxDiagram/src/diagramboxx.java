import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;

import acm.graphics.*;
import acm.program.*;
import javax.swing.*;

public class diagramboxx extends GraphicsProgram {
	JLabel label;
	JTextField jt;
	JButton button;
	JButton button1;
	JButton button2;
	HashMap<String, GObject> boxes;
	final int BOX_WIDTH = 120;
	final int BOX_HEIGHT = 50;
	GObject currentObject;
	GPoint last;

	public void init() {

		label = new JLabel("NAME");
		add(label, SOUTH);

		jt = new JTextField(30);
		jt.addActionListener(this);
		add(jt, SOUTH);
		// jt.addActionListener();

		button = new JButton("ADD");
		add(button, SOUTH);
		button1 = new JButton("REMOVE");
		add(button1, SOUTH);
		button2 = new JButton("CLEAR");
		add(button2, SOUTH);
		boxes = new HashMap<String, GObject>();
		addActionListeners();
		addMouseListeners();
	}

	@SuppressWarnings("null")
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button) {
			String boxName = jt.getText();
			GCompound abox = getBox(boxName);
			add(abox, getWidth() / 2, getHeight() / 2);
		}
		if (e.getSource() == button1) {
			String boxName = jt.getText();
			if (boxes.containsKey(boxName)) {
				GObject theBox = boxes.get(boxName);
				remove(theBox);
				boxes.remove(boxName);
			}

		}
		if (e.getSource() == button2) {
			removeContents();
		}
	}

	private void removeBox(String name) {
		GObject obj = boxes.get(name);
		if (obj != null) {
			remove(obj);

		}

	}

	public void mousePressed(MouseEvent e) {
		last = new GPoint(e.getPoint());
		currentObject = getElementAt(last);
	}

	public void mouseDragged(MouseEvent e) {
		if (currentObject != null) {
			currentObject.move(e.getX() - last.getX(), e.getY() - last.getY());
			last = new GPoint(e.getPoint());
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (currentObject != null)
			currentObject.sendToFront();
	}

	private void removeContents() {
		Iterator<String> iterator = boxes.keySet().iterator();
		while (iterator.hasNext()) {
			removeBox(iterator.next());
		}
		boxes.clear();
	}

	public GCompound getBox(String boxName) {
		// create GCompound object
		GCompound box = new GCompound();
		// create a GRect object to act as the box outline.
		GRect outline = new GRect(BOX_WIDTH, BOX_HEIGHT);
		// create a GLabel with nameOfBox as its content
		GLabel label = new GLabel(boxName);
		// add the GRect object to the GCompound object and center it within the
		// GCompound object.
		box.add(outline, -BOX_WIDTH / 2, -BOX_HEIGHT / 2);

		box.add(label, -label.getWidth() / 2, label.getAscent() / 2);
		System.out.println(label.getWidth());
		System.out.println(label.getAscent());

		// add the String key and it's pair, the box object to the hashmap.
		boxes.put(boxName, box);

		return box;
	}

}
