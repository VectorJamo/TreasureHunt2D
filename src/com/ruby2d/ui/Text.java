package com.ruby2d.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Text {
	protected float x, y;
	protected int width, height;
	
	protected String text;
	protected Color color;
	protected Font font;

	public Text(String text, float x, float y, Font font){
		this.font = font;

		this.x = x;
		this.y = y + font.getSize();
		this.width = 0; // width and height of the displayed font will be determined later.
		this.height = 0;

		this.text = text;
		color = Color.WHITE;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public void setColor(Color c) {
		color = c;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setFont(Font f) {
		font = f;
	}
	
	public Font getFont() {
		return font;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y - font.getSize();
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void render(Graphics2D g2d) {
		width = g2d.getFontMetrics(font).stringWidth(text);
		height = g2d.getFontMetrics(font).getHeight();
		
		g2d.setColor(color);
		g2d.setFont(font);
		g2d.drawString(text, (int)x, (int)y);
	}
}
