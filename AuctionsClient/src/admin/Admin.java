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
import auctions.messages.AuctionsMsgAcceptOffer;
import auctions.messages.AuctionsMsgAuctionFinished;
import auctions.objects.Auction;
import auctions.objects.AuctionClient;
import auctions.messages.AuctionsMsgMessageToBidder;
import auctions.messages.AuctionsMsgNewOffer;
import auctions.objects.AuctionProduct;
import auctions.objects.Auctions;
import auctions.interfaces.AuctionsIMsgHandler;
import auctions.interfaces.AuctionsIObserver;
import auctions.interfaces.AuctionsIPrintable;
import auctions.messages.AuctionsMsg;
import auctions.objects.AuctionsAbsObservable;
import java.util.Date;
import javax.swing.Icon;

/**
 *
 * @author alexander
 */
public class Admin extends AuctionsAbsObservable implements AuctionsIObserver {

    private final AuctionsClientAdmin clientAdministrator;
    
    private String clientId;
    private static String nombre;
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

        clientAdministrator.addObserver(this);
        clientId = "";

        auctions = new Auctions();
        followedAuctions = new Auctions();
        
    }
    
    //TODO: Arreglar esta función
    public void addMeAsAClient() {
        clientAdministrator.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.ADD_BIDDER, ""));
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
        if (message instanceof AuctionsMsg) {
            messageHandler.handleMessage((AuctionsMsg)message);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        Admin.nombre = nombre;

        client = AuctionsClientFact.createClient(nombre);
        addClient(client);
    }

    public String getClientId() {
        return clientId;
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
