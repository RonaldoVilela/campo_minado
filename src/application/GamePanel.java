package application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import scenes.Scene;

public class GamePanel extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;
	
	BufferedImage gameImage = new BufferedImage(400, 320, BufferedImage.TYPE_INT_RGB);
	final int scale = 2;
	final static int FPS = 60;
	Thread gameThread;
	
	public static Scene scene = null;
	
	public GamePanel() {
		setDoubleBuffered(true);
		setPreferredSize(new Dimension(gameImage.getWidth() * Program.scale, gameImage.getHeight() * Program.scale));
		setLayout(null);
		addMouseMotionListener(Program.mouseInput);
		addMouseListener(Program.mouseInput);
		//setVisible(true);
		setFocusable(true);
		scene = Program.scenes[0];
		
	}
	
	public static void changeScene(int sceneIndex) {
		scene = Program.scenes[sceneIndex];
	}
	
	public void startGame() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void update() {
		if(scene != null) {
			scene.update();
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, 400, 320);
		if(scene != null) {
			scene.render(g);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics g2 = gameImage.createGraphics();
		render(g2);
		g2.dispose();
		
		g.drawImage(gameImage, 0, 0, gameImage.getWidth()* Program.scale, gameImage.getHeight()* Program.scale, null);
	}

	@Override
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
			
		}
		
	}

}
