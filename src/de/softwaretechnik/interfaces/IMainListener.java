package de.softwaretechnik.interfaces;

import java.awt.event.*;


public interface IMainListener extends ActionListener, ItemListener, TextListener {
    /*
            Interface Methoden definieren für alle Events des Programms
    */
    void startProgram();
    void exitProgram();

}
