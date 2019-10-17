/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import admin.Admin;
import auctions.AuctionsMsgFactForClients;
import auctions.AuctionsMsgFactForServer;
import auctions.msgs.AuctionsMsgAcceptOffer;
import auctions.msgs.AuctionsMsgAuctionFinished;
import auctions.msgs.AuctionsMsgMessageToBidder;
import auctions.msgs.AuctionsMsgNewOffer;
import auctions.objects.Auction;
import auctions.interfaces.AuctionsIMsgHandler;
import auctions.interfaces.AuctionsIPrintable;
import auctions.msgs.AuctionsMsg;
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
import javax.swing.DefaultListModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import vista.ClientGUI;

/**
 *
 * @author alexander
 */
public class Controller implements AuctionsIMsgHandler<AuctionsMsg> {
    
    private ClientGUI clientGUI;
    private final Admin admin;
    private final AuctionsIPrintable printer;

    public Controller(ClientGUI clientGUI, Admin admin, AuctionsIPrintable printer) {
        this.clientGUI = clientGUI;
        this.admin = admin;
        this.printer = printer;
        
        _init_();
    }
    
    private void _init_() {
        
        admin.setMessageHandler(this);
    
        DefaultListModel<String> lstYourAuctionsModel;
        DefaultListModel<String> lstFollowedAuctionsModel;
        DefaultListModel<String> lstAvailableAuctionsModel;
    
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
        clientGUI.spnNextPrice.setModel(spnSiguientePrecioModel);
        
        clientGUI.btnAcceptNewOffer.addActionListener(new ActionBtnAcceptNewOffer(admin, clientGUI, this, printer));
        clientGUI.btnCancelAuction.addActionListener(new ActionBtnCancelAuction(admin, clientGUI, this, printer));
        clientGUI.btnCreateNewAuction.addActionListener(new ActionBtnCreateNewAuction(admin, clientGUI, this, printer));
        clientGUI.btnFollowAuction.addActionListener(new ActionBtnFollowAuction(admin, clientGUI, this, printer));
        clientGUI.btnMakeNewOffer.addActionListener(new ActionBtnMakeNewOffer(admin, clientGUI, this, printer));
        clientGUI.btnSendMsgToWinner.addActionListener(new ActionBtnSendMsgToWinner(admin, clientGUI, this, printer));
        clientGUI.btnUnfollowAuction.addActionListener(new ActionBtnUnfollowAuction(admin, clientGUI, this, printer));
        clientGUI.lstAvailableAuctions.addListSelectionListener(new ActionLstAvailableAuctions(admin, clientGUI, this, printer));
        clientGUI.lstFollowedAuctions.addListSelectionListener(new ActionLstFollowedAuctions(admin, clientGUI, this, printer));
        clientGUI.lstYourAuctions.addListSelectionListener(new ActionLstYourAuctions(admin, clientGUI, this, printer));
        clientGUI.mnuSalir.addActionListener(new ActionMnuSalir(admin, clientGUI, this, printer));
        clientGUI.addWindowListener(new ActionWindowListener(admin, clientGUI, this, printer));
        
        String nombre = clientGUI.getClientName();
        admin.setClientName(nombre);
    }

    public ClientGUI getClientGUI() {
        return clientGUI;
    }

    public void setClientGUI(ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
    }
    
    public void actualizarTextos (Auction auction) {
        clientGUI.txtYourAuctionId.setText(auction.getId());
        clientGUI.txtYourAuctionStatus.setText(auction.getState().toString());
        clientGUI.txtYourAuctionBestOffer.setText(Double.toString(auction.getActualPrice()));
        clientGUI.txtYourAuctionsNewOffer.setText(Double.toString(auction.getNextPrice()));
        clientGUI.txtYourAuctionsIdProduct.setText(auction.getProduct().getId());
        clientGUI.spnNextPrice.setValue(auction.getNextPrice());
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
    }
    
    public void AuctionSelected() {
        String subasta = clientGUI.lstFollowedAuctions.getSelectedValue();
        if (admin.getAuctions().containsKey(subasta)) {
            Auction auction = admin.getAuctions().get(subasta);
            actualizarTextos(auction);
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
        ((DefaultListModel<String>)clientGUI.lstAvailableAuctions.getModel()).clear();
        ((DefaultListModel<String>)clientGUI.lstYourAuctions.getModel()).clear();
    }
    
    public void addAuction(Auction auction) {
        admin.getAuctions().add(auction);
        ((DefaultListModel<String>)clientGUI.lstAvailableAuctions.getModel()).addElement(auction.getId());
        if (admin.getClient().getId().equals(auction.getAuctioneerId())) {
            ((DefaultListModel<String>)clientGUI.lstYourAuctions.getModel()).addElement(auction.getId());
        }
    }
    
    public void auctionFinished(String auctionId) {
        admin.getAuctions().get(auctionId).setState(Auction.STATE.FINISHED);
    }

    @Override
    public void handleMsg(AuctionsMsg message) {
        printer.print("ClientController: Nuevo mensaje recibido: " + message.toString());
        switch(message.getType()) {
            case AuctionsMsgFactForClients.NEW_OFFER:
                AuctionsMsgNewOffer newOffer = (AuctionsMsgNewOffer)message.getMessage();
                printer.print("ClientController: Recibida una nueva oferta para la subasta " + newOffer.getIdAuction());
                newOffer(newOffer.getIdAuction(), message.getId(), newOffer.getNewOffer());
                break;
            case AuctionsMsgFactForClients.ACCEPT_OFFER:
                AuctionsMsgAcceptOffer acceptOffer = (AuctionsMsgAcceptOffer)message.getMessage();
                printer.print("ClientController: Se aceptó tu oferta en la subasta " + acceptOffer.getIdAuction());
                offerAccepted(acceptOffer.getIdAuction(), acceptOffer.getIdBidder(), acceptOffer.getNewPrice());
                break;
            case AuctionsMsgFactForClients.AUCTION_FINISHED:
                AuctionsMsgAuctionFinished auctionFinished = (AuctionsMsgAuctionFinished)message.getMessage();
                printer.print("ClientController: Se terminó la subasta" + auctionFinished.getIdAuction() + ", el ganador es " + auctionFinished.getIdWinnerBidder() + ".");
                auctionFinished(auctionFinished.getIdAuction());
                break;
            case AuctionsMsgFactForClients.MESSAGE_TO_BIDDER:
                AuctionsMsgMessageToBidder messageToBidder = (AuctionsMsgMessageToBidder)message.getMessage();
                printer.print("ClientController: Llegó un mensaje de la subasta " + messageToBidder.getIdAuction() + ", el mensaje es: " + messageToBidder.getMessage() + ".");
                break;
            case AuctionsMsgFactForServer.MESSAGE_TO_BIDDER:
            case AuctionsMsgFactForServer.INFO:
                printer.print("ClientController: Se recibió el mensaje: " + (String)message.getMessage());
                break;
            case AuctionsMsgFactForServer.SENDING_ALL_AUCTIONS:
                printer.print("ClientController: Se van a recibir todas las subastas.");
                deleteAllAuctions();
                break;
            case AuctionsMsgFactForServer.SENDING_ALL_BIDDERS:
                printer.print("ClientController: Se van a recibir todos los postores.");
                break;
            case AuctionsMsgFactForServer.SENDING_AUCTION:
                printer.print("ClientController: Se recibe una subasta.");
                addAuction((Auction)message.getMessage());
                break;
            case AuctionsMsgFactForServer.SENDING_BIDDER:
                printer.print("ClientController: Se recibe un postor.");
                break;
            case AuctionsMsgFactForServer.DONE:
                printer.print("ClientController: Todo realizado de parte del servidor.");
                break;
            case AuctionsMsgFactForServer.SENDING_BIDDER_ID:
                setId((String)message.getMessage());
                printer.print("ClientController: El servidor nos envía nuestro id: " + admin.getClient().getId() + ".");
                break;
            case AuctionsMsgFactForServer.CLOSE_CONNECTION:
                printer.print("ClientController: El servidor solicita cerrar la conexión.");
                break;
            case AuctionsMsgFactForServer.CHECKING_CONNECTION:
                printer.print("El servidor solicitó checkar la conexión, se mandó un mensaje de confirmación.");
                break;
            default:
                break;
        }
    }

    public void setId(String id) {
        admin.setClientId(id);
    }
    
    
    
}
