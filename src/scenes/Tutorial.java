package scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import application.GamePanel;
import application.Program;
import ui.Button;

public class Tutorial implements Scene{
	final Color bgColor = new Color(80,100,170);
	Button next, mark;
	BufferedImage[] tutoImages;
	public Tutorial() {
		next = new Button(312,282,80,30) {
			
			@Override
			protected void paintContent(Graphics g) {
				g.drawString("Jogar >>", 10, 15);
			}
			@Override
			public void action() {
				GamePanel.changeScene(1);
			}
		};
		mark = new Button(15,289,15,15) {
			
			@Override
			protected void paintContent(Graphics g) {
			}
			@Override
			public void action() {
				Program.showTutorial = (Program.showTutorial) ? false : true;
			}
		};
		tutoImages = new BufferedImage[3];
		try {
			BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/images/sprites.png"));
			for(int i = 0; i < tutoImages.length; i++) {
				BufferedImage sprite = new BufferedImage(35, 32, BufferedImage.TYPE_INT_ARGB);
				Graphics g = sprite.createGraphics();
				g.drawImage(spriteSheet, -i * 34, -47, null);
				g.dispose();
				tutoImages[i] = sprite;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void start() {
		
	}

	@Override
	public void update() {
		next.checkClick();
		mark.checkClick();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(bgColor);
		g.fillRect(0, 0, 400, 320);
		
		g.setColor(Color.WHITE);
		g.drawString("--- COMO JOGAR? ---", 20, 20);
		
		g.drawImage(tutoImages[0], 20, 60, null);
		g.drawString("Botão esquerdo = Cavar", 100, 70);
		g.drawString("Botão direito = Colocar/tirar bandeira", 100, 90);
		
		g.drawImage(tutoImages[1], 20, 130, null);
		g.drawString("Você ganha + 1 segundo de tempo", 100, 140);
		g.drawString("por cada quadrado cavado", 100, 160);
		
		g.drawImage(tutoImages[2], 20, 200, null);
		g.drawString("Clique no rosto acima do campo caso", 100, 210);
		g.drawString("queira cancelar o jogo", 100, 230);
		
		g.drawString("Não mostrar novamente", 50, 300);
		
		next.render(g);
		mark.render(g);
		
		if(!Program.showTutorial) {
			g.drawString("x", 20, 300);
		}
	}

}
