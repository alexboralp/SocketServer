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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import ooserver.commoninterfaces.OOIPrintable;
import vista.ClientGUI;

/**
 *
 * @author alexander
 */
public class ActionLstYourAuctions implements ListSelectionListener {

    private final Admin admin;
    private final ClientGUI clientGUI;
    private final Controller controller;
    private final AuctionsIPrintable printer;

    public ActionLstYourAuctions(Admin admin, ClientGUI clientGUI, Controller controller, AuctionsIPrintable printer) {
        this.admin = admin;
        this.clientGUI = clientGUI;
        this.controller = controller;
        this.printer = printer;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        String selectedValue = clientGUI.lstYourAuctions.getSelectedValue();
        
        if (selectedValue != null && !"".equals(selectedValue)) {
            Auction auction = admin.getAuctions().get(selectedValue);
            
            controller.actualizarTextos(auction);
        }
    }
    
}
