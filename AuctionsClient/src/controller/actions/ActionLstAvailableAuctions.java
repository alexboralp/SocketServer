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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import vista.ClientGUI;

/**
 *
 * @author alexander
 */
public class ActionLstAvailableAuctions implements ListSelectionListener, MouseListener {

    private final AuctionsClientAdmin admin;
    private final ClientGUI clientGUI;
    private final Controller controller;
    private final AuctionsIPrintable printer;

    public ActionLstAvailableAuctions(AuctionsClientAdmin admin, ClientGUI clientGUI, Controller controller, AuctionsIPrintable printer) {
        this.admin = admin;
        this.clientGUI = clientGUI;
        this.controller = controller;
        this.printer = printer;
    }
    
    private void process() {
        printer.print("ActionLstAvailableAuctions: " + "Action.");
        String selectedValue = clientGUI.lstAvailableAuctions.getSelectedValue();
        printer.print("ActionLstAvailableAuctions: " + "Selected value: " + selectedValue);
        if (clientGUI.lstAvailableAuctions.getSelectedIndex() != -1) {
            clientGUI.btnFollowAuction.setEnabled(true);
            Auction auction = admin.getAuctions().get(selectedValue);
            
            controller.updateGUIAvailableAndFollowedAuctionSelectedInfo(auction);
        } else {
            clientGUI.btnFollowAuction.setEnabled(false);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        process();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        process();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
