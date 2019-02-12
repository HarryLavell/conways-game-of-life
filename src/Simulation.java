/**
 * Conway's Game Of Life (7.5HD Improved Custom Program)
 * Controls the main game loop and the number of generations to
 * be processed per second of real time.
 * 
 * @author Harry Lavell
 * @version October 2018
 */
public class Simulation {
    private Grid grid = new Grid(); // Creates instance of Grid
    private Thread thread = new SimThread(); // Creates Thread object
    private final int DELAY = 1000; // 1 Second delay (1000ms)
    private static boolean running = false; // Current state of the simulation
    private static int generationSpeed; // Number of generations to cycle through per second
    private static int generation; // Current generation

    /**
     * Gets the running state of the simulation
     * @return the current state of the simulation
     */
    public boolean getState() {
        return running;
    }

    /**
     * Starts the simulation by starting the Thread that
     * the simulation runs on
     * @param genPerSec, the number of generation to update per second of real time
     */
    public synchronized void start(int genPerSec) {
        running = true;
        generationSpeed = genPerSec;
        thread.start();
    }

    /**
     * Stops the simulation via termination of the Thread
     * and handles exception
     */
    public synchronized void stop() {
        running = false;
        
        try {
            thread.join();
            Thread.sleep(DELAY); // Allow other threads to terminate
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    /**
     * The main game loop that calls the updateGrid() method in Grid
     * and sleeps the thread according to the number of generations per
     * second
     */
    private synchronized void update() {
        System.out.println(thread.getName());
        
        while (running) {

            System.out.println("Generation: "+generation + " and current time is " + System.currentTimeMillis());
            generation += 1; // Increment generation count

            grid.updateGrid(); // Call updateGrid() method in Grid

            // Sleeps the Thread for a designated number of millisecond to allow for updates
            try {
                Thread.sleep(DELAY / generationSpeed);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        
    }

    /**
     * Thread subclass to instantiate thread objects
     * that calls the update() method once a new thread
     * has been created
     */
    public class SimThread extends Thread {
        @Override
        /**
         * Thread's primary method upon starting, calls
         * update() method in Simulation
         */
        public void run() {
            update();
        }
    }
    
}