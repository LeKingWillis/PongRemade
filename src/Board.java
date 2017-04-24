/*
 * 
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 * The Class Board.
 * @author wrightwj
 */
@SuppressWarnings("serial")
public class Board extends JPanel implements MouseMotionListener{

	/** The player score. */
	private int playerScore;

	/** The ball. */
	private Ball ball;

	/** The board. */
	Board board;

	/** The player 2. */
	private Paddle player1, player2;

	/** The border line. */
	private Rectangle borderLine;

	/** Score X, Y */
	private int scoreX = 450;
	private int scoreY = 30;

	/** The fill height. */
	private int midPoint = 420, centerWidth = 10, fillHeight = 1000;

	/** The starting ball X. */
	private int startingBallX = 345;

	/** The starting Y 1. */
	private int startingX1 = 30, startingY1 = 345;

	/** The starting Y 2. */
	private int startingX2 = 800, startingY2 = 345;

	/** The increase per turn. */
	private double increasePerTurn;

	/** The sleep. */
	private final int SLEEP = 1;

	/** The bottom border. */
	private final int BOTTOM_BORDER = 720;

	/** The one third of paddle. */
	private final int ONE_THIRD_OF_PADDLE = 3;

	/** The right edge of screen. */
	private final int RIGHT_EDGE_OF_SCREEN = 820;

	/** The easiest difficulty. */
	private double easiestDifficulty = .10;

	/** The easy difficulty. */
	private double easyDifficulty = .20;

	/** The medium difficulty. */
	private double mediumDifficulty = .25;

	/** The harder difficulty. */
	private double harderDifficulty = .30;

	/** The hardest difficulty. */
	private double hardestDifficulty = .40;

	/** The difficulty. */
	private int difficulty;

	/** The increase enemy strength. */
	private double increaseEnemyStrength = 1.15;

	/** The player 1 img. */
	BufferedImage player1Img;

	/** The player 2 img. */
	BufferedImage player2Img;

	/** the Font Size and style **/
	int fontSize = 40;

	/** passed to main method to end game loop */
	private static boolean finished;

	/** highscore to record the high score board */
	
	/** fileName to read/write to */
	String fileName = "highScores.txt";
	
	/** instantiate highScores */
	HighScores highScores;

	private double keepFromZero = .10;

	private double HALF = 2;
	
	/**
	 * Instantiates a new board.
	 *
	 * @param difficulty the difficulty
	 */
	public Board(int difficulty){
		setup(difficulty);
	}

	/**
	 * Sets the up.
	 *
	 * @param difficulty the new up
	 */
	void setup(int difficulty) {
		//initialize difficulty
		this.difficulty = difficulty;
		//change difficulty base don user input
		switch (difficulty){
		case 0:
			increasePerTurn = easiestDifficulty;
			break;
		case 1:
			increasePerTurn = easyDifficulty;
			break;
		case 2:
			increasePerTurn = mediumDifficulty;
			break;
		case 3:
			increasePerTurn = harderDifficulty;
			break;
		case 4:
			increasePerTurn = hardestDifficulty;
			break;
		default:
			break;
		}

		finished = false;
		playerScore=0;
		player1 = new Paddle(startingX1, startingY1);
		player2 = new Paddle(startingX2, startingY2);
		ball = new Ball(midPoint, startingBallX);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2= (Graphics2D) g;
		borderLine = new Rectangle(midPoint,-1,centerWidth,fillHeight);

		g2.setColor(Color.white);
		g2.setFont(new Font("" + playerScore,Font.BOLD, fontSize));
		g2.drawString("Your score: " + playerScore,scoreX, scoreY);
		g2.fill(borderLine);
		this.setBackground(Color.BLACK);
		g2.draw(borderLine);
		player1.draw(g2);
		player2.draw(g2);
		g2.setColor(ball.getColor());
		ball.draw(g2);
	}
	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent e) {

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		player1.move(e.getY());
	}

	/**
	 * Advance.
	 *
	 * @param diff the diff
	 */
	public void advance(long diff) {
		ball.move(diff);

		//check for collision with paddles
		if (ball.getBounds().intersects(player2.getBounds())){
			ball.xRate = -ball.xRate;
			try {
				Thread.sleep(SLEEP);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ball.xRate *= increaseEnemyStrength;
			playerScore++;
		}
		//check if ball interects with player 1
		if (ball.getBounds().intersects(player1.getBounds())){
			ball.xRate = -ball.xRate;
			//check for ball at top 3rd of paddle
			if (ball.getBounds().getCenterY() < (player1.getBounds().getMinY() + (player1.getBounds().getHeight() / ONE_THIRD_OF_PADDLE))){
				if (ball.yRate == 0){
					ball.yRate =-increasePerTurn;
					try {
						Thread.sleep(SLEEP);
					} catch (InterruptedException e) {
						return;
					}
				} else {
					try {
						Thread.sleep(SLEEP);
					} catch (InterruptedException e) {
						return;
					}
					ball.yRate = ball.yRate + increasePerTurn- keepFromZero ;
				}
				//check for ball to be at bottom third of paddle
				//thread sleeps added to get rid of insane increase per turn.
			} else if (ball.getBounds().getCenterY() > (player1.getBounds().getMaxY() - (player1.getBounds().getHeight() / ONE_THIRD_OF_PADDLE))){
				if (ball.yRate == 0){
					ball.yRate =+increasePerTurn;
					try {
						Thread.sleep(SLEEP);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					try {
						Thread.sleep(SLEEP);
					} catch (InterruptedException e) {
						return;
					}
					ball.yRate = ball.yRate + increasePerTurn + (keepFromZero/HALF );
				} 

			}

		}
		//check player to the left bound of screen, call lose if so.
		if (ball.getBounds().getX() < 0){
			player1.lose(playerScore);
		}
		//check bounds at top and bottom of screen
		if ((ball.getBounds().getMinY() <= 0) || (ball.getBounds().getMaxY() >= BOTTOM_BORDER-(ball.getHeight()))){
			ball.yRate = - ball.yRate;
		}
		//check ball bounds at the right, then call win if so.
		if (ball.getBounds().getMaxX() > RIGHT_EDGE_OF_SCREEN){
			player1.win(playerScore);

		}
		//move player2 automatically to hit the ball.
		player2.moveAuto(ball.getBounds().getCenterY());
	}

	/**
	 * Gets the difficulty at which to star the game.
	 *
	 * @return the difficulty
	 */
	public int getDifficulty() {
		return difficulty;
	}

	/**
	 * Sets the difficulty.
	 *
	 * @return the int
	 */

	public boolean finished() {
		return finished;
	}

	public static void setFinishedTrue(){
		finished = true;
	}
	
	
	//read highscores and write new score to scoreboard. 
	//shows at the end of the menu
	public void endGame() {
		writeScores();
		//wait to ensure writing is done.
		try {
			Thread.sleep(3);
		} catch (InterruptedException e) {
		}
		JOptionPane.showMessageDialog(null, "Highscores! \n" + getScores().toString());
		
		System.exit(0);
	}
	private String getScores() {
		highScores.readScores();
		String result = highScores.convertString();
		return result;
	}

	//gets scores to show in the end
	private void writeScores() {
		String name;
		try{
			
			name = JOptionPane.showInputDialog("What is your name?");
			highScores = new HighScores(name, playerScore, fileName);
			
		} catch(NullPointerException fNFE){
			name = "Default";
			highScores = new HighScores(name, playerScore, fileName);
		}
	}
}

