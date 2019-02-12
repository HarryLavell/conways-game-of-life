import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Conway's Game Of Life (7.5HD Improved Custom Program)
 * Cell data-type populated into grid. Allows for regulation
 * of state, positions, colour, and mouse interaction via JPanel.
 * 
 * @author Harry Lavell
 * @version October 2018
 */
public class Cell extends MouseAdapter {
    private int state; // Cell's State (0 = Dead, 1 = Alive)
    private JPanel panel; // Cell's JPanel
    public int row; // Row position in Grid
    public int col; // Column position in Grid

    /**
     * Class constructor that assigns the parsed values to the current Cell
     * and adds a MouseListener to the Cell's JPanel
     * @param state, the alive or dead state of the current Cell
     * @param row, the row the current Cell is located on
     * @param col, the column the current Cell is located on
     * @param panel, the current Cell's JPanel
     */
    public Cell(int state, int row, int col, JPanel panel) { 
        this.state = state;
        this.row = row;
        this.col = col;
        this.panel = panel;
        panel.addMouseListener(this); // Add MouseListener to JPanel
    }

    /**
     * Gets the state of the current Cell
     * @return, the state of the current Cell
     */
    public int getState() {
        return state;
    }

    /**
     * Sets the state of the current Cell and changes
     * the cells JPanel colour based on the state
     * @param state, the alive or dead state of the current Cell
     */
    public void setState(int state) {
        this.state = state;
        
        // Change Cell's colour depending on state
        if (state == 1) {
            this.panel.setBackground(Color.BLACK);
        } else if (state == 0) {
            this.panel.setBackground(Color.WHITE);
        }
        
    }

    /**
     * Gets the JPanel of the current Cell
     * @return, the JPanel for the current Cell
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Sets the current Cell's state to alive
     */
    public void alive() {
        setState(1); // Change state to alive
    }

    /**
     * Sets the current Cell's state to dead
     */
    public void dead() {
        setState(0); // Change state to dead
    }

    /**
     * If Cell is alive, return true
     * @return true, if state equals 1
     */
    public boolean isAlive() {
        return state == 1;
    }

    /**
     * Add Cell's JPanel to the parsed JPanel
     */
    public void addToPanel(JPanel panel) {
        panel.add(this.panel);
    }

    /**
     * Change the state of the Cell to its opposite via a
     * mouse press on its JPanel located on the grid
     * @param e, MouseEvent that allow for user interaction with Cells via JPanel
     */
    public void mousePressed(MouseEvent e) {
        if (state == 0) { // If Cell dead, make alive
            alive();
        } else if (state == 1) { // If Cell alive, make dead
            dead();
        }
    }

}