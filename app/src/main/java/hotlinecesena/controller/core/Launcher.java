package hotlinecesena.controller.core;

import java.io.IOException;

/**
 * The entry point of application
 * @author Federico
 */
public class Launcher {

    public Launcher() {
    
    }
    /**
     * Main method of application. 
     * @param args parameters
     * @throws IOException 
     */
    public static void main(final String[] args) throws IOException {
        GameLoader.main(args);
    }
}