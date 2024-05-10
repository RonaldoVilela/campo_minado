package application;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JPanel;

import scenes.Game;
import scenes.Scene;

public class GamePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	BufferedImage gameImage = new BufferedImage(400, 320, BufferedImage.TYPE_INT_RGB);
	final static int FPS = 60;
	Thread gameThread;
	public static Font pixelFont;
	public long timerMills = 0;
	
	public static Scene scene = null;
	
	public GamePanel() {
		setDoubleBuffered(true);
		setPreferredSize(new Dimension(gameImage.getWidth() * Program.scale, gameImage.getHeight() * Program.scale));
		setLayout(null);
		addMouseMotionListener(Program.mouseInput);
		addMouseListener(Program.mouseInput);
		//setVisible(true);
		setFocusable(true);
		
		
		try {
			InputStream is = getClass().getResourceAsStream("/fonts/DePixelHalbfett.ttf");
			pixelFont = Font.createFont(Font.TRUETYPE_FONT, is);
			pixelFont = pixelFont.deriveFont(Font.PLAIN, 9);
			
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void changeScene(int sceneIndex) {
		scene = Program.scenes[sceneIndex];
		scene.start();
	}
	
	public void startGame() {
		changeScene(0);
		gameThread = new Thread(this::run);
		gameThread.start();
	}
	
	public void update() {
		if(scene != null) {
			scene.update();
		}
	}
	
	public void render(Graphics g) {
		
		if(scene != null) {
			scene.render(g);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics g2 = gameImage.createGraphics();
		g2.setFont(pixelFont);
		render(g2);
		g2.dispose();
		
		g.drawImage(gameImage, 0, 0, gameImage.getWidth()* Program.scale, gameImage.getHeight()* Program.scale, null);
	}

	
	public void run() {

		long millsPerFrame = 1000/FPS;
		while(true) {
			long startTime = System.nanoTime();
			update();
			repaint();
			long endTime = System.nanoTime();
			long totalTime = (endTime - startTime)/1000000;
			
			if(totalTime < millsPerFrame) {
				try {
					Thread.sleep(millsPerFrame - totalTime);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			if(Game.started && Game.state != Game.DEAD && Game.state != Game.VICTORY) {
				timerMills += (System.nanoTime() - startTime)/1000000;
				if(timerMills >= 1000) {
					Game.passSecond();
					timerMills -= 1000;
				}
			}
			
		}
		
	}

}
