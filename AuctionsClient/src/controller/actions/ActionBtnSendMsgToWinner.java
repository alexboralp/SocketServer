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
public class ActionBtnSendMsgToWinner implements ActionListener {

    private final AuctionsClientAdmin admin;
    private final ClientGUI clientGUI;
    private final Controller controller;
    private final AuctionsIPrintable printer;

    public ActionBtnSendMsgToWinner(AuctionsClientAdmin admin, ClientGUI clientGUI, Controller controller, AuctionsIPrintable printer) {
        this.admin = admin;
        this.clientGUI = clientGUI;
        this.controller = controller;
        this.printer = printer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int pos = clientGUI.lstYourAuctions.getSelectedIndex();
        
        if (pos != -1) {
            Auction auction = admin.getAuctions().get(clientGUI.lstYourAuctions.getSelectedValue());
            if (auction != null) {
                if (!"".equals(auction.getBidderId())) {
                    String idAuction = auction.getId();
                    String winner = auction.getBidderId();
                    String message = clientGUI.txtMessageToWinner.getText();
                    clientGUI.txtMessageToWinner.setText("");
                    admin.auctionFinished(idAuction, winner, message);
                }
            }
        } else {
            printer.printError("No hay una subasta seleccionada.");
        }
    }
    
}
