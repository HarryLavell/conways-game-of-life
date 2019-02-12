import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

/**
 * Conway's Game Of Life (7.5HD Improved Custom Program)
 * Allow for the saving and loading of grids to and from file.
 *
 * @author Harry Lavell
 * @version October 2018
 */
public class FileManager {
    private static String saved = ""; // Stores the saved String
    private static String loaded = ""; // Stores the loaded String
    Grid gd = new Grid(); // Create instance of Grid
    Cell[][] grid; // Create Cell[][] to store grid data

    /**
     * Iterates through grid and saves the current state of all
     * cells to the saved String
     */
    public void saveGrid() {
        grid = gd.getGrid(); // Assign grid the return value of getGrid() method in Grid
        saved = ""; // Stores all of the Cell's states on the grid

        // Iterate through grid & add Cell states to saved String
        for (int row = 0; row < gd.GRID_SIZE; row++) {
            for (int col = 0; col < gd.GRID_SIZE; col++) {
                int state = grid[row][col].getState(); // Assign state the current Cell's state
                saved += Integer.toString(state); // Add Cell state to saved String
            }
        }
        saveFile(); // Call saveFile() method
    }

    /**
     * Writes the saved String populated by the saveGrid() method
     * to file via JFileChooser
     */
    public void saveFile() {
        // Save to File
        JFileChooser fc = new JFileChooser(); // Create instance of JFileChooser
        fc.setDialogTitle("Save Grid");
        int saveFile = fc.showSaveDialog(null);
        if (saveFile == JFileChooser.APPROVE_OPTION) {
            File file = new File(fc.getSelectedFile() + ".grid");
            if (file == null) {
                return;
            }

            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.write(saved); // Write String to File
                bw.close(); // Close BufferedWriter
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Calls createGrid() in Grid if grid not created and
     * populates the grid according to the file loaded
     * @param panel, primary panel the grid is displayed on
     * @param add, true if adding formations
     */
    public void loadGrid(JPanel panel, boolean add) {
        int loadCount = 0; // Stores the index position of String
        loadFile(); // Call loadFile() methods

        // Grid doesn't exist, create it
        if (!gd.gridExists()) {
            gd.createGrid(panel);
        }

        grid = gd.getGrid(); // Assign grid the return value of getGrid() method in Grid

        // Iterate through grid & load Cell's states from file to grid
        for (int row = 0; row < gd.GRID_SIZE; row++) {
            for (int col = 0; col < gd.GRID_SIZE; col++) {
                // If state is 1 within String, set Cell alive
                if (loaded.charAt(loadCount) == '1') {
                    grid[row][col].alive();
                } else if (grid[row][col].getState() == 1 && !add) {
                    grid[row][col].dead();
                }
                loadCount++;
            }
        }
    }

    /**
     * Loads a .grid file via the JFileChooser and populates
     * the loaded String with the files contents
     */
    public void loadFile() {
        // Load from File
        JFileChooser fc = new JFileChooser(); // Create instance of JFileChooser
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Grid Files", "grid");
        fc.setFileFilter(filter);
        fc.setDialogTitle("Load Grid");
        int returnFile = fc.showOpenDialog(null);
        if (returnFile == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                loaded = br.readLine(); // Read file to loaded String
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }

}