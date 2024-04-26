package application;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class MouseInput implements MouseMotionListener, MouseListener{
	public boolean clicked = false;
	@Override
	public void mouseDragged(MouseEvent e) {
		Program.mouseX = e.getX() / Program.scale;
		Program.mouseY = e.getY() / Program.scale;
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Program.mouseX = e.getX() / Program.scale;
		Program.mouseY = e.getY() / Program.scale;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		clicked = true;
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		clicked = false;
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
