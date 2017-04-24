/*
 * 
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JOptionPane;

/**
 * The Class Paddle.
 * @author wrightwj
 */
public class Paddle{
	/** The paddle, or player rectangle. This checks for collision. */
	Rectangle paddle;

	/** The x,y of the paddles. */
	int x, y;

	/** The width and height of the paddles. */
	int width =20, height = 50;


	/**
	 * Instantiates a new paddle.
	 *pre: must pass two ints to the constructor
	 *post: will move to specified location, whether or not it is int or double, as long as it is a number.
	 * @param x the x
	 * @param y the y
	 */
	public Paddle(int x, int y) {
		this.x = x;
		this.y = y;
		paddle = new Rectangle(x,y,width,height);
	}



	/**
	 * Draw.
	 *pre: must pass g
	 *post: will draw the paddles
	 * @param g the graphics used to draw.
	 */
	public void draw(Graphics g){
		Graphics2D g2= (Graphics2D) g;

		g2.setColor(Color.WHITE);
		g2.fill(paddle);

	}

	/**
	 * Moves based on the Y, since X axis is not moved moved at all..
	 *
	 * @param y the new y the paddle should be at.
	 */
	public void move(int y){
		this.y = y;
		paddle.setLocation(this.x, y);
	}
	/**
	 * Gets the bounds.
	 *
	 * @return the bounds
	 */
	public Rectangle getBounds() {
		return paddle;
	}

	/**
	 * Lose. This lets the player know they have lost. The only recovery from here is to restart.
	 *pre: pass the player's score to this method
	 *post: will tell you you failed.
	 * @param playerScore the player score
	 */
	public void lose(int playerScore) {
		JOptionPane.showMessageDialog(null, "You lost, score was: " + playerScore);
		Board.setFinishedTrue();
	}


	/**
	 * Move auto. This method is for the enemy paddle to move according to where the ball's Y location is.
	 *
	 * @param y the y at which the ball is at.
	 */
	public void moveAuto(double y) {
		y -= (this.height/2);
		this.y=(int)y;
		paddle.setLocation(this.x,(int)y);

	}


	/**
	 * Win. This method should not be called, but I have found that, due to unfortunate updates, there are ways to make it call.
	 *pre: pass it playerscore
	 *post: it will tell you you won
	 * @param playerScore the player score
	 */
	public void win(int playerScore) {
		JOptionPane.showMessageDialog(null, "You won! Somehow, you beat the unbeatable bot! \n "
				+ "Congratulations! You earned " +playerScore+ "points!");
		Board.setFinishedTrue();
	}
}
