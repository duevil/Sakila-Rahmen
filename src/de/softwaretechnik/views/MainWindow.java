package de.softwaretechnik.views;

import de.softwaretechnik.controller.MainWindowController;
import de.softwaretechnik.program.Program;

import java.awt.*;

public class MainWindow extends Frame {
	
	/*
		Main Window als Singleton
		Nur UI/GUI spezifische Implementierungen
	 */

	// ------------------------------------------------------------------------------------------------
	// Singleton
	private static MainWindow window = new MainWindow();
	public static MainWindow getInstance(){
		return window;
	}

	// GUI Elements
	private MenuBar _menuBar;


	private MainWindow() {
		setTitle(Program.APP_TITLE + " [" + Program.APP_V + "]");
		setSize(400,400);

		createGUI();
	}

	public void createGUI(){
		setMenuBar(createMainMenu());
	}


	private MenuBar createMainMenu(){
		MenuBar menuBar = new MenuBar();
		Menu menuProgram = new Menu("Program");
		menuProgram.add(new MenuItem("Beenden") );

		menuBar.add(menuProgram);
		return menuBar;
	}

	
}
