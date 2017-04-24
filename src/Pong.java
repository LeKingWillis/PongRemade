/*
 * 
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * The Class Pong. It contains the main method
 * pre: call the main method
 * post: my game will run
 * @author wrightwj
 */
@SuppressWarnings("serial")
public class Pong extends JFrame{

	/** The Constant WIDTH, HEIGHT. of the frame*/
	public static final int WIDTH = 1000, HEIGHT = 750;

	/** The Constant TWICE and HALF to remove magic numbers. */
	public static final int HALF = 2, TWICE = 2;

	/** The Constant TENTH_OF_SCREEN_WIDTH to give the button JPanel a dimension */
	public static final int TENTH_OF_SCREEN_WIDTH = 100;

	/** The difficulty level at the start. It's a very minor difference between the modes. */
	static Integer difficult;

	/**
	 * The main method. It calls all methods and insures the user knows what to do
	 *
	 * @param args the arguments
	 */
	//main method, draws the jframe and sets it to visible
	public static void main(String[] args){
		JFrame f = new JFrame("Modern Pong");

		f.setSize(WIDTH, HEIGHT);
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		f.setBackground(Color.black);
		f.setResizable(false);


		//add over-arching JPanel
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setLayout(new BorderLayout());
		f.add(panel);

		//add buttons to right
		JPanel buttonPanel = new JPanel();


		JOptionPane.showMessageDialog(null, "Welcome to Pong: Updated! \n"
				+ "I hope you enjoy it ^-^ \n");

		JOptionPane.showMessageDialog(null, "To play, move your mouse on the window to control the left paddle\n"
				+ "you can win, however it is extremely hard.\n"
				+ "You earn points by bouncing the ball off the opponent.");
		try{
			String difficulty = JOptionPane.showInputDialog(null,  "Last one!\n"
				+"On a scale from 0-3, how hard to you want this game? (Please enter a single digit)");
				if (difficulty.equals("0") || difficulty.equals("1") || difficulty.equals("2") || difficulty.equals("3")){
					try{
						difficult = Integer.parseInt(difficulty);
					} catch (NullPointerException nPE){
						difficult = 0;
						JOptionPane.showMessageDialog(null, "There seems to have been an error,\n"
								+ "your difficulty has been set to the easiest.");
					}
				} else {
					difficult = 4;
					JOptionPane.showMessageDialog(null, "Since you did that wrong, you can play on the maximum difficulty ^_^");
				}
		} catch (NullPointerException nPE){
			difficult = 0;
			JOptionPane.showMessageDialog(null, "There seems to have been an error,\n"
					+ "your difficulty has been set to the easiest.");
		}
		
		//instantiate board
		Board board = new Board(difficult);
		//make it black
		buttonPanel.setBackground(Color.BLACK);
		//make it smaller than big panel
		buttonPanel.setBounds(WIDTH/TENTH_OF_SCREEN_WIDTH, 0, TENTH_OF_SCREEN_WIDTH, HEIGHT);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		//create restart button
		JButton restart = new JButton("Restart the game!");
		restart.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				board.setup(board.getDifficulty());
			}
		});
		//create exit button
		JButton exit = new JButton("Exit the game!");
		exit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {

				try{
					String leave = JOptionPane.showInputDialog("Are you sure? (Y/N)");
					if (!(leave.equals(null))){
						if ((leave.equalsIgnoreCase("Y")) || (leave.equalsIgnoreCase("yes"))) {
							System.exit(0);		
						} else if ((leave.equalsIgnoreCase("no")) || (leave.equalsIgnoreCase("n"))){
							return;
						} else{
							return;
						}
					}
				}	catch(Exception thisDoesntWork){
					return;
				}
			}
		});
		//add button panel to east and make it black
		buttonPanel.add(restart);

		buttonPanel.add(exit);

		panel.setBackground(Color.BLACK);
		panel.add(buttonPanel, BorderLayout.EAST);



		//add board/game to the left
		board.addMouseMotionListener(board);
		panel.add(board, BorderLayout.CENTER);

		f.setVisible(true);


		long oldTime = System.currentTimeMillis();

		while(!board.finished()){
			f.repaint();
			long currTime = System.currentTimeMillis();		

			board.advance(currTime - oldTime);
			oldTime = currTime;


			try {
				Thread.sleep(1);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
		
		board.endGame();
	}
}

