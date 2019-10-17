import java.awt.*;
import java.util.Scanner;
import java.math.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

//TODO run and fix small errors
@SuppressWarnings("unused")
public class MouseSupport {
	
	private static int mouseX = -1;
	private static int mouseY = -1;

	public static void main(String[] args) {
		
		int panelX = 600;
		int panelY = 600;
		
		BetterDP panel = new BetterDP(panelX, panelY) {
			@Override
			public void mouseClicked(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		};
		
		int intialX = ThreadLocalRandom.current().nextInt(10, 100);
		int initialY = ThreadLocalRandom.current().nextInt(20, 100);

		fireProjectile(panel, intialX, initialY, Color.BLACK);
		
	}
	
	public static void fireProjectile(BetterDP panel, int initialX, int initialY, Color color) {
		Graphics graphics = panel.getGraphics();
		graphics.setFont(new Font("TimesRoman", Font.BOLD, 20));
		graphics.setColor(color);
		
		BufferedImage img = null;

		try {
			img = ImageIO.read(new File("bird.png")); 
			graphics.drawImage(img, initialX, panel.getHeight() - initialY, 20, 20, null); // x, y, w, h
		} catch (IOException e) {
			
		}
		
		
		int targetX = ThreadLocalRandom.current().nextInt(panel.getWidth() / 2, panel.getWidth() - 50);
		int targetY = ThreadLocalRandom.current().nextInt(initialY, panel.getHeight());
		
		graphics.fillRect(targetX, panel.getHeight() - targetY, 20, 20);
		
		while (mouseX < 0 && mouseY < 0) {
			panel.refresh();
		}
		
		
		double angle = calcAngle(mouseX, mouseY, initialX, initialY, panel.getHeight());
		double velocity = calcVelocity(mouseX, mouseY, initialX, initialY, panel.getHeight());
		
		double vX = velocity * Math.cos(angle);
		double vY = velocity * Math.sin(angle);
		double gravity = -9.8;
		
		double timeOfFlight = -2 * vY / gravity;
		int steps = 100;
		double dT = timeOfFlight / steps;
		double inc = dT;
		
		double x = initialX;
		double y = initialY;
		
		graphics.setColor(Color.WHITE);
		
		for (int i = 1; i <= steps; i++) {
			graphics.drawImage(img, (int)x, (int)(panel.getHeight()-y), 20, 20, null);
			panel.sleep(25);
			graphics.fillOval((int)x, (int)(panel.getHeight()-y) - 4, 30, 30);
			
			if (x >= targetX && x <= (targetX + 20) && y >= targetY && y <= (targetY + 20)) {
				graphics.setColor(color);
				graphics.drawString("YOU WIN", panel.getWidth() - 100, 60);
				break;
			}
			
			x = (vX * dT) + initialX;
			y = (vY * dT + 0.5 * (gravity * dT * dT)) + initialY;
			dT += inc;
		}
	}
	
	public static double getAngle(Scanner input) {
		System.out.println("Enter an Angle");
		System.out.print("> ");
		double angle = input.nextDouble();
		
		return angle;
	}
	
	public static double getVelocity(Scanner input) {
		System.out.println("Enter a Velocity");
		System.out.print("> ");
		double velocity = input.nextDouble();
		
		return velocity;
	}
	
	public static double calcAngle(int x, int y, int initialX, int initialY, int height) {
		double xComp = Math.abs(x - initialX);
		double yComp = height - Math.abs(initialY - y);
		
		double angle = Math.atan(yComp/xComp);
		
		return angle;
	}
	
	public static double calcVelocity(int x, int y,int initialX, int initialY, int height) {
		double xComp = Math.abs(x - initialX);
		double yComp = height - Math.abs(initialY - y);
		
		double resultant = Math.sqrt(Math.pow(xComp, 2) + Math.pow(yComp, 2));
		
		return resultant / 5;
	}

}
