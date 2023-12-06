import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {

    private int height;
    private int width;
    private int tileSize = 25;
    private Tile snakeHead;
    private ArrayList<Tile> snakeBody;
    private Tile food;
    private Random random;
    private Timer gameLoop;
    private int velocityX;
    private int velocityY;
    boolean isGameOver = false;

    public SnakeGame( int height, int width) {
        this.height = height;
        this.width = width;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(7,7);
        snakeBody = new ArrayList<>();
        food = new Tile(12,13);
        random = new Random();
        placeFood();

        velocityX = 0;
        velocityY = 0;

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawSnake(g);
        drawFood(g);
    }
    public void drawSnake(Graphics g){
        // snake color
        g.setColor(Color.WHITE);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);
        // snake body
        for(int i = 0; i < snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize,true);
        }
        //score
        g.setFont(new Font("Calibri", Font.PLAIN, 14));
        if(isGameOver){
            g.setColor(Color.RED);
            g.drawString("Game Over: " + snakeBody.size(), tileSize - 16, tileSize);
        }else {
            g.drawString("Score: "+ snakeBody.size(), tileSize - 16, tileSize);
        }
    }
    public void drawFood(Graphics g){
        //food color
        g.setColor(Color.DARK_GRAY);
        g.fillRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize);
        g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize,true);
    }
    public void placeFood(){
        food.x = random.nextInt(width/tileSize);
        food.y = random.nextInt(height/tileSize);
    }
    public boolean isCollide(Tile tile, Tile tile2){
        return tile.x == tile2.x && tile.y == tile2.y ;
    }
    public void move(){
        //eat food
        if(isCollide(snakeHead,food)){
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }
        //snake body
        for (int i = snakeBody.size() - 1; i >= 0; i--){
            Tile snakePart = snakeBody.get(i);
            if(i == 0){
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                Tile prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        // gameover
        for (int i = 0 ; i< snakeBody.size(); i++){
            Tile snakePart = snakeBody.get(i);
            if(isCollide(snakeHead, snakePart)){
                isGameOver = true;
            }
        }
        if(snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > width ||
                snakeHead.y * tileSize < 0 || snakeHead.y * tileSize > height){
            isGameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(isGameOver){
            gameLoop.stop();
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1){
            velocityY = -1;
            velocityX = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1){
            velocityY = 1;
            velocityX = 0;
        }else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1){
            velocityY = 0;
            velocityX = -1;
        }else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1){
            velocityY = 0;
            velocityX = 1;
        }
    }
    // no need
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
