import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JFrame implements KeyListener, ActionListener {
    private static final int GRID_SIZE = 20;
    private static final int CELL_SIZE = 20;
    private static final int INITIAL_LENGTH = 3;
    private static final int DELAY = 150;

    private enum Direction { UP, DOWN, LEFT, RIGHT }

    private ArrayList<Point> snake;
    private Point food;
    private Direction direction;
    private Timer timer;

    public SnakeGame() {
        setTitle("Snake Game");
        setSize(GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        addKeyListener(this);

        initializeGame();

        timer = new Timer(DELAY, this);
        timer.start();

        setVisible(true);
    }

    private void initializeGame() {
        snake = new ArrayList<>();
        direction = Direction.RIGHT;

        for (int i = 0; i < INITIAL_LENGTH; i++) {
            snake.add(new Point(GRID_SIZE / 2 - i, GRID_SIZE / 2));
        }

        spawnFood();
    }

    private void spawnFood() {
        Random random = new Random();
        int x = random.nextInt(GRID_SIZE);
        int y = random.nextInt(GRID_SIZE);

        food = new Point(x, y);

        // Ensure food does not spawn on the snake
        while (snake.contains(food)) {
            x = random.nextInt(GRID_SIZE);
            y = random.nextInt(GRID_SIZE);
            food.setLocation(x, y);
        }
    }

    private void move() {
        Point head = snake.get(0);

        // Move in the current direction
        switch (direction) {
            case UP:
                head = new Point(head.x, (head.y - 1 + GRID_SIZE) % GRID_SIZE);
                break;
            case DOWN:
                head = new Point(head.x, (head.y + 1) % GRID_SIZE);
                break;
            case LEFT:
                head = new Point((head.x - 1 + GRID_SIZE) % GRID_SIZE, head.y);
                break;
            case RIGHT:
                head = new Point((head.x + 1) % GRID_SIZE, head.y);
                break;
        }

        // Check for collision with food
        if (head.equals(food)) {
            snake.add(0, food);
            spawnFood();
        } else {
            // Move the snake
            snake.add(0, head);
            snake.remove(snake.size() - 1);
        }

        // Check for collision with itself or boundaries
        if (snake.subList(1, snake.size()).contains(head) || head.x < 0 || head.x >= GRID_SIZE || head.y < 0 || head.y >= GRID_SIZE) {
            gameOver();
        }
    }

    private void gameOver() {
        timer.stop();
        JOptionPane.showMessageDialog(this, "Game Over!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        initializeGame();
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Paint the grid
        g.setColor(Color.BLACK);
        for (int i = 0; i <= GRID_SIZE; i++) {
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, GRID_SIZE * CELL_SIZE);
            g.drawLine(0, i * CELL_SIZE, GRID_SIZE * CELL_SIZE, i * CELL_SIZE);
        }

        // Paint the snake
        g.setColor(Color.GREEN);
        for (Point point : snake) {
            g.fillRect(point.x * CELL_SIZE, point.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        // Paint the food
        g.setColor(Color.RED);
        g.fillRect(food.x * CELL_SIZE, food.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (direction != Direction.DOWN) {
                    direction = Direction.UP;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (direction != Direction.UP) {
                    direction = Direction.DOWN;
                }
                break;
            case KeyEvent.VK_LEFT:
                if (direction != Direction.RIGHT) {
                    direction = Direction.LEFT;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != Direction.LEFT) {
                    direction = Direction.RIGHT;
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SnakeGame::new);
    }
}