/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.actions;

import auctions.admin.AuctionsClientAdmin;
import auctions.interfaces.AuctionsIPrintable;
import controller.Controller;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import vista.ClientGUI;

/**
 *
 * @author alexander
 */
public class ActionWindowListener implements WindowListener {

    private final AuctionsClientAdmin admin;
    private final ClientGUI clientGUI;
    private final Controller controller;
    private final AuctionsIPrintable printer;

    public ActionWindowListener(AuctionsClientAdmin admin, ClientGUI clientGUI, Controller controller, AuctionsIPrintable printer) {
        this.admin = admin;
        this.clientGUI = clientGUI;
        this.controller = controller;
        this.printer = printer;
    }

    @Override
    public void windowOpened(WindowEvent e) {
        
    }

    @Override
    public void windowClosing(WindowEvent e) {
        admin.closeConnection();
        printer.print("Cerrando el programa...");
        e.getWindow().dispose();
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        
    }

    @Override
    public void windowIconified(WindowEvent e) {
        
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        
    }

    @Override
    public void windowActivated(WindowEvent e) {
        
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        
    }
    
}
