import java.awt.*;

public class Piece {

    private  int x = 4, y = 0;
    private int normalSpeed = 600;
    private int fastSpeed = 40;
    private int delayTime = normalSpeed;
    private long startTime;
    private int deltaX = 0;
    private boolean collision = false;

    private int[][] coords;
    private Tetris tetris;
    private Color color;

    public Piece(int [][] coords, Tetris tetris, Color color){
        this.coords = coords;
        this.tetris = tetris;
        this.color = color;
    }

    /**
     * Resets the piece to its initial position.
     */
    public void reset(){
        this.x = 4;
        this.y = 0;
        collision = false;
    }


    /**
     * Updates the piece's position and checks for collisions.
     */
    public void update () {
        if(collision){
            for(int i = 0; i < coords.length; i++){
                for(int j = 0; j < coords[0].length; j++){
                    if(coords[i][j] != 0){
                        tetris.getGameBoard()[y + i][x + j] = color;
                    }
                }
            }
            checkLine();
            tetris.currentShape();
            return;
        }
        boolean moveX = true;
        if (!(x + deltaX + coords[0].length >10) && !(x + deltaX < 0)){
            for(int i = 0; i < coords.length; i++){
                for(int j = 0; j < coords[i].length;j++){
                    if (coords[i][j] != 0){
                        if(tetris.getGameBoard()[y+i][x + deltaX + j] != null){
                            moveX = false;
                        }
                    }
                }
            }
            if(moveX){
                x += deltaX;
            }
        }
        deltaX  = 0;

        if(System.currentTimeMillis() - startTime > delayTime) {
            if(!(y + coords.length >= Tetris.BOARD_HEIGHT)){
                for(int i = 0; i < coords.length;i++){
                    for(int j =0; j < coords[i].length;j++){
                        if(coords[i][j] != 0){
                            if(tetris.getGameBoard()[y + 1 + i][x + deltaX + j] != null){
                                collision = true;
                            }
                        }
                    }
                }
                if(!collision){
                    y++;
                }
            }else {
                collision = true;
            }
            startTime = System.currentTimeMillis();
        }
    }
    /**
     * Checks and clears completed lines on the Tetris game board.
     */
    private void checkLine(){
        int  i = tetris.getGameBoard().length - 1;
        for(int j = tetris.getGameBoard().length - 1; j > 0; j--){
            int count = 0;
            for(int k = 0; k < tetris.getGameBoard()[0].length; k ++){
                if(tetris.getGameBoard()[j][k] != null){
                    count++;
                }
                tetris.getGameBoard()[i][k] = tetris.getGameBoard()[j][k];
            }
            if(count < tetris.getGameBoard()[0].length){
                i--;
            }

        }
    }
    /**
     * Rotates the piece by transposing and reversing its shape.
     */
    public void rotateShape(){
        int[][] rotatedShape = transposeGrid(coords);
        reverseRows(rotatedShape);
        if((x + rotatedShape[0].length > Tetris.BOARD_WIDTH) || (y + rotatedShape.length > 20)){
            return;
        }

        for(int i = 0; i < rotatedShape.length; i ++){
            for(int j = 0; j < rotatedShape[i].length; j ++){
                if(rotatedShape[i][j] != 0){
                    if(tetris.getGameBoard()[y + i][x + j] != null){
                        return;
                    }
                }
            }
        }
        coords = rotatedShape;
    }

    /**
     * Transposes the given 2D grid.
     *
     * @param grid The grid to transpose.
     * @return The transposed grid.
     */
    private int [][] transposeGrid(int[][] grid){
        int [][] temp = new int [grid[0].length][grid.length];
        for(int i = 0; i < grid.length;i++){
            for (int j = 0; j< grid[0]. length; j++){
                temp[j][i] = grid[i][j];
            }
        }
        return temp;
    }

    /**
     * Reverses the rows of the given 2D grid.
     *
     * @param grid The grid to reverse.
     */
    private void reverseRows(int [][] grid){
        int middle = grid.length / 2;
        for (int i = 0; i < middle; i++){
            int[] temp = grid[i];
            grid[i] = grid[grid.length - i - 1];
            grid[grid.length - i - 1] = temp;
        }
    }

    /**
     * Renders the piece on the specified Graphics object.
     *
     * @param g The Graphics object to render on.
     */
    public void render(Graphics g ){
        for(int i = 0; i < coords.length; i++){
            for(int j =0; j < coords[0].length; j++){
                if(coords[i][j] != 0) {
                    g.setColor(color);
                    g.fillRect(j * Tetris.TILE_SIZE + x * Tetris.TILE_SIZE, i * Tetris.TILE_SIZE + y * Tetris.TILE_SIZE, Tetris.TILE_SIZE, Tetris.TILE_SIZE);
                }
            }
        }
    }
    /**
     * Sets the piece to move at a faster speed.
     */
    public void fastSpeed(){
        delayTime = fastSpeed;
    }

    /**
     * Sets the piece to move at the normal speed.
     */
    public void slow(){
        delayTime = normalSpeed;
    }

    /**
     * Moves the piece to the right.
     */
    public void right(){
        deltaX = 1;
    }

    /**
     * Moves the piece to the left.
     */
    public void left(){
        deltaX = -1;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int[][] getCoords() {
        return coords;
    }
    public void setCoords(int[][] coords) {
        this.coords = coords;
    }
}
