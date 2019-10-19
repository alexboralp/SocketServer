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
import auctions.objects.AuctionClient;
import java.io.Serializable;
import ooserver.msg.OOServerMsgHandler;

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
        super.handleMsg(message);
        if (message instanceof AuctionsMsg) {
            AuctionsMsg msg = (AuctionsMsg)message;
            switch(msg.getType()) {
                case AuctionsMsgFactForClients.NEW_OFFER:
                    printer.print("AuctionsServerMsgHandler: " + "Se recibió una nueva oferta, se envía al subastador.");
                    AuctionsMsgNewOffer newOffer = (AuctionsMsgNewOffer)msg.getMessage();
                    Auction auctionNewOffer = (Auction)aAdmin.getObservableFromServer(newOffer.getIdAuction());
                    aAdmin.sendMessageToClient(auctionNewOffer.getAuctioneerId(), (Serializable)message);
                    break;
                case AuctionsMsgFactForClients.ACCEPT_OFFER:
                    printer.print("AuctionsServerMsgHandler: " + "Se aceptó una oferta, se envía al postor.");
                    AuctionsMsgAcceptOffer acceptOffer = (AuctionsMsgAcceptOffer)msg.getMessage();
                    Auction auctionAcceptOffer = (Auction)aAdmin.getObservableFromServer(acceptOffer.getIdAuction());
                    aAdmin.sendMessageToClient(auctionAcceptOffer.getAuctioneerId(), (Serializable)message);
                    break;
                case AuctionsMsgFactForClients.ADD_AUCTION:
                    printer.print("AuctionsServerMsgHandler: " + "Se agregó una subasta, se envía la nueva subasta a todos los clientes.");
                    Auction newAuction = (Auction)msg.getMessage();
                    //newAuction.setAuctioneerId(msg.getId());
                    aAdmin.sendMessageToAllObservers(AuctionsMsgFactForServer.createMsg(AuctionsMsgFactForServer.SENDING_AUCTION, newAuction));
                    break;
                /*case MessageClientFactory.FOLLOW_AUCTION:
                    break;
                case MessageClientFactory.UNFOLLOW_AUCTION:
                    break;
                case MessageClientFactory.REMOVE_AUCTION:
                    break;
                case MessageClientFactory.SEND_ALL_AUCTIONS:
                    break;
                case MessageClientFactory.SEND_ALL_BIDDERS:
                    break;*/
                case AuctionsMsgFactForClients.ADD_BIDDER:
                    printer.print("AuctionsServerMsgHandler: " + "Se va a agregar un nuevo postor al sistema.");
                    aAdmin.addObserverToServer(AuctionsClientAdminFact.createObserverObj(msg.getId(), (AuctionClient)msg.getMessage()));
                    break;
                case AuctionsMsgFactForClients.MESSAGE_TO_BIDDER:
                    printer.print("AuctionsServerMsgHandler: " + "Se recibió un mensaje para un postor, se envía al postor.");
                    AuctionsMsgMessageToBidder messageToBidder = (AuctionsMsgMessageToBidder)msg.getMessage();
                    aAdmin.sendMessageToClient(messageToBidder.getIdBidder(), (Serializable)message);
                    break;
                case AuctionsMsgFactForClients.AUCTION_FINISHED:
                    printer.print("AuctionsServerMsgHandler: " + "Se terminó una subasta, se envía mensaje al ganador.");
                    AuctionsMsgAuctionFinished auctionFinished = (AuctionsMsgAuctionFinished)msg.getMessage();
                    ((Auction)aAdmin.getObservableFromServer(auctionFinished.getIdAuction())).setState(Auction.STATE.FINISHED);
                    aAdmin.sendMessageToClient(auctionFinished.getIdWinnerBidder(), (Serializable)message);
                    break;
                case AuctionsMsgFactForClients.CLOSE_CONNECTION:
                    printer.print("AuctionsServerMsgHandler: " + "Close conection.");
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void update(Object message) {
            this.printer.print("AuctionsServerMsgHandler: " + "Sending message to message handler.");
            this.handleMsg(message);
    }
    
}
