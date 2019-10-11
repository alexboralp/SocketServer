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
            print("ClientController: Conexión establecida.");
            addClient(clientGUI.getClient());
            clientAdministrator.addObserver(this);
            clientId = "";
        } else {
            printError("ClientController: No se pudo establecer la conexión.");
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
            messageReceived((IMessage)message);
        }
    }
    
    private void messageReceived(IMessage message) {
        switch(message.getType()) {
            case MessageClientFactory.NEW_OFFER:
                MsgNewOffer newOffer = (MsgNewOffer)message.getMessage();
                print("ClientController: Recibida una nueva oferta para la subasta " + newOffer.getIdAuction());
                clientGUI.newOffer(newOffer.getIdAuction(), message.getId(), newOffer.getNewOffer());
                break;
            case MessageClientFactory.ACCEPT_OFFER:
                MsgAcceptOffer acceptOffer = (MsgAcceptOffer)message.getMessage();
                print("ClientController: Se aceptó tu oferta en la subasta " + acceptOffer.getIdAuction());
                clientGUI.offerAccepted(acceptOffer.getIdAuction(), acceptOffer.getIdBidder(), acceptOffer.getNewPrice());
                break;
            case MessageClientFactory.AUCTION_FINISHED:
                MsgAuctionFinished auctionFinished = (MsgAuctionFinished)message.getMessage();
                print("ClientController: Se terminó la subasta" + auctionFinished.getIdAuction() + ", el ganador es " + auctionFinished.getIdWinnerBidder() + ".");
                clientGUI.auctionFinished(auctionFinished.getIdAuction());
                break;
            case MessageClientFactory.MESSAGE_TO_BIDDER:
                MsgMessageToBidder messageToBidder = (MsgMessageToBidder)message.getMessage();
                print("ClientController: Llegó un mensaje de la subasta " + messageToBidder.getIdAuction() + ", el mensaje es: " + messageToBidder.getMessage() + ".");
                break;
            case MessageServerFactory.MESSAGE_TO_BIDDER:
            case MessageServerFactory.INFO:
                print("ClientController: Se recibió el mensaje: " + (String)message.getMessage());
                break;
            case MessageServerFactory.SENDING_ALL_AUCTIONS:
                print("ClientController: Se van a recibir todas las subastas.");
                clientGUI.deleteAllAuctions();
                break;
            case MessageServerFactory.SENDING_ALL_BIDDERS:
                print("ClientController: Se van a recibir todos los postores.");
                break;
            case MessageServerFactory.SENDING_AUCTION:
                print("ClientController: Se recibe una subasta.");
                clientGUI.addAuction((Auction)message.getMessage());
                break;
            case MessageServerFactory.SENDING_BIDDER:
                print("ClientController: Se recibe un postor.");
                break;
            case MessageServerFactory.DONE:
                print("ClientController: Mesaje de todo realizado de parte del servidor.");
                break;
            case MessageServerFactory.CLOSE_CONNECTION:
                print("ClientController: El servidor solicita cerrar la conexión.");
                break;
            case MessageServerFactory.CHECKING_CONNECTION:
                print("El servidor solicitó checkar la conexión, se mandó un mensaje de confirmación.");
                break;
            default:
                break;
        }
    }
    
}
