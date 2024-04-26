package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import application.GamePanel;
import application.Program;
import game_objects.Position;


public abstract class Button {
	
	Position position = new Position();
	int width, height;
	boolean selected;
	boolean active;
	public BufferedImage buttonImage;
	private boolean focusable;
	private boolean focused;
	private boolean borderless;
	private Color color = Color.WHITE;
	private Color backgroundColor = Color.BLACK;
	private Color selectedColor = Color.DARK_GRAY;
	private Color unfocusColor = Color.GRAY;
	
	public Button(float x, float y) {
		setPosition(x, y);
	}
	public Button(float x, float y, int widht, int height) {
		setPosition(x, y);
		setSize(widht, height);
	}
	public Button() {
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	public void setUnfocusedColor(Color color) {
		unfocusColor = color;
	}
	public void setBackgroundColor(Color color) {
		backgroundColor = color;
	}
	public void setSelectedColor(Color color) {
		selectedColor = color;
	}
	public Color getColor() {
		return color;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean booleanValue) {
		active = booleanValue;
	}
	
	public void setFocusable(boolean booleanValue) {
		focusable = booleanValue;
	}
	
	public void setFocused(boolean booleanValue) {
		focused = booleanValue;
	}
	
	public void setBorderless(boolean booleanValue) {
		borderless = booleanValue;
	}
	
	public boolean isFocusable() {
		return focusable;
	}
	public boolean isFocused() {
		return focused;
	}
	
	public void setPosition(float x, float y) {
		position = new Position(x, y);
	}
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		buttonImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		setContent();
		
	}
	
	public void refactor() {
		setSize(buttonImage.getWidth(), buttonImage.getHeight());
	}
	
	
	
	protected void setContent() {
		Graphics2D g2 = buttonImage.createGraphics();
		g2.setFont(GamePanel.pixelFont);
		paintContent(g2);
		g2.dispose();
	}
	
	public void update() {
		
	}
	public void checkClick() {
		if(mouseOn()) {
			selected = true;
			if(Program.mouseInput.clicked) {
				action();
				Program.mouseInput.clicked = false;
			}
		}
		else {
			selected = false;
		}
		
	}
	public abstract void action();
	protected void altAction() {
		
	}
	
	private boolean mouseOn() {
		if(!(position.x < Program.mouseX && position.x + width > Program.mouseX)){
			return false;
		}
		if(!(position.y < Program.mouseY && position.y + height > Program.mouseY)){
			return false;
		}
		
		return true;
		
	}
	protected abstract void paintContent(Graphics g);
	
	public boolean isSelected() {
		return selected;
	}
		
	public void render(Graphics g) {
		
		if(selected || (focusable && focused)) {
			g.setColor(selectedColor);
		}
		else {
			g.setColor(backgroundColor);
		}
		
		g.fillRect((int)position.x, (int)position.y, width, height);
		
		
		g.drawImage(buttonImage,(int)position.x ,  (int)position.y, null);
		if(focusable && !focused) {
			g.setColor(unfocusColor);
		}
		else {
			g.setColor(color);
		}
		if(!borderless) {
			g.drawRect((int)position.x, (int)position.y, width, height);
		}
		
		
	}
}
