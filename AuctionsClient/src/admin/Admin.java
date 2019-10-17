/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import auctions.AuctionsClientAdminFact;
import auctions.AuctionsClientFact;
import auctions.AuctionsFact;
import auctions.AuctionsMsgFactForClients;
import auctions.AuctionsProductFact;
import auctions.admin.AuctionsClientAdmin;
import auctions.msgs.AuctionsMsgAcceptOffer;
import auctions.msgs.AuctionsMsgAuctionFinished;
import auctions.objects.Auction;
import auctions.objects.AuctionClient;
import auctions.msgs.AuctionsMsgMessageToBidder;
import auctions.msgs.AuctionsMsgNewOffer;
import auctions.objects.AuctionProduct;
import auctions.objects.Auctions;
import auctions.interfaces.AuctionsIMsgHandler;
import auctions.interfaces.AuctionsIObserver;
import auctions.interfaces.AuctionsIPrintable;
import auctions.msgs.AuctionsMsg;
import auctions.objects.AuctionsAbsObservable;
import java.util.Date;
import javax.swing.Icon;
import ooserver.commoninterfaces.OOIMsg;

/**
 *
 * @author alexander
 */
public class Admin extends AuctionsAbsObservable implements AuctionsIObserver {

    private final AuctionsClientAdmin clientAdministrator;
    
    private AuctionsIMsgHandler messageHandler;
    
    private final AuctionsIPrintable printer;
    
    private AuctionClient client;
    
    private final Auctions auctions;
    private final Auctions followedAuctions;
            
    public Admin(String serverName, int port, AuctionsIPrintable printer) {
        this.printer = printer;
        
        printer.print("Admin: " + "Starting server " + serverName + " on port " + port + ".");
        
        clientAdministrator = AuctionsClientAdminFact.createAuctionsClientAdmin(serverName, port, printer);
        
        if (!clientAdministrator.isOk()) {
            printer.printError("Admin: " + "No se pudo establecer la conexión.");
            System.exit(1);
        }
        
        printer.print("Admin: " + "Conexión establecida.");
        printer.print("Admin: " + "Solicitando nuestro ID.");
        clientAdministrator.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.SEND_MY_ID, null));

        clientAdministrator.addObserver(this);
        
        client = AuctionsClientFact.createClient(null, null);

        auctions = new Auctions();
        followedAuctions = new Auctions();
        
    }
    
    //TODO: Arreglar esta función
    public void addMeAsAClient() {
        clientAdministrator.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.ADD_BIDDER, client));
    }
    
    public void addClient(AuctionClient client) {
        clientAdministrator.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.ADD_BIDDER, client));
    }
    
    public void addAuction(String id, Date date, int duration, String productId, String productDescription, Icon productImage, double productInititalPrice, double initialPrice, Icon image) {
        AuctionProduct product = AuctionsProductFact.createProduct(productId, productDescription, productImage, productInititalPrice);
        Auction auction = AuctionsFact.createAuction(id, date, duration, product, initialPrice, image);
        clientAdministrator.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.ADD_AUCTION, auction));
    }
    
    public void followAuction(String idAuction) {
        clientAdministrator.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.FOLLOW_AUCTION, idAuction));
    }
    
    public void unfollowAuction(String idAuction) {
        clientAdministrator.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.UNFOLLOW_AUCTION, idAuction));
    }
    
    public void cancelAuction(String idAuction) {
        clientAdministrator.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.CANCEL_AUCTION, idAuction));
    }
    
    public void removeAuction(String idAuction) {
        clientAdministrator.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.REMOVE_AUCTION, idAuction));
    }
    
    public void newOffer(String idAuction, double newOffer) {
        AuctionsMsgNewOffer mess = AuctionsMsgFactForClients.createNewOffer(idAuction, newOffer);
        clientAdministrator.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.NEW_OFFER, mess));
    }
    
    public void acceptOffer(String idAuction, String idBidder, double newPrice) {
        AuctionsMsgAcceptOffer mess = AuctionsMsgFactForClients.createAcceptOffer(idAuction, idBidder, newPrice);
        clientAdministrator.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.ACCEPT_OFFER, mess));
    }
    
    public void auctionFinished(String idAuction, String idWinnerBidder, String messageToWinner) {
        AuctionsMsgAuctionFinished mess = AuctionsMsgFactForClients.createAuctionFinished(idAuction, idWinnerBidder, messageToWinner);
        clientAdministrator.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.AUCTION_FINISHED, mess));
    }
    
    public void sendMessageToBidder(String idAuction, String idWinner, String message) {
        AuctionsMsgMessageToBidder mess = AuctionsMsgFactForClients.createMessageToBidder(idAuction, idWinner, message);
        clientAdministrator.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.MESSAGE_TO_BIDDER, mess));
    }
    
    public void sendAllAuctions() {
        clientAdministrator.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.SEND_ALL_AUCTIONS, null));
    }
    
    public void sendAllBidders() {
        clientAdministrator.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.SEND_ALL_BIDDERS, null));
    }
    
    public void closeConnection() {
        clientAdministrator.closeConnection();
    }
    
    public boolean isOk() {
        return clientAdministrator.isOk();
    }

    @Override
    public void update(Object message) {
        printer.print("Admin: message received.");
        if (message instanceof AuctionsMsg) {
            printer.print("Admin: AuctionMsg received.");
            if (messageHandler != null) {
                printer.print("Admin: Resending message to MessageHandler.");
                messageHandler.handleMsg((AuctionsMsg)message);
            }
        } else if (message instanceof OOIMsg) {
            printer.print("Admin: OOIMsg received.");
            message = AuctionsMsgFactForClients.createMsg((OOIMsg)message);
            if (messageHandler != null) {
                printer.print("Admin: Resending message to MessageHandler.");
                messageHandler.handleMsg((AuctionsMsg)message);
            }
        } else {
            printer.print("Admin: Non AuctionMsg received.");
        }
    }

    public void setClientName(String name) {
        client.setName(name);
        if (client.getId() != null) {
            addMeAsAClient();
        }
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

    public AuctionsIMsgHandler getMessageHandler() {
        return messageHandler;
    }

    public void setMessageHandler(AuctionsIMsgHandler messageHandler) {
        this.messageHandler = messageHandler;
    }
    
}
