/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions.msgs;

import auctions.AuctionsClientAdminFact;
import auctions.AuctionsMsgFactForClients;
import auctions.AuctionsMsgFactForServer;
import auctions.admin.AuctionsServerAdmin;
import auctions.interfaces.AuctionsIMsgHandler;
import auctions.interfaces.AuctionsIPrintable;
import auctions.objects.Auction;
import auctions.objects.AuctionSimpleClient;
import java.io.Serializable;
import ooserver.msg.OOServerMsgHandler;
import ooserver.observables.OOIObservableObj;

/**
 *
 * @author alexander
 */
public class AuctionsServerMsgHandler extends OOServerMsgHandler implements AuctionsIMsgHandler {
    
    protected AuctionsServerAdmin aAdmin;
    
    public AuctionsServerMsgHandler(AuctionsIPrintable printer, AuctionsServerAdmin admin) {
        super(printer, admin);
        aAdmin = admin;
    }
    
    @Override
    public void handleMsg(Object message) {
        this.printer.print("AuctionsServerMsgHandler: " + "New message received.");
        if (message instanceof AuctionsMsg) {
            AuctionsMsg msg = (AuctionsMsg)message;
            switch(msg.getType()) {
                case AuctionsMsgFactForClients.NEW_OFFER:
                    printer.print("AuctionsServerMsgHandler: " + "Se recibió una nueva oferta, se envía al subastador y a los seguidores.");
                    AuctionsMsgNewOffer newOffer = (AuctionsMsgNewOffer)msg.getMessage();
                    Auction auctionNewOffer = (Auction)aAdmin.getObservableFromServer(newOffer.getIdAuction()).getObject();
                    if ("".equals(auctionNewOffer.getNewBidderId()) && auctionNewOffer.getNextPrice() == newOffer.getNewOffer()){
                        auctionNewOffer.setNewBidderId(newOffer.getIdBidder());
                        printer.print("AuctionsServerMsgHandler: " + "Todo bien, mandando mensaje al subastador");
                        aAdmin.sendMessageToClient(auctionNewOffer.getAuctioneerId(), (Serializable)message);
                        printer.print("AuctionsServerMsgHandler: " + "Todo bien, mandando mensaje a los seguidores de esta subasta");
                        aAdmin.sendMessageToAllObserversOfAbservable(newOffer.getIdAuction(), (Serializable)message);
                    } else {
                        aAdmin.sendMessageToClient(msg.getId(), AuctionsMsgFactForServer.createMsg(AuctionsMsgFactForServer.NEW_BID_ALLREADY_MADE, newOffer.getIdAuction()));
                    }
                    break;
                case AuctionsMsgFactForClients.ACCEPT_OFFER:
                    printer.print("AuctionsServerMsgHandler: " + "Se aceptó una oferta, se envía al postor.");
                    AuctionsMsgAcceptOffer acceptOffer = (AuctionsMsgAcceptOffer)msg.getMessage();
                    Auction auctionAcceptOffer = (Auction)aAdmin.getObservableFromServer(acceptOffer.getIdAuction()).getObject();
                    auctionAcceptOffer.setActualPrice(auctionAcceptOffer.getNextPrice());
                    auctionAcceptOffer.setNextPrice(acceptOffer.getNewPrice());
                    auctionAcceptOffer.setBidderId(acceptOffer.getIdBidder());
                    auctionAcceptOffer.setNewBidderId("");
                    aAdmin.sendMessageToClient(auctionAcceptOffer.getAuctioneerId(), msg);
                    aAdmin.sendMessageToAllObserversOfAbservable(auctionAcceptOffer.getId(), msg);
                    break;
                case AuctionsMsgFactForClients.ADD_AUCTION:
                    printer.print("AuctionsServerMsgHandler: " + "Se agregó una subasta, se envía la nueva subasta a todos los clientes.");
                    Auction newAuction = (Auction)msg.getMessage();
                    if (aAdmin.getObservableFromServer(newAuction.getId()) == null) {
                        aAdmin.sendMessageToAllObservers(AuctionsMsgFactForServer.createMsg(AuctionsMsgFactForServer.SENDING_AUCTION, newAuction));
                    }
                    break;
                case AuctionsMsgFactForClients.FOLLOW_AUCTION:
                    printer.print("AuctionsServerMsgHandler: " + "El cliente " + msg.getId() + " solicita seguir la subasta " + (String)msg.getMessage() + ".");
                    OOIObservableObj observable1 = aAdmin.getObservableFromServer((String)msg.getMessage());
                    observable1.addObserver(aAdmin.getObserverFromServer(msg.getId()));
                    aAdmin.sendMessageToClient(msg.getId(), msg);
                    break;
                case AuctionsMsgFactForClients.UNFOLLOW_AUCTION:
                    printer.print("AuctionsServerMsgHandler: " + "El cliente " + msg.getId() + " solicita dejar de seguir la subasta " + (String)msg.getMessage() + ".");
                    OOIObservableObj observable2 = aAdmin.getObservableFromServer((String)msg.getMessage());
                    observable2.removeObserver(msg.getId());
                    aAdmin.sendMessageToClient(msg.getId(), msg);
                    break;
                case AuctionsMsgFactForClients.REMOVE_AUCTION:
                    printer.print("AuctionsServerMsgHandler: " + "Solicitud de eliminar una subasta.");
                    aAdmin.removeObservableFromServer((String)msg.getMessage());
                    break;
                case AuctionsMsgFactForClients.SEND_ALL_AUCTIONS:
                    printer.print("AuctionsServerMsgHandler: " + "Solicitud de enviar todas las subastas.");
                    aAdmin.sendObservablesToClient(msg.getId());
                    break;
                case AuctionsMsgFactForClients.SEND_ALL_BIDDERS:
                    printer.print("AuctionsServerMsgHandler: " + "Solicitud de enviar todos los postores.");
                    aAdmin.sendObserversToClient(msg.getId());
                    break;
                case AuctionsMsgFactForClients.ADD_BIDDER:
                    printer.print("AuctionsServerMsgHandler: " + "Se va a agregar un nuevo postor al sistema.");
                    aAdmin.addObserverToServer(AuctionsClientAdminFact.createObserverObj(msg.getId(), (AuctionSimpleClient)msg.getMessage()));
                    break;
                case AuctionsMsgFactForClients.MESSAGE_TO_BIDDER:
                    printer.print("AuctionsServerMsgHandler: " + "Se recibió un mensaje para un postor, se envía al postor.");
                    AuctionsMsgMessageToBidder messageToBidder = (AuctionsMsgMessageToBidder)msg.getMessage();
                    aAdmin.sendMessageToClient(messageToBidder.getIdBidder(), (Serializable)message);
                    break;
                case AuctionsMsgFactForClients.AUCTION_FINISHED:
                    printer.print("AuctionsServerMsgHandler: " + "Se terminó una subasta, se envía mensaje al ganador.");
                    AuctionsMsgAuctionFinished auctionFinished = (AuctionsMsgAuctionFinished)msg.getMessage();
                    ((Auction)aAdmin.getObservableFromServer(auctionFinished.getIdAuction()).getObject()).setState(Auction.STATE.FINISHED);
                    aAdmin.sendMessageToClient(auctionFinished.getIdWinnerBidder(), (Serializable)message);
                    aAdmin.sendMessageToAllObserversOfAbservable(auctionFinished.getIdAuction(), (Serializable)message);
                    break;
                case AuctionsMsgFactForClients.CLOSE_CONNECTION:
                    printer.print("AuctionsServerMsgHandler: " + "Close conection.");
                    break;
                default:
                    break;
            }
        }
        super.handleMsg(message);
    }

    @Override
    public void update(Object message) {
            this.printer.print("AuctionsServerMsgHandler: " + "Sending message to message handler.");
            this.handleMsg(message);
    }
    
}
