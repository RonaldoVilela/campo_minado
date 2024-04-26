package application;

import javax.swing.JFrame;

import scenes.Game;
import scenes.Menu;
import scenes.Scene;

public class Program {
	
	public static JFrame window;
	public static GamePanel panel;
	public static MouseInput mouseInput;
	public static final int scale = 2;
	
	public static int mouseX = 0, mouseY = 0;
	
	public static Scene[] scenes = new Scene[3];
	public static void main(String[] args) {
		mouseInput = new MouseInput();
		scenes[0] = new Menu();
		scenes[1] = new Game();
		
		window = new JFrame();
		panel = new GamePanel();
		
		window.setTitle("Game");

		window.add(panel);
		
		window.setResizable(false);
		//window.setUndecorated(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();

		window.setLocationRelativeTo(null);

		window.setVisible(true);
		
		panel.startGame();
	}

}
