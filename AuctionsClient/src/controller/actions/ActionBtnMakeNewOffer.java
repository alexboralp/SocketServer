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
public class ActionBtnMakeNewOffer implements ActionListener {

    private final AuctionsClientAdmin admin;
    private final ClientGUI clientGUI;
    private final Controller controller;
    private final AuctionsIPrintable printer;

    public ActionBtnMakeNewOffer(AuctionsClientAdmin admin, ClientGUI clientGUI, Controller controller, AuctionsIPrintable printer) {
        this.admin = admin;
        this.clientGUI = clientGUI;
        this.controller = controller;
        this.printer = printer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int escogido = clientGUI.lstFollowedAuctions.getSelectedIndex();
        if (escogido != -1) {
            Auction auction = admin.getFollowedAuctions().get(clientGUI.lstFollowedAuctions.getSelectedValue());
            if (auction.getState() == Auction.STATE.IN_PROGRESS) {
                admin.newOffer(auction.getId(), auction.getNextPrice());
            }
        } else {
            printer.printError("Se debe escoger una subasta para ofertar.");
        }
    }
    
}
