package scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

import application.GamePanel;
import application.Program;
import game_objects.Tile;
import ui.Button;

public class Game implements Scene{
	Button faceButton;
	Button[] buttons;
	BufferedImage[] face;
	public static Tile[][] tiles;
	public static int flags = 0;
	public static int dificulty = 2;
	public static int maxSeconds = 999;
	public static int seconds = 999;
	public static boolean started = false;
	static boolean loaded = false;
	Color backGroundColor = new Color(21,76,24);
	Color redish = new Color(255,0,0,170);
	
	public static int state = 0;
	
	public static final int FINE = 0;
	public static final int HURRY = 1;
	public static final int DEAD = 2;
	public static final int VICTORY = 3;
	
	public Game() {
		face = new BufferedImage[4];
		File file = new File("res/images/sprites.png");
		try {
			BufferedImage spriteSheet = ImageIO.read(new FileImageInputStream(file));
			for(int i = 0; i < face.length; i++) {
				BufferedImage sprite = new BufferedImage(35, 35, BufferedImage.TYPE_INT_ARGB);
				Graphics g = sprite.createGraphics();
				g.drawImage(spriteSheet, -i * 35, 0, null);
				g.dispose();
				face[i] = sprite;
			}
			
			BufferedImage sprite = new BufferedImage(12, 12, BufferedImage.TYPE_INT_ARGB);
			Graphics g = sprite.createGraphics();
			g.drawImage(spriteSheet, 0, -35, null);
			
			Tile.bombImage = sprite;
			
			sprite = new BufferedImage(12, 12, BufferedImage.TYPE_INT_ARGB);
			g = sprite.createGraphics();
			g.drawImage(spriteSheet, -12, -35, null);
			g.dispose();
			Tile.flagImage = sprite;
			
			sprite = new BufferedImage(12, 12, BufferedImage.TYPE_INT_ARGB);
			g = sprite.createGraphics();
			g.drawImage(spriteSheet, -49, -35, null);
			g.dispose();
			Tile.flowerImage = sprite;
			
			g.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		faceButton = new Button(180,5,40,40) {
			
			@Override
			protected void paintContent(Graphics g) {
				
			}
			
			@Override
			public void action() {
				setState(DEAD);
			}
		};
		buttons = new Button[2];
		buttons[0] = new Button(20,9,140,30) {
			
			@Override
			protected void paintContent(Graphics g) {
				g.setColor(Color.WHITE);
				g.drawString("Jogar novamente", 10, 18);
			}
			
			@Override
			public void action() {
				start();
			}
		};
		
		buttons[1] = new Button(240,9,140,30) {
			
			@Override
			protected void paintContent(Graphics g) {
				g.setColor(Color.WHITE);
				g.drawString("Voltar para o menu", 10, 18);
			}
			
			@Override
			public void action() {
				GamePanel.changeScene(0);
			}
		};
		
	}
	
	public void start() {
		setState(FINE);
		loadTiles();
		loaded = false;
		seconds = maxSeconds;
		started = false;
	}
	
	private void loadTiles() {
		int tileSize = 0;
		switch(dificulty){
			case 1:tiles = new Tile[16][10];
				flags = 16;
				maxSeconds = 60;
				tileSize = 18;
			break;
			
			case 2:tiles = new Tile[21][14];
				flags = 40;
				maxSeconds = 120;
				tileSize = 14;
			break;
		
			case 3:tiles = new Tile[32][21];
				flags = 99;
				maxSeconds = 200;
				tileSize = 12;
			break;
		}
		
		int offSetX = (400 - tiles.length*tileSize) / 2;
		int offSetY = (272 - tiles[0].length*tileSize) / 2 + 50;
		
		for(int i = 0; i <tiles.length; i++) {
			for(int j = 0; j < tiles[0].length; j++) {
				Tile tile = new Tile((i + j) % 2 != 0);
				tile.setSize(tileSize);
				tile.setMapPosition(i, j);
				tile.position.x = tile.width * i + offSetX;
				tile.position.y = tile.height * j + offSetY;
				tiles[i][j] = tile;
			}
		}
		
		
		
	}
	
	static void loadBombs(int tx, int ty) {
		if(loaded) {
			return;
		}
		int bombs = 0;
		Random rand = new Random();
		
		while(bombs < flags) {
			int x = rand.nextInt(tiles.length);
			int y = rand.nextInt(tiles[0].length);
			if(x != tx && y != ty && !tiles[x][y].isBomb()) {
				tiles[x][y].setBomb(true);
				bombs++;
			}
		}
		
		
		
		for(int i = 0; i <tiles.length; i++) {
			for(int j = 0; j < tiles[0].length; j++) {
				tiles[i][j].checkNumber(tiles);;
			}
		}
		
		loaded = true;
	}
	static void revealBombs() {
		for(int i = 0; i <tiles.length; i++) {
			for(int j = 0; j < tiles[0].length; j++) {
				if(tiles[i][j].isBomb()) {
					tiles[i][j].setDigged(true);
				}
			}
		}
	}
	
	public static boolean checkFlags() {
		
		for(int i = 0; i <tiles.length; i++) {
			for(int j = 0; j < tiles[0].length; j++) {
				if(tiles[i][j].isBomb() && !tiles[i][j].isFlagged()) {
					return false;
				}
			}
		}
		
		return true;
	}
	public static void checkDigged() {
		
		for(int i = 0; i <tiles.length; i++) {
			for(int j = 0; j < tiles[0].length; j++) {
				if(!tiles[i][j].isBomb() && !tiles[i][j].isDigged()) {
					return;
				}
			}
		}
		
		setState(VICTORY);
	}
	
	public static void setState(int state) {
		Game.state = state;
		if(state == Game.DEAD) {
			revealBombs();
			return;
		}
	}
	
	public static void passSecond() {
		
		if(!started) {
			return;
		}
		
		seconds--;
		if(state == VICTORY) {
			return;
		}
		if(seconds <= 0) {
			if(flags == 0) {
				if(checkFlags()) {
					setState(Game.VICTORY);
					return;
				}
			}
			setState(DEAD);
			return;
		}
		if(seconds <= 20) {
			setState(HURRY);
		}
	}
	
	public static void addTime(int time) {
		if(!started) {
			started = true;
			return;
		}
		
		seconds += time;
		if(seconds > 20) {
			setState(FINE);
		}
		if(seconds > maxSeconds) {
			seconds = maxSeconds;
		}
		checkDigged();
	}
	@Override
	public void update() {
		
		if(state != DEAD && state != VICTORY) {
			for(int i = 0; i <tiles.length; i++) {
				for(int j = 0; j < tiles[0].length; j++) {
					tiles[i][j].update();
				}
			}
			faceButton.checkClick();
		}else {
			for(int i = 0; i < buttons.length; i++) {
				buttons[i].checkClick();
			}
			
		}
		
		Program.mouseInput.clicked = false;
		Program.mouseInput.rClicked = false;
	}
	
	public static void dig(int x, int y) {
		
		if(x < tiles.length - 1 && !tiles[x + 1][y].isBomb()) {
			Tile tile = tiles[x+1][y];
			if(tile.number == 0) {
				tile.dig();
				addTime(5);
				return;
			}
		}
		if(x > 0 && !tiles[x - 1][y].isBomb()) {
			Tile tile = tiles[x-1][y];
			if(tile.number == 0) {
				tile.dig();
				addTime(5);
				return;
			}
		}
		
		if(y < tiles[0].length - 1 && !tiles[x][y + 1].isBomb()) {
			Tile tile = tiles[x][y + 1];
			if(tile.number == 0) {
				tile.dig();
				addTime(5);
				return;
			}
		}
		if(y > 0 && !tiles[x][y - 1].isBomb()) {
			Tile tile = tiles[x][y - 1];
			if(tile.number == 0) {
				tile.dig();
				addTime(5);
				return;
			}
		}
		
		if(!started) {
			loadBombs(x, y);
			//addTime(0);
			
			if(tiles[x][y].number == 0) {
				dig();
				return;
			}
			
			if(x < tiles.length - 1 && !tiles[x + 1][y].isBomb()) {
				Tile tile = tiles[x+1][y];
				if(tile.number != 0) {
					tile.dig();
				}
			}
			if(x > 0 && !tiles[x - 1][y].isBomb()) {
				Tile tile = tiles[x-1][y];
				if(tile.number != 0) {
					tile.dig();
				}
			}
			
			if(y < tiles[0].length - 1 && !tiles[x][y + 1].isBomb()) {
				Tile tile = tiles[x][y + 1];
				if(tile.number != 0) {
					tile.dig();
				}
			}
			if(y > 0 && !tiles[x][y - 1].isBomb()) {
				Tile tile = tiles[x][y - 1];
				if(tile.number != 0) {
					tile.dig();
				}
			}
		}
		addTime(1);
	}
	
	
	public static void dig() {
		for(int i = 0; i < tiles.length; i++) {
			for(int j = 0; j < tiles[0].length; j++) {
				
				if(tiles[i][j].isDigged() && tiles[i][j].number == 0) {
					
					if(i < tiles.length - 1 && !tiles[i + 1][j].isDigged()) {
						Tile tile = tiles[i+1][j];
						tile.dig();
					}
					if(i > 0 && !tiles[i - 1][j].isDigged()) {
						Tile tile = tiles[i -1][j];
						tile.dig();
					}
					
					if(j < tiles[0].length - 1 && !tiles[i][j + 1].isDigged()) {
						Tile tile = tiles[i][j + 1];
						tile.dig();
					}
					if(j > 0 && !tiles[i][j - 1].isDigged()) {
						Tile tile = tiles[i][j - 1];
						tile.dig();
					}
					
					if(i > 0) {
						if(j > 0 && !tiles[i - 1][j - 1].isDigged()) {
							Tile tile = tiles[i - 1][j - 1];
							tile.dig();
						}
						if(j < tiles[0].length - 1 && !tiles[i - 1][j + 1].isDigged()) {
							Tile tile = tiles[i - 1][j + 1];
							tile.dig();
						}
					}
					
					if(i < tiles.length - 1) {
						if(j > 0 && !tiles[i + 1][j - 1].isDigged()) {
							Tile tile = tiles[i+1][j - 1];
							tile.dig();
						}
						if(j < tiles[0].length - 1 && !tiles[i + 1][j + 1].isDigged()) {
							Tile tile = tiles[i+1][j + 1];
							tile.dig();
						}
					}
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(backGroundColor);
		g.fillRect(0, 0, 400, 320);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 400, 50);
		
		
		for(int i = 0; i <tiles.length; i++) {
			for(int j = 0; j < tiles[0].length; j++) {
				tiles[i][j].render(g);
			}
		}
		if(state == DEAD) {
			g.setColor(redish);
			g.fillRect(0, 50, 400, 270);
			
		}else if(state != VICTORY){
			g.setColor(Color.WHITE);
			g.drawString("Bandeiras: "+ flags, 20, 30);
			if(state == HURRY) {
				g.setColor(Color.RED);
			}
			g.drawString("Tempo: "+String.format("%03d", seconds), 305, 30);
			faceButton.render(g);
			g.drawImage(face[state], 183,7,null);
			return;
		}
		
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].render(g);
		}
		
		g.drawImage(face[state], 183,7,null);
		
	}

}
