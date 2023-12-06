
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        int boardW = 600;
        int boardH = boardW;

        // Frame for the game.
        JFrame frame = new JFrame("Snake Game");
        frame.setVisible(true);
        frame.setSize(boardW, boardH);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame snakeGame = new SnakeGame(boardW, boardH);
        frame.add(snakeGame);
        frame.pack();

        snakeGame.requestFocus();
        }
    }
