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
import vista.ClientGUI;

/**
 *
 * @author alexander
 */
public class ActionBtnFollowAuction implements ActionListener {

    private final Admin admin;
    private final ClientGUI clientGUI;
    private final Controller controller;
    private final AuctionsIPrintable printer;

    public ActionBtnFollowAuction(Admin admin, ClientGUI clientGUI, Controller controller, AuctionsIPrintable printer) {
        this.admin = admin;
        this.clientGUI = clientGUI;
        this.controller = controller;
        this.printer = printer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int pos = clientGUI.lstAvailableAuctions.getSelectedIndex();
        if (pos != -1) {
            String AuctionName = clientGUI.lstAvailableAuctions.getSelectedValue();

            printer.print("Solicitando seguir la subasta " + AuctionName + ".");
            admin.followAuction(AuctionName);
        } else {
            printer.printError("Se debe escoger una subasta.");
        }
    }
    
}
