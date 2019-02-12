import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Conway's Game Of Life (7.5HD Improved Custom Program)
 * Creates the grid of user defined size and updates the grid
 * according to the game rules on update call.
 * 
 * @author Harry Lavell
 * @version October 2018
 */
public class Grid {
    private static Cell[][] grid; // Stores Cells of grid in a 2d Array
    private static ArrayList<Cell> aliveNextGeneration = new ArrayList<>(); // Stores the positions of the live cells in the next generation
    private static ArrayList<Cell> deadNextGeneration = new ArrayList<>(); // Stores the positions of the dead cells in the next generation
    private static boolean gridExists = false; // Stores true if grid currently exists
    private final long NANO_TO_MILLI = 1000000; // Nano seconds in one millisecond
    private long timeStart; // Stores starting times
    private long timeEnd; // Stores elapsed times
    public final int GRID_SIZE = 125; // Row & column size of the grid

    /**
     * Initializes the grid if not created, populates it with dead cells
     * and adds each cell to the the main panel parsed within the method
     * parameters
     * @param panel, primary GUI panel that displays the cells
     */
    public void createGrid(JPanel panel) {
        timeStart = System.nanoTime(); // Stores time started

        // Create Cell[][] and set layout if grid does not exist
        if (!gridExists()) {
            grid = new Cell[GRID_SIZE][GRID_SIZE]; // Create Empty 2D Cell Array of (rows, cols)
            panel.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE)); // Change panel to GridLayout and assign rows and cols
        }

        // Populate each element of array with new Cell
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {

                // Grid does not exist
                if (!gridExists()) {
                    grid[row][col] = new Cell(0, row, col, new JPanel()); // Create new Cell at grid[row][col]
                    grid[row][col].dead(); // Call Cell's dead() method
                    grid[row][col].addToPanel(panel); // Call Cell's addToPanel() method

                    JPanel cellPanel = grid[row][col].getPanel(); // Create reference to Cell's JPanel
                    cellPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Set Border Colour
                } else {
                    grid[row][col].dead(); // Call Cell's dead() method
                }

            }
        }

        gridExists = true; // Grid successfully created, therefore does exist
        
        // Calculate time elapsed
        timeEnd = (System.nanoTime() - timeStart) / NANO_TO_MILLI; // Time Elapsed into ms
        System.err.println("Grid Created in "+timeEnd+"ms"); // Print time elapsed in milliseconds
    }

    /**
     * Iterates through grid, checks neighbours, and applies games
     * rules to each cell
     */
    public void updateGrid() {
        timeStart = System.nanoTime(); // Stores time started

        // Iterate through entire grid & scan each Cell and it's neighbours
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                int deadCellNeighbours = 0;
                int aliveCellNeighbours = -1; // Excludes own live Cell

                // Iterate through Cell's surrounding neighbours and itself
                for (int cRow = row-1; cRow <= row+1; cRow++) {
                    for (int cCol = col-1; cCol <= col+1; cCol++) {
                        boolean withinGridBounds = inBounds(cRow,cCol,grid);

                        // Check Cell is alive and if original Cell is dead
                        if (withinGridBounds && grid[cRow][cCol].isAlive() && !grid[row][col].isAlive()) {
                            deadCellNeighbours+=1; // Increment the deadCellNeighbours of the current Cell
                        }

                        // Check Cell is alive and if original Cell is also alive
                        if (withinGridBounds && grid[cRow][cCol].isAlive() && grid[row][col].isAlive()) {
                            aliveCellNeighbours+=1; // Increment the aliveCellNeighbours of the current Cell
                        }

                    }
                }

                applyGameRules(row,col,aliveCellNeighbours,deadCellNeighbours); // Call applyGameRules() method
            }
        }

        updateNextGeneration(); // Call updateNextGeneration method()

        // Calculate & print time elapsed
        timeEnd = (System.nanoTime() - timeStart) / NANO_TO_MILLI; // Calculate & convert to ms
        System.err.println("Updated: "+timeEnd+"ms"); // Print average time per generation
    }

    /**
     * Apply the rules of the Game of Life to a specific Cell via the
     * parameters parsed
     * @param row, the row the Cell is located on within the grid
     * @param col, the column the Cell is located on within the grid
     * @param aliveCellNeighbours, the Cell's number of live neighbours
     * @param deadCellNeighbours, the Cell's number of dead neighbours
     */
    public void applyGameRules(int row, int col, int aliveCellNeighbours, int deadCellNeighbours) {
        // Check Cell is alive and live neighbour count is less than 2 (Dies)
        if (grid[row][col].isAlive() && aliveCellNeighbours < 2) {
            deadNextGeneration.add(grid[row][col]); // Add Cell to deadNextGeneration ArrayList
        }

        // Check Cell is alive and live neighbour count is exactly 2 or 3 (Lives on)
        else if (grid[row][col].isAlive() && aliveCellNeighbours == 2 || aliveCellNeighbours == 3) {
            aliveNextGeneration.add(grid[row][col]); // Add Cell to aliveNextGeneration ArrayList
        }

        // Check Cell is alive and live neighbour count greater than 3 (Dies)
        else if (grid[row][col].isAlive() && aliveCellNeighbours > 3) {
            deadNextGeneration.add(grid[row][col]); // Add Cell to deadNextGeneration ArrayList
        }

        // Check Cell is dead and live neighbour count is 3 (Becomes Alive)
        else if (!grid[row][col].isAlive() && deadCellNeighbours == 3) {
            aliveNextGeneration.add(grid[row][col]); // Add Cell to aliveNextGeneration ArrayList
        }
    }

    /**
     * Iterates through aliveNextGeneration & deadNextGeneration
     * ArrayLists and sets appropriate states depending on the cell
     */
    public void updateNextGeneration() {
        // Iterate through ArrayList and set all Cells to alive state
        for (Cell a : aliveNextGeneration) {
            a.alive(); // Call alive() method in Cell
        }

        // Iterate through ArrayList and set all Cells to dead state
        for (Cell d : deadNextGeneration) {
            d.dead(); // Call dead() method in Cell
        }

        // Clear ArrayLists
        aliveNextGeneration.clear();
        deadNextGeneration.clear();
    }

    /**
     * Returns true if grid has been created already
     * @return gridExists, returns true if grid exists
     */
    public boolean gridExists() {
        return gridExists;
    }

    /**
     * Returns the current grid.
     * @return grid, returns Cell[][] of grid
     */
    public Cell[][] getGrid() {
        return grid;
    }

    /**
     * Checks whether the input parameters are within the
     * bounds of the grid
     * @param row, the current row to test
     * @param col, the current column to test
     * @param grid, the grid to bounds to test
     * @return true if within bounds
     */
    private boolean inBounds(int row, int col, Cell[][] grid) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[row].length;
    }

}