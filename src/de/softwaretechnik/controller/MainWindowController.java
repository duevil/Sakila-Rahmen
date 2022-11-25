package de.softwaretechnik.controller;

import de.softwaretechnik.interfaces.IMainListener;
import de.softwaretechnik.models.Model;
import de.softwaretechnik.views.MainWindow;

import java.awt.event.*;

public class MainWindowController extends WindowAdapter implements IMainListener {

    private MainWindow window;
    private Model model;

    public MainWindowController(MainWindow mw, Model m) {
        window = mw;
        model = m;

        window.addWindowListener(this);


    }

    // ---------------------------------------------------------------------------
    // Controller Methods
    public void startProgram(){
        window.setVisible(true);
    }

    public void exitProgram(){
        window.dispose();
        System.exit(0);
    }

    // ---------------------------------------------------------------------------
    // Events
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    @Override
    public void textValueChanged(TextEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {
         window.createGUI();
    }




    @Override
    public void windowClosing(WindowEvent e){
      exitProgram();
    }
    // ---------------------------------------------------------------------------

}
