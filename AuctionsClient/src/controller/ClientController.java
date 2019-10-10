/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import auctions.AuctionFactory;
import auctions.MessageClientFactory;
import auctions.ProductFactory;
import auctions.messages.AuctionFinished;
import auctions.objects.Auction;
import auctions.objects.Client;
import auctions.messages.MessageToBidder;
import auctions.messages.NewOffer;
import auctions.objects.Product;
import java.util.Date;
import javax.swing.Icon;
import observerclient.ObserverClientAdministratorFactory;
import observerserver.administrator.ObserverClientAdministrator;
import socketserver.commoninterfaces.IPrintable;
import vista.ClientGUI;

/**
 *
 * @author alexander
 */
public class ClientController implements IPrintable {

    private final ClientGUI clientGUI;
    private final ObserverClientAdministrator clientAdministrator;
            
    public ClientController(ClientGUI clientGUI, String serverName, int port) {
        this.clientGUI = clientGUI;
        this.clientGUI.setController(this);
        
        print("Starting server" + serverName + "on port " + port + ".");
        
        clientAdministrator = ObserverClientAdministratorFactory.createObserverClientAdministrator(serverName, port, this);
        
        if (clientAdministrator.isOk()) {
            print("Conexión establecida.");
        } else {
            printError("No se pudo establecer la conexión.");
            System.exit(1);
        }
        
    }
    
    public void addClient(Client client) {
        clientAdministrator.sendMessage(MessageClientFactory.createMessage(MessageClientFactory.ADD_BIDDER, client));
    }
    
    
    public void addAuction(String id, Date date, int duration, String productId, String productDescription, Icon productImage, double productInititalPrice, double initialPrice, Icon image) {
        Product product = ProductFactory.createProduct(productId, productDescription, productImage, productInititalPrice);
        Auction auction = AuctionFactory.createAuction(id, date, duration, product, initialPrice, image);
        clientAdministrator.sendMessage(MessageClientFactory.createMessage(MessageClientFactory.ADD_AUCTION, auction));
    }
    
    public void followAuction(String idAuction) {
        clientAdministrator.sendMessage(MessageClientFactory.createMessage(MessageClientFactory.FOLLOW_AUCTION, idAuction));
    }
    
    public void unfollowAuction(String idAuction) {
        clientAdministrator.sendMessage(MessageClientFactory.createMessage(MessageClientFactory.UNFOLLOW_AUCTION, idAuction));
    }
    
    public void removeAuction(String idAuction) {
        clientAdministrator.sendMessage(MessageClientFactory.createMessage(MessageClientFactory.REMOVE_AUCTION, idAuction));
    }
    
    public void newOffer(String idAuction, double newOffer) {
        NewOffer mess = MessageClientFactory.createNewOffer(idAuction, newOffer);
        clientAdministrator.sendMessage(MessageClientFactory.createMessage(MessageClientFactory.NEW_OFFER, mess));
    }
    
    public void auctionFinished(String idAuction, String idWinnerBidder, String messageToWinner) {
        AuctionFinished mess = MessageClientFactory.createAuctionFinished(idAuction, idWinnerBidder, messageToWinner);
        clientAdministrator.sendMessage(MessageClientFactory.createMessage(MessageClientFactory.AUCTION_FINISHED, mess));
    }
    
    public void sendMessageToBidder(String idAuction, String idWinner, String message) {
        MessageToBidder mess = MessageClientFactory.createMessageToBidder(idAuction, idWinner, message);
        clientAdministrator.sendMessage(MessageClientFactory.createMessage(MessageClientFactory.MESSAGE_TO_BIDDER, mess));
    }
    
    public boolean isOk() {
        return clientAdministrator.isOk();
    }
    
    @Override
    public void print(String message) {
        clientGUI.print(message + "\n");
    }

    @Override
    public void printError(String message) {
        clientGUI.print("ERROR: " + message + "\n");
    }
    
}
