import javax.swing.*;

/**
 * Conway's Game Of Life (7.5HD Improved Custom Program)
 * The Game of Life, also known simply as Life, is a cellular automaton
 * devised by the British mathematician John Horton Conway in 1970.
 * 
 * Standard Rules
 * 1. Any live cell with fewer than two live neighbors dies, as if by under population.
 * 2. Any live cell with two or three live neighbors lives on to the next generation.
 * 3. Any live cell with more than three live neighbors dies, as if by overpopulation.
 * 4. Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
 * 
 * @author Harry Lavell
 * @version October 2018
 */
public class GameOfLife
{
    // Constants
    private final static String WINDOW_TITLE = "Conway's Game of Life - 7.5HD Improved Custom Program"; // JFrame Window Title
    private final static int WINDOW_WIDTH = 1280; // JFrame Window Width
    private final static int WINDOW_HEIGHT = 720; // JFrame Window Height
    
    public static void main(String[] args) { 
        
        /** Create instance of GUI */
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI(WINDOW_TITLE,WINDOW_WIDTH,WINDOW_HEIGHT); // Create instance of GUI
            }
        });
        
    }

}








