/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.actions;

import admin.Admin;
import auctions.interfaces.AuctionsIPrintable;
import controller.Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import ooserver.commoninterfaces.OOIPrintable;
import vista.ClientGUI;

/**
 *
 * @author alexander
 */
public class ActionBtnUpdate implements ActionListener {

    private final Admin admin;
    private final ClientGUI clientGUI;
    private final Controller controller;
    private final AuctionsIPrintable printer;

    public ActionBtnUpdate(Admin admin, ClientGUI clientGUI, Controller controller, AuctionsIPrintable printer) {
        this.admin = admin;
        this.clientGUI = clientGUI;
        this.controller = controller;
        this.printer = printer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ((DefaultListModel<String>)clientGUI.lstAvailableAuctions.getModel()).clear();
        ((DefaultListModel<String>)clientGUI.lstFollowedAuctions.getModel()).clear();
        ((DefaultListModel<String>)clientGUI.lstYourAuctions.getModel()).clear();
        admin.sendAllAuctions();
    }
    
}
