/*
 * GUI_part.java
 * 
 * Version:
 *   $Id$
 *   
 * Revision:
 *   $Log$
 *  
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * The GUI part of the Chess
 * 
 * @Author's Login ID: yc7816
 * 
 * @Name: Yihao Cheng
 * 
 */
public class GUI_part extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	private Chess pattern;
	private ArrayList<PieceButton> buttons = new ArrayList<PieceButton>();
	private JLabel status;
	private int fButton = -1;
	private int sButton = -1;
	private int select = 0;
	private int move = 0;

	/**
	 * Constructs GUI based off current game
	 * 
	 * @param model
	 *            Current Game
	 */
	public GUI_part(Chess pattern) {
		this.pattern = pattern;

		JFrame userView = new JFrame("Chess Solitare(Yihao Cheng ~ yc7816");
		userView.setLayout(new BorderLayout());
		Panel game = new Panel();
		game.setLayout(new GridLayout(Chess.ROWS, Chess.COLUMNS));
		ButtonListener listener = new ButtonListener();
		PieceButton button;
		Boolean white = true;
		int count = 0;
		for (int i = 0; i < pattern.getState().size(); i++) {
			if (!(pattern.getState().get(i).equals("."))) {
				button = new PieceButton(pattern.getState().get(i), i);
			} else {
				button = new PieceButton("", i);
			}
			if (Chess.COLUMNS % 2 != 0) {
				if (white) {
					button.setBackground(Color.WHITE);
					button.setForeground(Color.BLUE);
					white = false;
				} else {
					button.setBackground(Color.BLUE);
					button.setForeground(Color.WHITE);
					white = true;
				}
			} else {
				if (count % Chess.COLUMNS == 0) {
					white = !(white.booleanValue());
				}
				if (white) {
					button.setBackground(Color.WHITE);
					button.setForeground(Color.BLUE);
					white = false;
				} else {
					button.setBackground(Color.BLUE);
					button.setForeground(Color.WHITE);
					white = true;
				}
				count++;
			}
			button.addActionListener(listener);
			game.add(button);
			buttons.add(button);
		}
		Panel userButtons = new Panel();
		userButtons.setLayout(new FlowLayout());
		JButton reset = new JButton("Reset");
		reset.addActionListener(listener);
		JButton nextmove = new JButton("Next Move");
		nextmove.addActionListener(listener);
		JButton help = new JButton("Help");
		help.addActionListener(listener);
		JButton quit = new JButton("Quit");
		quit.addActionListener(listener);
		userButtons.add(reset);
		userButtons.add(nextmove);
		userButtons.add(help);
		userButtons.add(quit);

		status = new JLabel("Moves: 0       Welcome! Make your first move");

		userView.add(game, BorderLayout.CENTER);
		userView.add(userButtons, BorderLayout.SOUTH);
		userView.add(status, BorderLayout.NORTH);

		userView.setSize(600, 450);
		userView.setLocation(100, 100);
		userView.setVisible(true);
		userView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		boardDisplay();
	}

	/**
	 * Calls Update after button has been pressed
	 */
	class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Object buttonObject = e.getSource();
			Observable listener = new Observable();
			if (buttonObject instanceof PieceButton) {
				update(listener, buttonObject);
			} else if (buttonObject instanceof JButton) {
				update(listener, buttonObject);
			}
		}
	}

	/**
	 * Resets buttons after a reset, de-selection, or no match
	 */
	private void recolorButtons() {
		int count = 0;
		Boolean white = true;
		for (PieceButton button : buttons) {
			if (Chess.COLUMNS % 2 != 0) {
				if (white) {
					button.setBackground(Color.WHITE);
					button.setForeground(Color.BLUE);
					white = false;
				} else {
					button.setBackground(Color.BLUE);
					button.setForeground(Color.WHITE);
					white = true;
				}
			} else {
				if (count % Chess.COLUMNS == 0) {
					white = !(white.booleanValue());
				}
				if (white) {
					button.setBackground(Color.WHITE);
					button.setForeground(Color.BLUE);
					white = false;
				} else {
					button.setBackground(Color.BLUE);
					button.setForeground(Color.WHITE);
					white = true;
				}
				count++;
			}
		}
	}

	/**
	 * Updates board based on which button is pressed
	 * 
	 * @param t
	 *            -- not used
	 * @param o
	 *            The Button Pushed
	 */
	@Override
	public void update(Observable t, Object o) {
		if (o instanceof PieceButton) {
			if (select == 0) {
				select++;
				fButton = ((PieceButton) o).getPos();
				buttons.get(fButton).setBackground(Color.RED);
				buttons.get(fButton).setForeground(Color.WHITE);
				status.setText("Moves: " + move + " "
						+ PieceName.getName(pattern.getState().get(fButton))
						+ " Selected.");
			} else if (select == 1) {
				sButton = ((PieceButton) o).getPos();
				if (sButton != fButton) {
					buttons.get(sButton).setBackground(Color.RED);
					buttons.get(sButton).setForeground(Color.WHITE);
					status.setText("Moves: "
							+ move
							+ " "
							+ PieceName
									.getName(pattern.getState().get(sButton))
							+ " Selected.");
					String first = pattern.getState().get(fButton);
					String second = pattern.getState().get(sButton);
					if (pattern.makeMovement(fButton, sButton)) {
						move++;
						status.setText("Moves: " + move + "  You took a "
								+ PieceName.getName(second) + " with a "
								+ PieceName.getName(first));
						boardDisplay();
						fButton = -1;
						sButton = -1;
						select = 0;
						recolorButtons();
					} else {
						status.setText("Moves: "
								+ move
								+ "  Invalid Move. Press Help button or Try Again.");
						fButton = -1;
						sButton = -1;
						select = 0;
						recolorButtons();
						boardDisplay();
					}
				} else {
					status.setText("Moves: " + move + "  Select Piece to Move.");
					fButton = -1;
					sButton = -1;
					select = 0;
					recolorButtons();
					recolorButtons();
				}
				recolorButtons();
			}
		} else if (o instanceof JButton) {
			String buttonText = ((JButton) o).getText();
			if (buttonText.compareTo("Quit") == 0) {
				System.exit(0);
			} else if (buttonText.compareTo("Reset") == 0) {
				pattern.reset_TheGame();
				move = 0;
				status.setText("Moves: " + move
						+ "  Welcome! Make your first move. ");
				select = 0;
				fButton = -1;
				sButton = -1;
				recolorButtons();
				boardDisplay();
			} else if (buttonText.compareTo("Help") == 0) {
				JOptionPane
						.showMessageDialog(
								null,
								"Welcome to play Chess Solitaire!\n To begin the game: \n"
										+ "Select the chess by clicking and click the location you want to move.\n"
										+ "If you don't know next step, then just click the \" Next Move \" button.\n"
										+ "You must remove a piece every move.\n The game is "
										+ "over when there is only one piece "
										+ "remaining.\n Good Luck!");
			} else if (buttonText.compareTo("Next Move") == 0) {
				if (pattern.getNextMove()) {
					move++;
					status.setText("Moves: " + move + "  Next Move Displayed");
					boardDisplay();
				} else {
					status.setText("Moves: "
							+ move
							+ "  No More Valid Moves. "
							+ "Game Over. Press Reset to Start Over, or Quit to exit.");
					boardDisplay();
				}
			}
		}
	}

	/**
	 * Updates the board to reflect current state of model
	 */
	private void boardDisplay() {
		ArrayList<String> current = pattern.getState();
		int i = 0;
		for (PieceButton button : buttons) {
			if (current.get(i).equals(".")) {
				button.setText("");
			} else {
				button.setText(current.get(i));
			}
			i++;
		}
		if (pattern.checkForGoal(pattern.state)) {
			status.setText("Moves: " + move + "  You Won!");
		}
	}

	/**
	 * Error checks game file Creates new Chess Model Starts GUI
	 * 
	 * @param args
	 *            Chess game inputFile
	 */
}

class PieceButton extends JButton {

	/**
	 * Class Generated
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Position of button on game grid
	 */
	private int pos;

	/**
	 * Constructor
	 * 
	 * @param name
	 *            Name on Button
	 * @param pos
	 *            Position of Button
	 */
	public PieceButton(String name, int pos) {
		super(name);
		this.pos = pos;
	}

	/**
	 * Returns position
	 * 
	 * @return position
	 */
	public int getPos() {
		return pos;
	}

}

class PieceName {
	/**
	 * Returns piece name based on Id
	 * 
	 * @param piece
	 *            Piece Id
	 * @return name
	 */
	public static String getName(String piece) {
		if (piece.equals("Q")) {
			return "Queen";
		} else if (piece.equals("K")) {
			return "King";
		} else if (piece.equals("P")) {
			return "Pawn";
		} else if (piece.equals("B")) {
			return "Bishop";
		} else if (piece.equals("R")) {
			return "Rook";
		} else if (piece.equals("N")) {
			return "Knight";
		} else {
			return "Empty Space";
		}
	}
}