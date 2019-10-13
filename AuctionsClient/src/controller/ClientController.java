/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import auctions.AuctionFact;
import auctions.AuctionMsgFactForClients;
import auctions.AuctionMsgFactForServer;
import auctions.AuctionProductFact;
import auctions.messages.AuctionMsgAcceptOffer;
import auctions.messages.AuctionMsgAuctionFinished;
import auctions.objects.Auction;
import auctions.objects.AuctionClient;
import auctions.messages.AuctionMsgMessageToBidder;
import auctions.messages.AuctionMsgNewOffer;
import auctions.objects.AuctionProduct;
import java.util.Date;
import javax.swing.Icon;
import ooclient.OOClientAdminFact;
import ooserver.admin.OOClientAdmin;
import ooserver.commoninterfaces.OOIObserver;
import ooserver.commoninterfaces.OOIPrintable;
import ooserver.commoninterfaces.OOIMsg;
import vista.ClientGUI;

/**
 *
 * @author alexander
 */
public class ClientController implements OOIPrintable, OOIObserver {

    private final ClientGUI clientGUI;
    private final OOClientAdmin clientAdministrator;
    private String clientId;
            
    public ClientController(ClientGUI clientGUI, String serverName, int port) {
        this.clientGUI = clientGUI;
        this.clientGUI.setController(this);
        
        print("Starting server " + serverName + " on port " + port + ".");
        
        clientAdministrator = OOClientAdminFact.createObserverClientAdministrator(serverName, port, this);
        
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
    
    //TODO: Arreglar esta función
    public void addMeAsAClient() {
        clientAdministrator.sendMessage(AuctionMsgFactForClients.createMsg(AuctionMsgFactForClients.ADD_BIDDER, ""));
    }
    
    public void addClient(AuctionClient client) {
        clientAdministrator.sendMessage(AuctionMsgFactForClients.createMsg(AuctionMsgFactForClients.ADD_BIDDER, client));
    }
    
    public void addAuction(String id, Date date, int duration, String productId, String productDescription, Icon productImage, double productInititalPrice, double initialPrice, Icon image) {
        AuctionProduct product = AuctionProductFact.createProduct(productId, productDescription, productImage, productInititalPrice);
        Auction auction = AuctionFact.createAuction(id, date, duration, product, initialPrice, image);
        clientAdministrator.sendMessage(AuctionMsgFactForClients.createMsg(AuctionMsgFactForClients.ADD_AUCTION, auction));
    }
    
    public void followAuction(String idAuction) {
        clientAdministrator.sendMessage(AuctionMsgFactForClients.createMsg(AuctionMsgFactForClients.FOLLOW_AUCTION, idAuction));
    }
    
    public void unfollowAuction(String idAuction) {
        clientAdministrator.sendMessage(AuctionMsgFactForClients.createMsg(AuctionMsgFactForClients.UNFOLLOW_AUCTION, idAuction));
    }
    
    public void cancelAuction(String idAuction) {
        clientAdministrator.sendMessage(AuctionMsgFactForClients.createMsg(AuctionMsgFactForClients.CANCEL_AUCTION, idAuction));
    }
    
    public void removeAuction(String idAuction) {
        clientAdministrator.sendMessage(AuctionMsgFactForClients.createMsg(AuctionMsgFactForClients.REMOVE_AUCTION, idAuction));
    }
    
    public void newOffer(String idAuction, double newOffer) {
        AuctionMsgNewOffer mess = AuctionMsgFactForClients.createNewOffer(idAuction, newOffer);
        clientAdministrator.sendMessage(AuctionMsgFactForClients.createMsg(AuctionMsgFactForClients.NEW_OFFER, mess));
    }
    
    public void acceptOffer(String idAuction, String idBidder, double newPrice) {
        AuctionMsgAcceptOffer mess = AuctionMsgFactForClients.createAcceptOffer(idAuction, idBidder, newPrice);
        clientAdministrator.sendMessage(AuctionMsgFactForClients.createMsg(AuctionMsgFactForClients.ACCEPT_OFFER, mess));
    }
    
    public void auctionFinished(String idAuction, String idWinnerBidder, String messageToWinner) {
        AuctionMsgAuctionFinished mess = AuctionMsgFactForClients.createAuctionFinished(idAuction, idWinnerBidder, messageToWinner);
        clientAdministrator.sendMessage(AuctionMsgFactForClients.createMsg(AuctionMsgFactForClients.AUCTION_FINISHED, mess));
    }
    
    public void sendMessageToBidder(String idAuction, String idWinner, String message) {
        AuctionMsgMessageToBidder mess = AuctionMsgFactForClients.createMessageToBidder(idAuction, idWinner, message);
        clientAdministrator.sendMessage(AuctionMsgFactForClients.createMsg(AuctionMsgFactForClients.MESSAGE_TO_BIDDER, mess));
    }
    
    public void sendAllAuctions() {
        clientAdministrator.sendMessage(AuctionMsgFactForClients.createMsg(AuctionMsgFactForClients.SEND_ALL_AUCTIONS, null));
    }
    
    public void sendAllBidders() {
        clientAdministrator.sendMessage(AuctionMsgFactForClients.createMsg(AuctionMsgFactForClients.SEND_ALL_BIDDERS, null));
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
        if (message instanceof OOIMsg) {
            messageReceived((OOIMsg)message);
        }
    }
    
    private void messageReceived(OOIMsg message) {
        switch(message.getType()) {
            case AuctionMsgFactForClients.NEW_OFFER:
                AuctionMsgNewOffer newOffer = (AuctionMsgNewOffer)message.getMessage();
                print("ClientController: Recibida una nueva oferta para la subasta " + newOffer.getIdAuction());
                clientGUI.newOffer(newOffer.getIdAuction(), message.getId(), newOffer.getNewOffer());
                break;
            case AuctionMsgFactForClients.ACCEPT_OFFER:
                AuctionMsgAcceptOffer acceptOffer = (AuctionMsgAcceptOffer)message.getMessage();
                print("ClientController: Se aceptó tu oferta en la subasta " + acceptOffer.getIdAuction());
                clientGUI.offerAccepted(acceptOffer.getIdAuction(), acceptOffer.getIdBidder(), acceptOffer.getNewPrice());
                break;
            case AuctionMsgFactForClients.AUCTION_FINISHED:
                AuctionMsgAuctionFinished auctionFinished = (AuctionMsgAuctionFinished)message.getMessage();
                print("ClientController: Se terminó la subasta" + auctionFinished.getIdAuction() + ", el ganador es " + auctionFinished.getIdWinnerBidder() + ".");
                clientGUI.auctionFinished(auctionFinished.getIdAuction());
                break;
            case AuctionMsgFactForClients.MESSAGE_TO_BIDDER:
                AuctionMsgMessageToBidder messageToBidder = (AuctionMsgMessageToBidder)message.getMessage();
                print("ClientController: Llegó un mensaje de la subasta " + messageToBidder.getIdAuction() + ", el mensaje es: " + messageToBidder.getMessage() + ".");
                break;
            case AuctionMsgFactForServer.MESSAGE_TO_BIDDER:
            case AuctionMsgFactForServer.INFO:
                print("ClientController: Se recibió el mensaje: " + (String)message.getMessage());
                break;
            case AuctionMsgFactForServer.SENDING_ALL_AUCTIONS:
                print("ClientController: Se van a recibir todas las subastas.");
                clientGUI.deleteAllAuctions();
                break;
            case AuctionMsgFactForServer.SENDING_ALL_BIDDERS:
                print("ClientController: Se van a recibir todos los postores.");
                break;
            case AuctionMsgFactForServer.SENDING_AUCTION:
                print("ClientController: Se recibe una subasta.");
                clientGUI.addAuction((Auction)message.getMessage());
                break;
            case AuctionMsgFactForServer.SENDING_BIDDER:
                print("ClientController: Se recibe un postor.");
                break;
            case AuctionMsgFactForServer.DONE:
                print("ClientController: Todo realizado de parte del servidor.");
                break;
            case AuctionMsgFactForServer.CLOSE_CONNECTION:
                print("ClientController: El servidor solicita cerrar la conexión.");
                break;
            case AuctionMsgFactForServer.CHECKING_CONNECTION:
                print("El servidor solicitó checkar la conexión, se mandó un mensaje de confirmación.");
                break;
            default:
                break;
        }
    }
    
}
