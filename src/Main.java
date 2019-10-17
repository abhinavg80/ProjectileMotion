import java.awt.*;
import java.util.Scanner;
import java.math.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
	
//x = velocity * dT * Math.cos(angle);
//y = velocity * dT * Math.sin(angle) + 0.5 * (gravity * dT * dT);

@SuppressWarnings("unused")
public class Main {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		double angle = getAngle(input);
		double velocity = getVelocity(input);
		fireProjectile(new DrawingPanel(600, 600), angle, velocity, 40, 100, Color.BLUE);

	}
	
	public static void fireProjectile(DrawingPanel panel, double angle, double velocity, int initialX, int initialY, Color color) {
		Graphics graphics = panel.getGraphics();
		graphics.setColor(color);
		
		double vX = velocity * Math.cos(Math.toRadians(angle));
		double vY = velocity * Math.sin(Math.toRadians(angle));
		double gravity = -9.8;
		
		double timeOfFlight = -2 * vY / gravity;
		int steps = 100;
		double dT = timeOfFlight / steps;
		double inc = dT;
		
		double x = initialX;
		double y = initialY;
		
		for (int i = 1; i <= steps; i++) {
			graphics.fillOval((int)x, (int)(panel.getHeight()-y), 10, 10);
			panel.sleep(50);
			graphics.setColor(Color.WHITE);	
			graphics.fillOval((int)x, (int)(panel.getHeight()-y), 10, 10);
			graphics.setColor(color);
			
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

}
