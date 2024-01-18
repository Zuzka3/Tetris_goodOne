import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Tetris extends JPanel implements KeyListener {
    public static int PLAYING = 0;
    public static int GAME_OVER = 1;
    private int state = PLAYING;
    Random r = new Random();
    private static int FRAME_RATE = 60;
    private static int delay = 1000 / FRAME_RATE;
    public static final int BOARD_WIDTH = 10;
    public static final int BOARD_HEIGHT = 20;
    public static final int TILE_SIZE = 50;
    private Timer gameLoop;
    private Color[][] grid = new Color[BOARD_HEIGHT][BOARD_WIDTH];
    private Color[] colors = {Color.decode("#00ffff"),
            Color.decode("#800080"),
            Color.decode("#ff7f00"),
            Color.decode("#0000ff"),
            Color.decode("#00ff00"),
            Color.decode("#ff0000"),
            Color.decode("#ffff00") };
    private final Piece[] pieces = new Piece[7];
    private Piece currentPiece;


    /**
     * Constructs a new {@code Tetris} game.
     * Initializes the game loop, pieces, and sets up the initial game state.
     */
    public Tetris() {
        initializePiece();
        currentPiece = randomPiece();
        gameLoop = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
                update();
            }
        });
        gameLoop.start();
    }

    /**
     * Initializes the Tetris pieces.
     */
    private void initializePiece(){

        pieces[0] = new Piece(new int[][]{{1,1}, {1,1},}, this, colors[6]);
        pieces[1] = new Piece(new int[][]{{0,1,1}, {1,1,0}}, this, colors[4]);
        pieces[2] = new Piece(new int[][]{{1,1,0}, {0,1,1},}, this, colors[5]);
        pieces[3] = new Piece(new int[][]{{1,1,1}, {1,0,0},}, this, colors[2]);
        pieces[4] = new Piece(new int[][]{{1,1,1}, {0,1,0},}, this, colors[1]);
        pieces[5] = new Piece(new int[][]{{1,1,1}, {0,0,1}}, this, colors[3]);
        pieces[6] = new Piece(new int[][]{{1,1,1,1},}, this, colors[0]);

    }

    /**
     * Generates a random Tetris piece from the available set.
     *
     * @return A randomly selected Tetris piece.
     */
    private Piece randomPiece(){
        return pieces[r.nextInt(pieces.length)];
    }

    /**
     * Sets the game board with the provided grid.
     *
     * @param gameBoard The grid representing the game board.
     */
    public void gameBoard(Color[][] gameBoard) {
        this.grid = gameBoard;
    }

    /**
     * Retrieves the current game board grid.
     *
     * @return The current game board grid.
     */
    public Color[][] getGameBoard(){
        return grid;
    }

    /**
     * Updates the game state, called in the game loop.
     */
    private void update(){
        if(state == PLAYING){
            currentPiece.update();
        }
    }

    /**
     * Sets the next Tetris piece and resets its state.
     * Checks for game over conditions.
     */
    public void currentShape(){
        currentPiece = randomPiece();
        currentPiece.reset();
        checkOverGame();

    }

    /**
     * Checks for game over conditions based on the current piece's position.
     */
    private void checkOverGame(){
        int[][] coords = currentPiece.getCoords();
        for(int i = 0; i < coords.length;i ++){
            for(int j = 0; j < coords[0].length; j++){
                if(coords[i][j] != 0){
                    if(grid[i + currentPiece.getY()][j + currentPiece.getX()] != null){
                        state = GAME_OVER;

                    }
                }
            }
        }
    }


    /**
     * Paints the Tetris game components, including the background, current piece, grid, and board.
     *
     * @param g The Graphics object to paint on.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawCurrentPiece(g);
        drawGrid(g);
        drawBoard(g);
        if (state == GAME_OVER) {
            drawGameOver(g);
        }
    }

    /**
     * Draws the background of the game.
     *
     * @param g The Graphics object to draw on.
     */
    private void drawBackground(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Draws the current falling Tetris piece.
     *
     * @param g The Graphics object to draw on.
     */
    private void drawCurrentPiece(Graphics g) {
        currentPiece.render(g);
    }

    /**
     * Draws the grid based on the current game state.
     *
     * @param g The Graphics object to draw on.
     */
    private void drawGrid(Graphics g) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] != null) {
                    g.setColor(grid[i][j]);
                    g.fillRect(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }
    }



    /**
     * Draws the Tetris board with grid lines.
     *
     * @param g The Graphics object to draw on.
     */
    private void drawBoard(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            g.drawLine(0, TILE_SIZE * i, TILE_SIZE * BOARD_WIDTH, TILE_SIZE * i);
        }
        for (int k = 0; k < BOARD_WIDTH + 1; k++) {
            g.drawLine(k * TILE_SIZE, 0, k * TILE_SIZE, TILE_SIZE * BOARD_HEIGHT);
        }
    }

    /**
     * Draws the "GAME OVER" message on the screen when the game ends.
     *
     * @param g The Graphics object to draw on.
     */
    private void drawGameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 70));
        g.drawString("GAME OVER", 25, 300);
    }


    /**
     * Handles key-typed events (unused in this implementation).
     *
     * @param e The KeyEvent object representing the key-typed event.
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Handles key-pressed events, allowing player interaction with the game.
     *
     * @param e The KeyEvent object representing the key-pressed event.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            currentPiece.fastSpeed();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            currentPiece.right();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            currentPiece.left();
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            currentPiece.rotateShape();
        }


        if (state == GAME_OVER) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid[i].length; j++) {
                        grid[i][j] = null;
                    }
                }
                currentShape();
                state = PLAYING;
            }
        }
    }


    /**
     * Handles key-released events, controlling the descent speed of the falling Tetris piece.
     *
     * @param e The KeyEvent object representing the key-released event.
     */
    @Override
    public void keyReleased(KeyEvent e) {
       if(e.getKeyCode() == KeyEvent.VK_DOWN){
            currentPiece.slow();
        }


    }




}
