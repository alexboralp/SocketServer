/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.actions;

import auctions.admin.AuctionsClientAdmin;
import auctions.interfaces.AuctionsIPrintable;
import controller.Controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import vista.ClientGUI;

/**
 *
 * @author alexander
 */
public class ActionBtnUnfollowAuction implements ActionListener {

    private final AuctionsClientAdmin admin;
    private final ClientGUI clientGUI;
    private final Controller controller;
    private final AuctionsIPrintable printer;

    public ActionBtnUnfollowAuction(AuctionsClientAdmin admin, ClientGUI clientGUI, Controller controller, AuctionsIPrintable printer) {
        this.admin = admin;
        this.clientGUI = clientGUI;
        this.controller = controller;
        this.printer = printer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int pos = clientGUI.lstFollowedAuctions.getSelectedIndex();
        if (pos != -1) {
            String AuctionName = clientGUI.lstFollowedAuctions.getSelectedValue();
            /*DefaultListModel<String> lstFollowedAuctionsModel = (DefaultListModel<String>)clientGUI.lstFollowedAuctions.getModel();
            DefaultListModel<String> lstAvailableAuctionsModel = (DefaultListModel<String>)clientGUI.lstAvailableAuctions.getModel();*/

            System.out.println("Solicitud de dejar de seguir la subasta" + AuctionName + ".");
            admin.unfollowAuction(AuctionName);
            /*if (pos >= 0 && pos < lstFollowedAuctionsModel.getSize()){
                lstFollowedAuctionsModel.addElement(lstAvailableAuctionsModel.remove(pos));
            }*/
        } else {
            printer.printError("Se debe escoger una subasta.");
        }
    }
    
}
