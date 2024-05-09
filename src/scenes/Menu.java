package scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

import application.GamePanel;
import application.Program;
import ui.Button;

public class Menu implements Scene{
	Button[] buttons;
	BufferedImage backGround;
	
	public Menu() {
		BufferedImage[] face = new BufferedImage[3];
		
		try {

			BufferedImage spriteSheet = ImageIO.read(new FileImageInputStream(new File("res/images/sprites.png")));
			for(int i = 0; i < face.length; i++) {
				BufferedImage sprite = new BufferedImage(33, 34, BufferedImage.TYPE_INT_ARGB);
				Graphics g = sprite.createGraphics();
				g.drawImage(spriteSheet, -i * 33, -79, null);
				g.dispose();
				face[i] = sprite;
			}
			
			
			backGround = ImageIO.read(new FileImageInputStream(new File("res/images/bg.png")));;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		buttons = new Button[3];
		Button button = new Button(29,120,100,140) {
			
			@Override
			protected void paintContent(Graphics g) {
				g.drawString("-- Fácil --", 10, 15);
				
				g.drawImage(face[0], 32, 33 ,null);
				
				g.drawString("- 16x10", 10, 95);
				g.drawString("- 60s", 10, 110);
				g.drawString("- 16 B", 10, 125);
			}
			@Override
			public void action() {
				Game.dificulty = 1;
				toGame();
			}
		};
		buttons[0] = button;
		
		button = new Button(149,120,100,140) {
			
			@Override
			protected void paintContent(Graphics g) {
				g.drawString("-- Médio --", 10, 15);
				
				g.drawImage(face[1], 32, 33 ,null);
				
				g.drawString("- 21x14", 10, 95);
				g.drawString("- 120s", 10, 110);
				g.drawString("- 40 B", 10, 125);
			}
			@Override
			public void action() {
				Game.dificulty = 2;
				toGame();
			}
		};
		
		buttons[1] = button;
		
		button = new Button(269,120,100,140) {
			
			@Override
			protected void paintContent(Graphics g) {
				g.drawString("-- Difícil --", 10, 15);
				
				g.drawImage(face[2], 32, 33 ,null);
				
				g.drawString("- 32x21", 10, 95);
				g.drawString("- 200s", 10, 110);
				g.drawString("- 99 B", 10, 125);
			}
			@Override
			public void action() {
				Game.dificulty = 3;
				toGame();
			}
		};
		
		buttons[2] = button;
	}
	public void start() {
		
	}
	
	private void toGame() {
		if(Program.showTutorial) {
			GamePanel.changeScene(2);
			return;
		}
		GamePanel.changeScene(1);
	}
	@Override
	public void update() {
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].checkClick();
		}
		Program.mouseInput.clicked = false;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(backGround,0,0,null);
		g.setColor(Color.WHITE);
		g.drawString("[Selecione a dificuldade]", 124, 310);
		
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].render(g);
		}
		
	}

}
