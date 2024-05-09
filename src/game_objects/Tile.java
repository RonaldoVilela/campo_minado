package game_objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import application.Program;
import scenes.Game;

public class Tile {
	public Position position = new Position();
	boolean bomb = false;
	boolean selected = false;
	boolean digged = false;
	public int number = 1;
	public int width, height;
	boolean flagged = false;
	Color defaultColor, diggedColor;
	Color numberColor = Color.BLACK;
	int mapX = 0, mapY = 0;
	int offSet = 0;
	public static BufferedImage flagImage;
	public static BufferedImage bombImage;
	public static BufferedImage flowerImage;
	
	public Tile(boolean isDarker) {
		if(isDarker) {
			defaultColor = new Color(110, 197, 57);
			diggedColor = new Color(213, 179, 134);
		}else {
			defaultColor = new Color(138, 222, 73);
			diggedColor = new Color(247, 202, 145);
		}
		
		setSize(12);
	}
	
	public void setMapPosition(int x, int y) {
		mapX = x;
		mapY = y;
	}
	
	public void setBomb(boolean isBomb) {
		bomb = isBomb;
	}
	
	public void setSize(int size) {
		width = size;
		height = size;
		offSet = (size - 12)/2;
	}
	
	public boolean isBomb() {
		return bomb;
	}
	
	public boolean isDigged() {
		return digged;
	}
	public void setDigged(boolean value) {
		digged = value;
	}
	
	public boolean isFlagged() {
		return flagged;
	}
	
	public void checkNumber(Tile[][] tiles) {
		number = 0;
		if(bomb) {
			return;
		}
		if(mapX < tiles.length - 1 && tiles[mapX + 1][mapY].isBomb()) {
			number++;
		}
		if(mapX > 0 && tiles[mapX - 1][mapY].isBomb()) {
			number++;
		}
		
		if(mapY < tiles[0].length - 1 && tiles[mapX][mapY + 1].isBomb()) {
			number++;
		}
		if(mapY > 0 && tiles[mapX][mapY - 1].isBomb()) {
			number++;
		}
		
		if(mapX > 0) {
			if(mapY > 0 && tiles[mapX - 1][mapY - 1].isBomb()) {
				number ++;
			}
			if(mapY < tiles[0].length - 1 && tiles[mapX - 1][mapY + 1].isBomb()) {
				number++;
			}
		}
		
		if(mapX < tiles.length - 1) {
			if(mapY > 0 && tiles[mapX + 1][mapY - 1].isBomb()) {
				number ++;
			}
			if(mapY < tiles[0].length - 1 && tiles[mapX + 1][mapY + 1].isBomb()) {
				number++;
			}
		}
		
		switch(number) {
			case 1: numberColor = new Color(63, 98, 183);
			break;
			case 2: numberColor = new Color(51, 110, 66);
			break;
			case 3: numberColor = new Color(174, 61, 47);
			break;
			case 4: numberColor = new Color(80, 38, 127);
			break;
			case 5: numberColor = new Color(213, 96, 16);
			break;
			case 6: numberColor = new Color(69, 152, 130);
			break;
			case 7: numberColor = new Color(180, 69, 155);
			break;
			case 8: numberColor = new Color(104, 104, 104);
			break;
		}
	}
	
	public void update() {
		if(digged) {
			return;
		}
		
		if(mouseOn()) {
			selected = true;
			
			if(Program.mouseInput.clicked) {
				dig();
				Program.mouseInput.clicked = false;
				return;
			}
			
			if(Program.mouseInput.rClicked) {
				
				if(flagged) {
					flagged = false;
					Game.flags ++;
					Program.mouseInput.rClicked = false;
					return;
				}
				if(Game.flags > 0) {
					flagged = true;
					Game.flags --;
					
				}
				
				Program.mouseInput.rClicked = false;
			}
			return;
		}
		selected = false;
		
	}
	
	
	public void dig() {
		if(flagged) {
			return;
		}
		if(digged) {
			return;
		}
		digged = true;
		if(bomb) {
			Game.setState(Game.DEAD);
			return;
		}
		
		if(number == 0 && !bomb) {
			Game.dig();
			return;
		}
		Game.dig(mapX, mapY);
		
	}
	private boolean mouseOn() {
		if(!(position.x <= Program.mouseX && position.x + width > Program.mouseX)){
			return false;
		}
		if(!(position.y <= Program.mouseY && position.y + height > Program.mouseY)){
			return false;
		}
		
		return true;
		
	}
	
	public void render(Graphics g) {
		if(digged) {
			g.setColor(diggedColor);
			
		}else {
			if(selected && Game.state != Game.DEAD) {
				g.setColor(Color.WHITE);
				
			}else {
				g.setColor(defaultColor);
				
				if(Game.state == Game.VICTORY) {
					g.fillRect(position.x, position.y, width, height);
					g.setColor(numberColor);
					if(!bomb && number != 0) {
						g.drawString(""+number, position.x + 3 + offSet, position.y + 11 + offSet);
					}
					g.drawImage(flowerImage, position.x + offSet, position.y + offSet, null);
					return;
				}
			}
			
			
		}
		
		g.fillRect(position.x, position.y, width, height);
		
		if(!digged) {
			if(flagged) {
				g.drawImage(flagImage, position.x + 1 + offSet, position.y + offSet, null);
			}
			return;
			
		}
		if(bomb) {
			g.drawImage(bombImage, position.x + offSet, position.y + offSet, null);
		}
		
		g.setColor(numberColor);
		if(!bomb && number != 0) {
			g.drawString(""+number, position.x + 3 + offSet, position.y + 11 + offSet);
		}
		
	}
}
