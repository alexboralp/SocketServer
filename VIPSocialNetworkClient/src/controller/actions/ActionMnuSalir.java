/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.actions;

import vip.admin.VIPClientAdmin;
import controller.Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.ClientGUI;
import vip.interfaces.VIPIPrintable;

/**
 *
 * @author alexander
 */
public class ActionMnuSalir implements ActionListener {

    private final VIPClientAdmin admin;
    private final ClientGUI clientGUI;
    private final Controller controller;
    private final VIPIPrintable printer;

    public ActionMnuSalir(VIPClientAdmin admin, ClientGUI clientGUI, Controller controller, VIPIPrintable printer) {
        this.admin = admin;
        this.clientGUI = clientGUI;
        this.controller = controller;
        this.printer = printer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        admin.closeConnection();
        printer.print("Cerrando el programa...");
        System.exit(0);
    }
    
}
