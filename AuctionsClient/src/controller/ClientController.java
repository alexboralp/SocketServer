/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import auctions.AuctionFactory;
import auctions.MessageClientFactory;
import auctions.MessageServerFactory;
import auctions.ProductFactory;
import auctions.messages.MsgAcceptOffer;
import auctions.messages.MsgAuctionFinished;
import auctions.objects.Auction;
import auctions.objects.Client;
import auctions.messages.MsgMessageToBidder;
import auctions.messages.MsgNewOffer;
import auctions.objects.Product;
import java.util.Date;
import javax.swing.Icon;
import observerclient.ObserverClientAdministratorFactory;
import observerserver.administrator.ObserverClientAdministrator;
import socketserver.commoninterfaces.IPrintable;
import socketserver.message.IMessage;
import socketserver.patterns.observer.IObserver;
import vista.ClientGUI;

/**
 *
 * @author alexander
 */
public class ClientController implements IPrintable, IObserver {

    private final ClientGUI clientGUI;
    private final ObserverClientAdministrator clientAdministrator;
    private String clientId;
            
    public ClientController(ClientGUI clientGUI, String serverName, int port) {
        this.clientGUI = clientGUI;
        this.clientGUI.setController(this);
        
        print("Starting server" + serverName + "on port " + port + ".");
        
        clientAdministrator = ObserverClientAdministratorFactory.createObserverClientAdministrator(serverName, port, this);
        
        if (clientAdministrator.isOk()) {
            print("Conexión establecida.");
            addClient(clientGUI.getClient());
            clientAdministrator.addObserver(serverName);
            clientId = "";
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
    
    public void cancelAuction(String idAuction) {
        clientAdministrator.sendMessage(MessageClientFactory.createMessage(MessageClientFactory.CANCEL_AUCTION, idAuction));
    }
    
    public void removeAuction(String idAuction) {
        clientAdministrator.sendMessage(MessageClientFactory.createMessage(MessageClientFactory.REMOVE_AUCTION, idAuction));
    }
    
    public void newOffer(String idAuction, double newOffer) {
        MsgNewOffer mess = MessageClientFactory.createNewOffer(idAuction, newOffer);
        clientAdministrator.sendMessage(MessageClientFactory.createMessage(MessageClientFactory.NEW_OFFER, mess));
    }
    
    public void acceptOffer(String idAuction, String idBidder, double newPrice) {
        MsgAcceptOffer mess = MessageClientFactory.createAcceptOffer(idAuction, idBidder, newPrice);
        clientAdministrator.sendMessage(MessageClientFactory.createMessage(MessageClientFactory.ACCEPT_OFFER, mess));
    }
    
    public void auctionFinished(String idAuction, String idWinnerBidder, String messageToWinner) {
        MsgAuctionFinished mess = MessageClientFactory.createAuctionFinished(idAuction, idWinnerBidder, messageToWinner);
        clientAdministrator.sendMessage(MessageClientFactory.createMessage(MessageClientFactory.AUCTION_FINISHED, mess));
    }
    
    public void sendMessageToBidder(String idAuction, String idWinner, String message) {
        MsgMessageToBidder mess = MessageClientFactory.createMessageToBidder(idAuction, idWinner, message);
        clientAdministrator.sendMessage(MessageClientFactory.createMessage(MessageClientFactory.MESSAGE_TO_BIDDER, mess));
    }
    
    public void sendAllAuctions() {
        clientAdministrator.sendMessage(MessageClientFactory.createMessage(MessageClientFactory.SEND_ALL_AUCTIONS, null));
    }
    
    public void sendAllBidders() {
        clientAdministrator.sendMessage(MessageClientFactory.createMessage(MessageClientFactory.SEND_ALL_BIDDERS, null));
    }
    
    public void closeConnection() {
        clientAdministrator.closeConnection();
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

    @Override
    public void update(Object message) {
        if (message instanceof IMessage) {
            IMessage mes = (IMessage)message;
            switch(mes.getType()) {
                case MessageServerFactory.NEW_OFFER:
                    break;
                case MessageServerFactory.MESSAGE_TO_BIDDER:
                case MessageServerFactory.INFO:
                    break;
                case MessageServerFactory.SENDING_ALL_AUCTIONS:
                    break;
                case MessageServerFactory.SENDING_ALL_BIDDERS:
                    break;
                case MessageServerFactory.SENDING_AUCTION:
                    break;
                case MessageServerFactory.SENDING_BIDDER:
                    break;
                case MessageServerFactory.DONE:
                    break;
                case MessageServerFactory.CLOSE_CONNECTION:
                    break;
                case MessageServerFactory.CHECKING_CONNECTION:
                    break;
                default:
                    break;
            }
        }
    }
    
}
