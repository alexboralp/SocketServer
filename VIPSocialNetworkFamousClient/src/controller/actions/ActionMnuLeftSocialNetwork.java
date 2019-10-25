/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.actions;

import controller.Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vip.admin.VIPClientFamousAdmin;
import vip.interfaces.VIPIPrintable;
import vista.ClientGUI;

/**
 *
 * @author aborbon
 */
public class ActionMnuLeftSocialNetwork implements ActionListener {

    private final VIPClientFamousAdmin admin;
    private final ClientGUI clientGUI;
    private final Controller controller;
    private final VIPIPrintable printer;

    public ActionMnuLeftSocialNetwork(VIPClientFamousAdmin admin, ClientGUI clientGUI, Controller controller, VIPIPrintable printer) {
        this.admin = admin;
        this.clientGUI = clientGUI;
        this.controller = controller;
        this.printer = printer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        printer.print("Dejando la red social...");
        admin.leftSocialNetwork();
    }
    
}
