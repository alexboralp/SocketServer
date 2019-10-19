/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions.admin;

import auctions.AuctionsClientFact;
import auctions.AuctionsFact;
import auctions.AuctionsMsgFactForClients;
import auctions.AuctionsProductFact;
import auctions.interfaces.AuctionsIPrintable;
import auctions.msgs.AuctionsAtributeChangedFact;
import auctions.msgs.AuctionsClientMsgHandler;
import auctions.msgs.AuctionsMsgAcceptOffer;
import auctions.msgs.AuctionsMsgAuctionFinished;
import auctions.msgs.AuctionsMsgMessageToBidder;
import auctions.msgs.AuctionsMsgNewOffer;
import auctions.objects.Auction;
import auctions.objects.AuctionClient;
import auctions.objects.AuctionProduct;
import auctions.objects.Auctions;
import java.util.Date;
import javax.swing.Icon;
import ooserver.admin.OOClientAdmin;

/**
 *
 * @author alexander
 */
public class AuctionsClientAdmin extends OOClientAdmin {
    
    private AuctionClient client;
    
    private Auctions auctions;
    private Auctions followedAuctions;

    public AuctionsClientAdmin(String serverName, int port, AuctionsIPrintable printer) {
        super(serverName, port, printer);
        _init_(printer);
        
        msgHandler = new AuctionsClientMsgHandler(printer, this);
        this.replaceMsgHandler(msgHandler);
    }

    public AuctionsClientAdmin(String serverName, int port, AuctionsIPrintable printer, AuctionsClientMsgHandler msgHandler) {
        super(serverName, port, printer, msgHandler);
        _init_(printer);
    }
    
    private void _init_(AuctionsIPrintable printer) {
        if (!this.isOk()) {
            printer.printError("AuctionsClientAdmin: " + "No se pudo establecer la conexión.");
            System.exit(1);
        }
        
        //printer.print("AuctionsClientAdmin: " + "Conexión establecida.");
        //printer.print("AuctionsClientAdmin: " + "Solicitando nuestro ID.");
        //this.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.SEND_MY_ID, null));
        
        client = AuctionsClientFact.createClient(this.getMyId(), null);

        auctions = new Auctions();
        followedAuctions = new Auctions();
    }
    
    public void addMeAsAClient() {
        if (client.getId() != null && client.getName() != null) {
            printer.print("AuctionsClientAdmin: " + "Asking server to add me as observer.");
            this.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.ADD_BIDDER, client));
        }
    }
    
    public void addClient(AuctionClient client) {
        this.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.ADD_BIDDER, client));
    }
    
    public void addAuction(Auction auction) {
        auctions.add(auction);
        this.updateAll(AuctionsAtributeChangedFact.createAuctionsAtributeChanged("new auction", auction));
    }
    
    public void addAuction(String id, Date date, int duration, String productId, String productDescription, Icon productImage, double productInititalPrice, double initialPrice, Icon image) {
        AuctionProduct product = AuctionsProductFact.createProduct(productId, productDescription, productImage, productInititalPrice);
        Auction auction = AuctionsFact.createAuction(this.getMyId(), id, date, duration, product, initialPrice, image);
        this.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.ADD_AUCTION, auction));
    }
    
    public void followAuction(String idAuction) {
        this.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.FOLLOW_AUCTION, idAuction));
    }
    
    public void unfollowAuction(String idAuction) {
        this.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.UNFOLLOW_AUCTION, idAuction));
    }
    
    public void cancelAuction(String idAuction) {
        this.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.CANCEL_AUCTION, idAuction));
    }
    
    public void removeAuction(String idAuction) {
        this.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.REMOVE_AUCTION, idAuction));
    }
    
    public void newOffer(String idAuction, double newOffer) {
        AuctionsMsgNewOffer mess = AuctionsMsgFactForClients.createNewOffer(idAuction, newOffer);
        this.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.NEW_OFFER, mess));
    }
    
    public void acceptOffer(String idAuction, String idBidder, double newPrice) {
        AuctionsMsgAcceptOffer mess = AuctionsMsgFactForClients.createAcceptOffer(idAuction, idBidder, newPrice);
        this.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.ACCEPT_OFFER, mess));
    }
    
    public void auctionFinished(String idAuction, String idWinnerBidder, String messageToWinner) {
        AuctionsMsgAuctionFinished mess = AuctionsMsgFactForClients.createAuctionFinished(idAuction, idWinnerBidder, messageToWinner);
        this.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.AUCTION_FINISHED, mess));
    }
    
    public void sendMessageToBidder(String idAuction, String idWinner, String message) {
        AuctionsMsgMessageToBidder mess = AuctionsMsgFactForClients.createMessageToBidder(idAuction, idWinner, message);
        this.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.MESSAGE_TO_BIDDER, mess));
    }
    
    public void sendAllAuctions() {
        this.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.SEND_ALL_AUCTIONS, null));
    }
    
    public void sendAllBidders() {
        this.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.SEND_ALL_BIDDERS, null));
    }
    
    // Setter y Getter del administrador

    public void setClientName(String name) {
        client.setName(name);
        addMeAsAClient();
    }

    public void setClientId(String clientId) {
        client.setId(clientId);
        if (client.getName() != null) {
            addMeAsAClient();
        }
    }

    public AuctionClient getClient() {
        return client;
    }

    public Auctions getAuctions() {
        return auctions;
    }

    public Auctions getFollowedAuctions() {
        return followedAuctions;
    }
}
