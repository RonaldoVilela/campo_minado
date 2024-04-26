package scenes;

import java.awt.Color;
import java.awt.Graphics;

import application.GamePanel;
import application.Program;
import ui.Button;

public class Menu implements Scene{
	Button button;
	
	public Menu() {
		button = new Button(20,100,100,100) {
			
			@Override
			protected void paintContent(Graphics g) {
				
			}
			
			@Override
			public void action() {
				GamePanel.changeScene(1);
			}
		};
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		button.checkClick();
		Program.mouseInput.clicked = false;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString("CAMPO MINADO", 50, 40);
		
		button.render(g);
		
		//g.drawRect(Program.mouseX - 2, Program.mouseY - 2, 4, 4);
		
	}

}
