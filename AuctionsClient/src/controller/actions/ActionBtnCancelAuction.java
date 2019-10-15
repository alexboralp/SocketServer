/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.actions;

import admin.Admin;
import auctions.interfaces.AuctionsIPrintable;
import auctions.objects.Auction;
import controller.Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.ClientGUI;

/**
 *
 * @author alexander
 */
public class ActionBtnCancelAuction implements ActionListener {

    Admin admin;
    ClientGUI clientGUI;
    Controller controller;
    AuctionsIPrintable printer;

    public ActionBtnCancelAuction(Admin admin, ClientGUI clientGUI, Controller controller, AuctionsIPrintable printer) {
        this.admin = admin;
        this.clientGUI = clientGUI;
        this.controller = controller;
        this.printer = printer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int pos = clientGUI.lstYourAuctions.getSelectedIndex();
        
        if (pos != -1) {
            String selectedValue = clientGUI.lstYourAuctions.getSelectedValue();
            
            Auction auction = admin.getAuctions().get(selectedValue);
            
            auction.setState(Auction.STATE.CANCELED);
            
            admin.getAuctions().add(auction);
            
            admin.cancelAuction(selectedValue);
            controller.actualizarTextos(auction);
        } else {
            printer.print("Debe seleccionar alguna subasta.");
        }
    }
    
}
