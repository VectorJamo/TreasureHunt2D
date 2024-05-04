package com.ruby2d.display;

import java.awt.*;
import javax.swing.*;

public class Display {
	
	private static String TITLE;
	
	private static JFrame frame;
	private static Canvas canvas;
	
	public Display(int width, int height, String title) {
		TITLE = title;
		
		frame = new JFrame(title);
		frame.setResizable(false);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		
		frame.add(canvas);
		frame.pack();
	}
	
	public static int getWidth() {
		return canvas.getWidth();
	}

	public static int getHeight() {
		return canvas.getHeight();
	}

	public static String getTitle() {
		return TITLE;
	}

	public Canvas getCanvas() {
		return canvas;
	}
	public JFrame getFrame() {
		return frame;
	}
}
