package scenes;

import java.awt.Color;
import java.awt.Graphics;

import application.GamePanel;
import ui.Button;

public class Game implements Scene{
	Button button;
	
	public Game() {
		button = new Button(20,250,40,20) {
			
			@Override
			protected void paintContent(Graphics g) {
				
			}
			
			@Override
			public void action() {
				GamePanel.changeScene(0);
			}
		};
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		button.checkClick();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString("Game :", 20, 40);
		button.render(g);
		
	}

}
