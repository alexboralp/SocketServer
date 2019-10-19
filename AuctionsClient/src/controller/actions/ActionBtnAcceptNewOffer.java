/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.actions;

import auctions.admin.AuctionsClientAdmin;
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
public class ActionBtnAcceptNewOffer implements ActionListener {

    private final AuctionsClientAdmin admin;
    private final ClientGUI clientGUI;
    private final Controller controller;
    private final AuctionsIPrintable printer;

    public ActionBtnAcceptNewOffer(AuctionsClientAdmin admin, ClientGUI clientGUI, Controller controller, AuctionsIPrintable printer) {
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
            
            double newPrice = (double)clientGUI.spnYourAuctionsNextPrice.getValue();
            String newBidder = auction.getNewBidderId();
            
            auction.setNextPrice(auction.getNextPrice());
            auction.setBidderId(auction.getNewBidderId());
            auction.setNextPrice(newPrice);
            auction.setNewBidder(null);
            admin.getAuctions().add(auction);
            
            admin.acceptOffer(selectedValue, newBidder, newPrice);
            controller.updateGUIYourAuctionSelectedInfo(auction);
        } else {
            printer.print("Debe seleccionar alguna subasta.");
        }
    }
    
}
