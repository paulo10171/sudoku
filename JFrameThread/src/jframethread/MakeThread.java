package jframethread;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Random;

public class MakeThread extends JPanel {

	private static final long serialVersionUID = 1L;
	private int imageX, imageY;
	private ArrayList<Points> arrayPoints = new ArrayList<Points>();
	private final static int BALL_DIAMETER = 100;

	// constructor
	public MakeThread() {

		this.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				final int x = e.getX();
				final int y = e.getY();

				new Thread(new Runnable() {
					public void run() {
						imageX = x;
						imageY = y;

						redrawImage(imageX, imageY);

					}

				}).start();
			}

		});

	} // end of constructor

	private void redrawImage(int x, int y) {
		Points p = new Points(x, y, new Color(new Random().nextInt(255),
				new Random().nextInt(255), new Random().nextInt(255)));

		arrayPoints.add(p);

		while (true) {

			p.x += p.accelerationX;
			p.y += p.accelerationY;

			//p.changeAcceleration();
			
			//collision test
			for(Points point : arrayPoints)
			{
				testColision(p,point);
			}

			if (p.x < 0 || p.x > this.getWidth() - BALL_DIAMETER)
				p.accelerationX = -p.accelerationX;

			if (p.y < 0 || p.y > this.getHeight() - BALL_DIAMETER)
				p.accelerationY = -p.accelerationY;

			try {
				Thread.sleep(30);
			} catch (Exception e) {
				e.printStackTrace();
			}

			repaint();
			revalidate();
		}
	}

	public boolean collision(int obj1X, int obj1Y, int obj1W, int obj1H,
			int obj2X, int obj2Y, int obj2W, int obj2H) {
		if ((obj1X >= obj2X && obj1X <= obj2X + obj2W)
				&& (obj1Y >= obj2Y && obj1Y <= obj2Y + obj2H)) {
			return true;
		} else if ((obj1X + obj1W >= obj2X && obj1X + obj1W <= obj2X + obj2W)
				&& (obj1Y >= obj2Y && obj1Y <= obj2Y + obj2H)) {
			return true;
		} else if ((obj1X >= obj2X && obj1X <= obj2X + obj2W)
				&& (obj1Y + obj1H >= obj2Y && obj1Y + obj1H <= obj2Y + obj2H)) {
			return true;
		} else if ((obj1X + obj1W >= obj2X && obj1X + obj1W <= obj2X + obj2W)
				&& (obj1Y + obj1H >= obj2Y && obj1Y + obj1H <= obj2Y + obj2H)) {
			return true;
		} else {
			return false;
		}
	}
	
	public void testColision(Points p ,  Points p2)
	{
		if(p.x != p2.x && p.y != p2.y)
		{
			//if collision , change the direction
			if(collision(p.x , p.y , BALL_DIAMETER , BALL_DIAMETER , p2.x , p2.y , BALL_DIAMETER, BALL_DIAMETER))
			{
				p.changeDirection();
				p2.changeDirection();
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		for (Points point : arrayPoints) {
			g2d.setColor(point.getColor());
			g2d.fillOval(point.getX(), point.getY(), BALL_DIAMETER, BALL_DIAMETER);
			g2d.setColor(Color.black);
			g2d.setStroke(new BasicStroke(5));
			g2d.drawOval(point.getX() - 2, point.getY() - 2, BALL_DIAMETER + 5, BALL_DIAMETER + 5);
		}
	}

}
