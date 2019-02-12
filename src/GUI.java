import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Conway's Game Of Life (7.5HD Improved Custom Program)
 * Creates and controls program gui and allows for user interaction.
 * 
 * @author Harry Lavell
 * @version October 2018
 */
public class GUI extends JFrame implements ActionListener {
    private Grid grid = new Grid(); // Create instance of Grid
    
    // Main Window
    private JFrame mainFrame = new JFrame(); // Window Frame
    private JPanel mainPanel = new JPanel(); // Window Panel
    
    // Panels
    private JPanel topPanel = new JPanel(); // Top Sector Panel
    private JPanel optionsLeft = new JPanel(); // Left Buttons Panel
    private JButton bNew = new JButton();
    private JButton bSave = new JButton();
    private JButton bLoad = new JButton();
    private JButton bAdd = new JButton();
    private JPanel optionsRight = new JPanel(); // Right Buttons Panel
    private JButton bRunSim = new JButton();
    private JLabel genPerSec = new JLabel("", SwingConstants.RIGHT);
    private JTextField perSecond = new JTextField();
    
    private JPanel middlePanel = new JPanel();
    private JScrollPane scrollPane = new JScrollPane(middlePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); // ScrollPane
    
    private JPanel bottomPanel = new JPanel(); // Bottom Sector Panel
    private JPanel statusBarLeft = new JPanel(); // Left Labels Panel
    private JLabel generationCount = new JLabel("", SwingConstants.LEFT);
    private JPanel statusBarRight = new JPanel(); // Right Labels Panel
    private JLabel currentFile = new JLabel("", SwingConstants.RIGHT);

    /**
     * Class constructor that initializes the graphics user
     * interface via a call to the init() method
     * @param title, the title of the window
     * @param width, the default width of the window
     * @param height, the defualt height of the window
     */
    public GUI(String title, int width, int height) {
        init(title, width, height); // Call init(String,int,int) method
    }

    /**
     * Create graphical user interface using the paramteres parsed
     * @param title, the title of the window
     * @param width, the default width of the window
     * @param height, the defualt height of the window
     */
    private void init(String title, int width, int height) {
        // JFrame
        mainFrame = new JFrame(title); // Create instance of JFrame
        mainFrame.setSize(width,height); // Size
        mainFrame.setLocationRelativeTo(null); // Center Window to Screen
        mainFrame.setResizable(true); // Resizable
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation
        mainFrame.getContentPane().add(mainPanel); // Add main panel to frame
        
        // Set Panel Layouts
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        // Top Section's Panels
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        optionsLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
        optionsRight.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        // Middle Section's Panels (Grid Panel)
        middlePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        // Bottom Section's Panels
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        statusBarLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
        statusBarRight.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        // Top Section
        // Left Panel
        bNew.setText("New Grid");
        optionsLeft.add(bNew); // Add Component to Panel
        bNew.addActionListener(this); // Action Listener
        
        bSave.setText("Save");
        optionsLeft.add(bSave); // Add Component to Panel
        bSave.addActionListener(this); // Action Listener

        bLoad.setText("Load");
        optionsLeft.add(bLoad); // Add Component to Panel
        bLoad.addActionListener(this); // Action Listener

        bAdd.setText("Add to Grid");
        optionsLeft.add(bAdd); // Add Component to Panel
        bAdd.addActionListener(this); // Action Listener
        
        // Right Panel
        genPerSec.setText("Generations Per Second:");
        genPerSec.setOpaque(true);
        optionsRight.add(genPerSec); // Add Component to Panel
        
        perSecond = new JTextField(6);
        optionsRight.add(perSecond); // Add Component to Panel
        
        bRunSim = new JButton("Run Simulation");
        optionsRight.add(bRunSim); // Add Component to Panel
        bRunSim.addActionListener(this); // Action Listener
        
        // Bottom Section
        // Left Panel
        generationCount.setText("|"); // Text to display & align left
        generationCount.setOpaque(true);
        statusBarLeft.add(generationCount); // Add Component to Panel
        
        // Right Panel
        currentFile.setText("Current File: No File Found"); // Text to display & align right
        currentFile.setOpaque(true);
        //statusBarRight.add(currentFile); // Add Component to Panel
        
        // Add Components to Panels
        topPanel.add(optionsLeft);
        topPanel.add(optionsRight);
        
        scrollPane.setPreferredSize(new Dimension(1500, 1000)); // Set dimensions of ScrollPane
        scrollPane.setViewportView(middlePanel); // Set ViewPort to middlePanel
        
        bottomPanel.add(statusBarLeft);
        bottomPanel.add(statusBarRight);
        
        // Add Panels to Main Panel
        mainPanel.add(topPanel);
        mainPanel.add(scrollPane); // ScrollPane to mainPanel (Grid Panel)
        mainPanel.add(bottomPanel);
        
        mainFrame.setVisible(true); // JFrame Visibility
    }

    /**
     * ActionListener to enable button presses
     * @param e, ActionListener for the buttons of the gui
     */
    public void actionPerformed(ActionEvent e) {

        // 'New' Button Pressed
        if (e.getSource() == bNew) {
            grid.createGrid(middlePanel); // Call createGrid(int,int,JPanel) method in Grid
        }
        
        // 'Save' Button Pressed
        if (e.getSource() == bSave) {
            try {
                FileManager fm = new FileManager(); // Create instance of FileManager
                fm.saveGrid(); // Call saveGrid() method in FileManager
            } catch (Exception saveExc){
                saveExc.printStackTrace();
            }
            
        }

        // 'Load' Button Pressed
        if (e.getSource() == bLoad) {
            try {
                FileManager fm = new FileManager(); // Create instance of FileManager
                fm.loadGrid(middlePanel, false); // Call loadFile() method in FileManager
            } catch (Exception loadExc){
                loadExc.printStackTrace();
            }

        }

        // 'Load' Button Pressed
        if (e.getSource() == bAdd) {
            try {
                FileManager fm = new FileManager(); // Create instance of FileManager
                fm.loadGrid(middlePanel, true); // Call loadFile() method in FileManager
            } catch (Exception loadExc){
                loadExc.printStackTrace();
            }

        }
        
        // 'Run/Stop Simulation' Button Pressed
        if (e.getSource() == bRunSim) {
            int generationsPerSecond = Integer.parseInt(perSecond.getText()); // Parse text from textField to variable
            Simulation sim = new Simulation(); // Create instance of Simulation

            if (sim.getState() == false) { // Not running
                sim.start(generationsPerSecond); // Call start() method in Simulation
                bRunSim.setText("Stop Simulation"); // Change Button Text
            } else { // Running
                sim.stop(); // Call stop() method in Simulation
                bRunSim.setText("Run Simulation");
            }
            
        }
        
    }

}