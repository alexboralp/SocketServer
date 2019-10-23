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
import auctions.msgs.AuctionsAtributeChangedFact;
import auctions.msgs.AuctionsMsgAcceptOffer;
import auctions.msgs.AuctionsMsgNewOffer;
import controller.actions.ActionBtnAcceptNewOffer;
import controller.actions.ActionBtnCancelAuction;
import controller.actions.ActionBtnCreateNewAuction;
import controller.actions.ActionBtnFollowAuction;
import controller.actions.ActionBtnMakeNewOffer;
import controller.actions.ActionBtnSendMsgToWinner;
import controller.actions.ActionBtnUnfollowAuction;
import controller.actions.ActionLblNewAuctionAuctionImage;
import controller.actions.ActionLblNewAuctionProductImage;
import controller.actions.ActionLstAvailableAuctions;
import controller.actions.ActionLstFollowedAuctions;
import controller.actions.ActionLstYourAuctions;
import controller.actions.ActionMnuSalir;
import controller.actions.ActionWindowListener;
import java.util.Calendar;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
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
        
        ActionLstAvailableAuctions lstAvailableAuctionsListener = new ActionLstAvailableAuctions(admin, clientGUI, this, printer);
        clientGUI.lstAvailableAuctions.addListSelectionListener(lstAvailableAuctionsListener);
        clientGUI.lstAvailableAuctions.addMouseListener(lstAvailableAuctionsListener);
        
        ActionLstFollowedAuctions lstFollowedAuctionsListener = new ActionLstFollowedAuctions(admin, clientGUI, this, printer);
        clientGUI.lstFollowedAuctions.addListSelectionListener(lstFollowedAuctionsListener);
        clientGUI.lstFollowedAuctions.addMouseListener(lstFollowedAuctionsListener);
        
        ActionLstYourAuctions lstYourActionsListener = new ActionLstYourAuctions(admin, clientGUI, this, printer);
        clientGUI.lstYourAuctions.addListSelectionListener(lstYourActionsListener);
        clientGUI.lstYourAuctions.addMouseListener(lstYourActionsListener);
        
        clientGUI.lblNewAuctionAuctionImage.addMouseListener(new ActionLblNewAuctionAuctionImage(admin, clientGUI, this, printer));
        clientGUI.lblNewAuctionProductImage.addMouseListener(new ActionLblNewAuctionProductImage(admin, clientGUI, this, printer));
        
        clientGUI.mnuSalir.addActionListener(new ActionMnuSalir(admin, clientGUI, this, printer));
        clientGUI.addWindowListener(new ActionWindowListener(admin, clientGUI, this, printer));
        
        Calendar calendar = Calendar.getInstance();
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
        if (!"".equals(auction.getNewBidderId())) {
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
        clientGUI.txtActualBidder.setText(auction.getBidderId());
        clientGUI.txtNextPrice.setText(Double.toString(auction.getNextPrice()));
        clientGUI.lblAuctionImage.setIcon(auction.getImage());
        clientGUI.lblProductImage.setIcon(auction.getProduct().getImage());
    }
    
    public void GUIYourAuctionSelected() {
        String subasta = clientGUI.lstFollowedAuctions.getSelectedValue();
        if (admin.getAuctions().containsKey(subasta)) {
            Auction auction = admin.getAuction(subasta);
            updateGUIYourAuctionSelectedInfo(auction);
        }
    }
    
    public void newOffer(String auctionId, String bidderId, double nextPrice) {
        Auction auction = admin.getAuction(auctionId);
        if (nextPrice == auction.getNextPrice() && !"".equals(auction.getNewBidderId())) {
            auction.setNewBidderId(bidderId);
        }
    }
    
    public void offerAccepted(String auctionId, String bidderId, double newPrice) {
        Auction auction = admin.getAuction(auctionId);
        auction.setBidderId(bidderId);
        auction.setActualPrice(newPrice);
    }
    
    public void deleteAllAuctions() {
        lstAvailableAuctionsModel.clear();
        lstYourAuctionsModel.clear();
    }
    
    public void deleteAuction(String auctionId) {
        if (lstFollowedAuctionsModel.contains(auctionId)) {
            lstFollowedAuctionsModel.removeElement(auctionId);
        }
        if (lstYourAuctionsModel.contains(auctionId)) {
            lstYourAuctionsModel.removeElement(auctionId);
        }
        if (lstAvailableAuctionsModel.contains(auctionId)) {
            lstAvailableAuctionsModel.removeElement(auctionId);
        }
    }
    
    public void addAuction(Auction auction) {
        if (admin.getClient().getId().equals(auction.getAuctioneerId())) {
            lstYourAuctionsModel.addElement(auction.getId());
        } else {
            lstAvailableAuctionsModel.addElement(auction.getId());
        }
    }
    
    public void followAuction (String auctionId) {
        if (lstAvailableAuctionsModel.contains(auctionId) && !lstFollowedAuctionsModel.contains(auctionId)) {
            lstFollowedAuctionsModel.addElement(auctionId);
        }
    }
    
    public void unfollowAuction (String auctionId) {
        if (lstFollowedAuctionsModel.contains(auctionId)) {
            lstFollowedAuctionsModel.removeElement(auctionId);
        }
    }
    
    /*public void auctionFinished(String auctionId) {
        admin.getAuctions().get(auctionId).setState(Auction.STATE.FINISHED);
    }*/

    public void setId(String id) {
        admin.setClientId(id);
    }
    
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(clientGUI, message);
    }

    @Override
    public void update(Object message) {
        if (message instanceof AuctionsAtributeChanged) {
            AuctionsAtributeChanged msg = (AuctionsAtributeChanged) message;
            switch (msg.getType()) {
                case AuctionsAtributeChangedFact.DELETE_ALL_AUCTIONS:
                    deleteAllAuctions();
                    break;
                case AuctionsAtributeChangedFact.DELETE_AUCTION:
                    deleteAuction((String)msg.getObject());
                    break;
                case AuctionsAtributeChangedFact.NEW_AUCTION:
                    addAuction((Auction)msg.getObject());
                    break;
                case AuctionsAtributeChangedFact.NEW_BID_ALLREADY_MADE:
                    showMessage("Ya otro postor ofertó por el nuevo precio de la subasta: " + (String)msg.getObject() + ", se está a la espera que el subastador acepte dicha oferta.");
                    break;
                case AuctionsAtributeChangedFact.NEW_MESSAGE:
                    showMessage((String)msg.getObject());
                    break;
                case AuctionsAtributeChangedFact.FOLLOW_AUCTION:
                    followAuction((String)msg.getObject());
                    break;
                case AuctionsAtributeChangedFact.UNFOLLOW_AUCTION:
                    unfollowAuction((String)msg.getObject());
                    break;
                case AuctionsAtributeChangedFact.NEW_OFFER:
                    AuctionsMsgNewOffer newOffer = (AuctionsMsgNewOffer)msg.getObject();
                    showMessage("Nueva oferta en la subasta " + newOffer.getIdAuction() + ".");
                    break;
                case AuctionsAtributeChangedFact.ACCEPT_OFFER:
                    AuctionsMsgAcceptOffer acceptOffer = (AuctionsMsgAcceptOffer)msg.getObject();
                    showMessage("Se aceptó la oferta en la subasta " + acceptOffer.getIdAuction() + ".");
                    break;
                default:
                    printer.printError("Comando desconocido.");
                    break;
            }
        }
    }
    
    
    
}
