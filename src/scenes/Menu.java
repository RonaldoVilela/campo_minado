package scenes;

import java.awt.Color;
import java.awt.Graphics;

import application.GamePanel;
import application.Program;
import ui.Button;

public class Menu implements Scene{
	Button[] buttons;
	
	public Menu() {
		buttons = new Button[3];
		Button button = new Button(25,80,100,140) {
			
			@Override
			protected void paintContent(Graphics g) {
				g.drawString("-- Fácil --", 10, 15);
				
				g.drawString("- 16x10", 10, 95);
				g.drawString("- 60s", 10, 110);
				g.drawString("- 16 B", 10, 125);
			}
			@Override
			public void action() {
				Game.dificulty = 1;
				GamePanel.changeScene(1);
			}
		};
		buttons[0] = button;
		
		button = new Button(145,80,100,140) {
			
			@Override
			protected void paintContent(Graphics g) {
				g.drawString("-- Médio --", 10, 15);
				
				
				g.drawString("- 21x14", 10, 95);
				g.drawString("- 120s", 10, 110);
				g.drawString("- 40 B", 10, 125);
			}
			@Override
			public void action() {
				Game.dificulty = 2;
				GamePanel.changeScene(1);
			}
		};
		
		buttons[1] = button;
		
		button = new Button(265,80,100,140) {
			
			@Override
			protected void paintContent(Graphics g) {
				g.drawString("-- Difícil --", 10, 15);
				
				g.drawString("- 32x21", 10, 95);
				g.drawString("- 200s", 10, 110);
				g.drawString("- 99 B", 10, 125);
			}
			@Override
			public void action() {
				Game.dificulty = 3;
				GamePanel.changeScene(1);
			}
		};
		
		buttons[2] = button;
	}
	public void start() {
		
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].checkClick();
		}
		Program.mouseInput.clicked = false;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, 400, 320);
		g.setColor(Color.WHITE);
		g.drawString("CAMPO MINADO", 50, 40);
		
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].render(g);
		}
		
		//g.drawRect(Program.mouseX - 2, Program.mouseY - 2, 4, 4);
		
	}

}
