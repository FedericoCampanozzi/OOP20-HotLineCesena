package hotlinecesena.controller.core;

import java.io.IOException;

import hotlinecesena.controller.GameController;

public class Launcher {

	private Launcher() {
	}
	
	/**
     * Main method of application.
     * 
     * @param args parameters
	 * @throws IOException 
     */
    public static void main(final String[] args) throws IOException {
        GameController.main(args);
    }
}
