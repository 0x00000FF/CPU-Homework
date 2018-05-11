package me.patche.bouncingball;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BouncingBallFrame extends JFrame implements MouseListener, ActionListener  {
	private static final long serialVersionUID = 1L;
	
	private Ball ball;
	private JPanel panel = new JPanel();
	private Timer t = new Timer(50, this);
	
	public BouncingBallFrame()
	{
		ball = new Ball();
		
		super.setTitle("Bouncing Ball");
		super.setSize(400, 400);
		
		super.setLocation(100, 100);
		super.setVisible(true);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		this.addMouseListener(this);
		t.start();
	}
	

	@Override
	public void paint(Graphics g) {
		this.paintComponents(g);
		
		g.setColor(Color.blue);
		g.fillArc(150, 
				this.getHeight() - (108 + (int)ball.getPos())
				, 100, 100, 0, 360);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (ball.isDrop()) {
			if (ball.getPos() != 0) {
				if (ball.getPos() < 0) {
					ball.setVelocity(
							ball.getPos() * -1
							);
					
					ball.setDrop(false);
				} else {
					ball.setPos(
							ball.getPos() - ball.getVelocity()
							);
					
					ball.setVelocity(
							ball.getVelocity() + 1
							);
				}
			}
		} else {
			ball.setPos(
					ball.getPos() + ball.getVelocity()
					);
			
			ball.setVelocity(
					ball.getVelocity() - 0.8
					);
			
			if (ball.getVelocity() <= 0)
				ball.setDrop(true);
		}
		
		this.repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		ball.setDrop(false);
		ball.setVelocity(20);
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { }
}
