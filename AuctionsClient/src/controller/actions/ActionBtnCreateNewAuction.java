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
import java.util.Date;
import javax.swing.Icon;
import vista.ClientGUI;

/**
 *
 * @author alexander
 */
public class ActionBtnCreateNewAuction implements ActionListener {

    private final AuctionsClientAdmin admin;
    private final ClientGUI clientGUI;
    private final Controller controller;
    private final AuctionsIPrintable printer;

    public ActionBtnCreateNewAuction(AuctionsClientAdmin admin, ClientGUI clientGUI, Controller controller, AuctionsIPrintable printer) {
        this.admin = admin;
        this.clientGUI = clientGUI;
        this.controller = controller;
        this.printer = printer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            if (!"".equals(clientGUI.txtNewAuctionid.getText()) && 
                    !"".equals(clientGUI.txtNewAuctionProductName.getText()) &&
                    !"".equals(clientGUI.txtNewAuctionDescription.getText()) &&
                    !"".equals(clientGUI.txtnewAuctionInitialPrice.getText())) {
                String AuctionName = clientGUI.txtNewAuctionid.getText();
                int dia = (int)clientGUI.spnNewAuctionDay.getValue();
                int mes = (int)clientGUI.spnnewAuctionMonth.getValue();
                int anno = (int)clientGUI.spnnewAuctionYear.getValue();
                int hora = (int)clientGUI.spnNewAuctionHour.getValue();
                int duracion = (int)clientGUI.spnNewAuctionDuration.getValue();
                String nombreProduct = clientGUI.txtNewAuctionProductName.getText();
                String descripcion = clientGUI.txtNewAuctionDescription.getText();
                double initialPrice = Double.parseDouble(clientGUI.txtnewAuctionInitialPrice.getText());
                Icon auctionImage = clientGUI.lblAuctionImage.getIcon();
                Icon productImage = clientGUI.lblProductImage.getIcon();
                
                Date fecha = new Date(anno, mes, dia, hora, 0);
                
                printer.print("Enviando la solicitud de agregar la nueva subasta");
                admin.addAuction(AuctionName, fecha, duracion, nombreProduct, descripcion, productImage, initialPrice, initialPrice, auctionImage);
            } else {
                printer.printError("Falta algún dato para la subasta.");
            }
        } catch(NumberFormatException ex) {
            printer.printError("Revise que todos los números sean válidos.");
        }
    }
    
}
