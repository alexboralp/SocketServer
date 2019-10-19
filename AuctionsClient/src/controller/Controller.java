/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import auctions.admin.AuctionsClientAdmin;
import auctions.interfaces.AuctionsIObserver;
import auctions.objects.Auction;
import auctions.interfaces.AuctionsIPrintable;
import auctions.msgs.AuctionsAtributeChanged;
import controller.actions.ActionBtnAcceptNewOffer;
import controller.actions.ActionBtnCancelAuction;
import controller.actions.ActionBtnCreateNewAuction;
import controller.actions.ActionBtnFollowAuction;
import controller.actions.ActionBtnMakeNewOffer;
import controller.actions.ActionBtnSendMsgToWinner;
import controller.actions.ActionBtnUnfollowAuction;
import controller.actions.ActionLstAvailableAuctions;
import controller.actions.ActionLstFollowedAuctions;
import controller.actions.ActionLstYourAuctions;
import controller.actions.ActionMnuSalir;
import controller.actions.ActionWindowListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.DefaultListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import vista.ClientGUI;

/**
 *
 * @author alexander
 */
public class Controller implements AuctionsIObserver {
    
    private ClientGUI clientGUI;
    private final AuctionsClientAdmin admin;
    private final AuctionsIPrintable printer;
    
    DefaultListModel<String> lstYourAuctionsModel;
    DefaultListModel<String> lstFollowedAuctionsModel;
    DefaultListModel<String> lstAvailableAuctionsModel;

    public Controller(ClientGUI clientGUI, AuctionsClientAdmin admin, AuctionsIPrintable printer) {
        this.clientGUI = clientGUI;
        this.admin = admin;
        this.printer = printer;
        
        _init_();
    }
    
    private void _init_() {
        
        admin.addObserver(this);
    
        clientGUI.lstYourAuctions.removeAll();
        lstYourAuctionsModel = new DefaultListModel<>();
        clientGUI.lstYourAuctions.setModel(lstYourAuctionsModel);
        
        clientGUI.lstFollowedAuctions.removeAll();
        clientGUI.lstAvailableAuctions.removeAll();
        lstFollowedAuctionsModel = new DefaultListModel<>();
        clientGUI.lstFollowedAuctions.setModel(lstFollowedAuctionsModel);
        lstAvailableAuctionsModel = new DefaultListModel<>();
        clientGUI.lstAvailableAuctions.setModel(lstAvailableAuctionsModel);
            
        SpinnerModel spnSiguientePrecioModel = new SpinnerNumberModel(0.0,0.0,Double.MAX_VALUE, 0.1);
        clientGUI.spnYourAuctionsNextPrice.setModel(spnSiguientePrecioModel);
        
        clientGUI.btnAcceptNewOffer.addActionListener(new ActionBtnAcceptNewOffer(admin, clientGUI, this, printer));
        clientGUI.btnCancelAuction.addActionListener(new ActionBtnCancelAuction(admin, clientGUI, this, printer));
        clientGUI.btnCreateNewAuction.addActionListener(new ActionBtnCreateNewAuction(admin, clientGUI, this, printer));
        clientGUI.btnFollowAuction.addActionListener(new ActionBtnFollowAuction(admin, clientGUI, this, printer));
        clientGUI.btnMakeNewOffer.addActionListener(new ActionBtnMakeNewOffer(admin, clientGUI, this, printer));
        clientGUI.btnSendMsgToWinner.addActionListener(new ActionBtnSendMsgToWinner(admin, clientGUI, this, printer));
        clientGUI.btnUnfollowAuction.addActionListener(new ActionBtnUnfollowAuction(admin, clientGUI, this, printer));
        clientGUI.lstAvailableAuctions.addListSelectionListener(new ActionLstAvailableAuctions(admin, clientGUI, this, printer));
        //clientGUI.lstAvailableAuctions.addListSelectionListener(new ActionLstAvailableAuctions(admin, clientGUI, this, printer));
        clientGUI.lstFollowedAuctions.addListSelectionListener(new ActionLstFollowedAuctions(admin, clientGUI, this, printer));
        clientGUI.lstYourAuctions.addListSelectionListener(new ActionLstYourAuctions(admin, clientGUI, this, printer));
        clientGUI.mnuSalir.addActionListener(new ActionMnuSalir(admin, clientGUI, this, printer));
        clientGUI.addWindowListener(new ActionWindowListener(admin, clientGUI, this, printer));
        
        Calendar calendar = new GregorianCalendar();
        clientGUI.spnNewAuctionDay.setValue(calendar.get(Calendar.DAY_OF_MONTH));
        clientGUI.spnNewAuctionMonth.setValue(calendar.get(Calendar.MONTH));
        clientGUI.spnNewAuctionYear.setValue(calendar.get(Calendar.YEAR));
        clientGUI.spnNewAuctionHour.setValue(calendar.get(Calendar.HOUR_OF_DAY));
        clientGUI.spnNewAuctionMinutes.setValue(calendar.get(Calendar.MINUTE));
        
        admin.setClientName(clientGUI.getClientName());
    }

    public ClientGUI getClientGUI() {
        return clientGUI;
    }

    public void setClientGUI(ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
    }
    
    public void updateGUIAvailableAuctionsList() {
        lstAvailableAuctionsModel.clear();
        for (Auction auction : admin.getAuctions().getAuctions().values()) {
            lstAvailableAuctionsModel.addElement(auction.getId());
        }
    }
    
    public void updateGUIFollowedAuctionsList() {
        lstFollowedAuctionsModel.clear();
        for (Auction auction : admin.getFollowedAuctions().getAuctions().values()) {
            lstFollowedAuctionsModel.addElement(auction.getId());
        }
    }
    
    public void updateGUIYourAuctionsList() {
        lstYourAuctionsModel.clear();
        for (Auction auction : admin.getAuctions().getAuctions().values()) {
            if (auction.getAuctioneerId().equals(admin.getMyId())) {
                lstYourAuctionsModel.addElement(auction.getId());
            }
        }
    }
    
    public void updateGUIYourAuctionSelectedInfo (Auction auction) {
        clientGUI.txtYourAuctionId.setText(auction.getId());
        clientGUI.txtYourAuctionStatus.setText(auction.getState().toString());
        clientGUI.txtYourAuctionBestOffer.setText(Double.toString(auction.getActualPrice()));
        clientGUI.txtYourAuctionsNewOffer.setText(Double.toString(auction.getNextPrice()));
        clientGUI.txtYourAuctionsIdProduct.setText(auction.getProduct().getId());
        clientGUI.spnYourAuctionsNextPrice.setValue(auction.getNextPrice());
        clientGUI.txtYourAuctionsDescription.setText(auction.getProduct().getDescription());
        if (!"".equals(auction.getBidderId())) {
            clientGUI.txtYourAuctionsUserBestOffer.setText(auction.getBidderId());
        } else {
            clientGUI.txtYourAuctionsUserBestOffer.setText("");
        }
        if ("".equals(auction.getNewBidderId())) {
            clientGUI.txtYourAuctionsUserNewOffer.setText(auction.getNewBidderId());
        } else {
            clientGUI.txtYourAuctionsUserNewOffer.setText("");
        }
        clientGUI.lblYourAuctionsAuctionImage.setIcon(auction.getImage());
        clientGUI.lblYourAuctionsProductImage.setIcon(auction.getProduct().getImage());
    }
    
    public void updateGUIAvailableAndFollowedAuctionSelectedInfo (Auction auction) {
        clientGUI.txtProductId.setText(auction.getProduct().getId());
        clientGUI.txtState.setText(auction.getState().toString());
        clientGUI.txtActualPrice.setText(Double.toString(auction.getActualPrice()));
        clientGUI.txtNextPrice.setText(Double.toString(auction.getNextPrice()));
        clientGUI.lblAuctionImage.setIcon(auction.getImage());
        clientGUI.lblProductImage.setIcon(auction.getProduct().getImage());
    }
    
    public void GUIYourAuctionSelected() {
        String subasta = clientGUI.lstFollowedAuctions.getSelectedValue();
        if (admin.getAuctions().containsKey(subasta)) {
            Auction auction = admin.getAuctions().get(subasta);
            updateGUIYourAuctionSelectedInfo(auction);
        }
    }
    
    public void newOffer(String auctionId, String bidderId, double nextPrice) {
        Auction auction = admin.getAuctions().get(auctionId);
        if (nextPrice == auction.getNextPrice() && !"".equals(auction.getNewBidderId())) {
            auction.setNewBidder(bidderId);
        }
    }
    
    public void offerAccepted(String auctionId, String bidderId, double newPrice) {
        Auction auction = admin.getAuctions().get(auctionId);
        auction.setBidderId(bidderId);
        auction.setActualPrice(newPrice);
    }
    
    public void deleteAllAuctions() {
        lstAvailableAuctionsModel.clear();
        lstYourAuctionsModel.clear();
    }
    
    public void addAuction(Auction auction) {
        admin.getAuctions().add(auction);
        
        if (admin.getClient().getId().equals(auction.getAuctioneerId())) {
            lstYourAuctionsModel.addElement(auction.getId());
        } else {
            lstAvailableAuctionsModel.addElement(auction.getId());
        }
    }
    
    public void auctionFinished(String auctionId) {
        admin.getAuctions().get(auctionId).setState(Auction.STATE.FINISHED);
    }

    public void setId(String id) {
        admin.setClientId(id);
    }

    @Override
    public void update(Object message) {
        if (message instanceof AuctionsAtributeChanged) {
            AuctionsAtributeChanged msg = (AuctionsAtributeChanged) message;
            if("new auction".equals(msg.getName())) {
                addAuction((Auction)msg.getObject());
            }
        }
    }
    
    
    
}
