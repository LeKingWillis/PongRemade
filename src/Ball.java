import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;


/**
 * The Class Ball. This handles movement and drawing of the ball object.
 * @author wrightwj
 */
@SuppressWarnings("serial")
public class Ball extends JPanel{

	/** The width of the ball, also diameter. */
	private int width = 15;

	/** The x,y location. */
	private double x, y;

	/** How the ball moves in the xDirection. */
	double xRate;

	/** How the ball moves in the yDirection. */
	double yRate;

	/** The starting x rate to the right. */
	double defaultXRate = .25;

	/** The max value for the red, blue, or green color. */
	private final int RGB_MAX_VALUE = 255;

	/**
	 * Instantiates a new ball.
	 *pre: must have x and y values as ints
	 *post: will create a ball object at that location
	 * @param x the x
	 * @param y the y
	 */
	public Ball(int x, int y){
		this.x = x;
		this.y = y;
		xRate = .25;
		yRate = .10;
	}

	/**
	 * Draw.
	 *pre: must pass G for graphics
	 *post: will draw oval using G
	 * @param g the graphics variable
	 */
	public void draw(Graphics g){
		g.fillOval((int)x,(int)y,width,width);	
	}




	/* (non-Javadoc)
	 * @see java.awt.Component#getBounds()
	 */
	public Rectangle getBounds(){
		return new Rectangle((int)x,(int)y,width,width);
	}

	/**
	 * Move.
	 *	pre: must pass the long for difference in time between old and new
	 *post: will move the ball by that difference multiplied by rate.
	 * @param diff the diff
	 */
	public void move(long diff) {
		x +=(xRate*diff);
		y +=(yRate*diff);


	}

	/**
	 * Gets the color.
	 *pre: must want color to call this method
	 *post: will return a random color
	 * @return the color
	 */
	public Color getColor() {
		return new Color((int)(Math.random() * RGB_MAX_VALUE),(int)(Math.random()
				* RGB_MAX_VALUE),(int)(Math.random() * RGB_MAX_VALUE));
	}


}
