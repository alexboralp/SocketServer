/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import auctions.AuctionsClientAdminFact;
import auctions.AuctionsMsgFactForClients;
import auctions.AuctionsMsgFactForServer;
import auctions.AuctionsServerAdminFact;
import auctions.admin.AuctionsServerAdmin;
import auctions.interfaces.AuctionsIMsgHandler;
import auctions.interfaces.AuctionsIObserver;
import auctions.interfaces.AuctionsIPrintable;
import auctions.msgs.AuctionsMsg;
import auctions.msgs.AuctionsMsgAcceptOffer;
import auctions.msgs.AuctionsMsgAuctionFinished;
import auctions.msgs.AuctionsMsgMessageToBidder;
import auctions.msgs.AuctionsMsgNewOffer;
import auctions.objects.Auction;
import auctions.objects.AuctionClient;

/**
 *
 * @author alexander
 */
public class Admin implements AuctionsIObserver {

    private final AuctionsServerAdmin serverAdmin;
    private final AuctionsIPrintable printer;
    private AuctionsIMsgHandler msgHandler;
            
    public Admin(int port, AuctionsIPrintable printer) {
        this.printer = printer;
        
        printer.print("Admin: " + "Starting server on port " + port + ".");
        
        serverAdmin = AuctionsServerAdminFact.createAuctionsServerAdmin(port, printer);
        serverAdmin.addObserver(this);
    }

    @Override
    public void update(Object message) {
        printer.print("Admin: New message received.");
        if (message instanceof AuctionsMsg) {
            printer.print("Admin: AuctionMsg received.");
            handleMsg((AuctionsMsg)message);
            if (msgHandler != null) {
                printer.print("Admin: Resending message to MsgHandler.");
                msgHandler.handleMsg((AuctionsMsg)message);
            }
        } else {
            printer.print("Admin: Non AuctionMsg received.");
        }
    }
    
    private void handleMsg(AuctionsMsg message) {
        switch(message.getType()) {
            case AuctionsMsgFactForClients.NEW_OFFER:
                printer.print("Admin: " + "Se recibió una nueva oferta, se envía al subastador.");
                AuctionsMsgNewOffer newOffer = (AuctionsMsgNewOffer)message.getMessage();
                Auction auctionNewOffer = (Auction)serverAdmin.getObservableFromServer(newOffer.getIdAuction());
                serverAdmin.sendMessageToClient(auctionNewOffer.getAuctioneerId(), message);
                break;
            case AuctionsMsgFactForClients.ACCEPT_OFFER:
                printer.print("Admin: " + "Se aceptó una oferta, se envía al postor.");
                AuctionsMsgAcceptOffer acceptOffer = (AuctionsMsgAcceptOffer)message.getMessage();
                Auction auctionAcceptOffer = (Auction)serverAdmin.getObservableFromServer(acceptOffer.getIdAuction());
                serverAdmin.sendMessageToClient(auctionAcceptOffer.getAuctioneerId(), message);
                break;
            case AuctionsMsgFactForClients.ADD_AUCTION:
                printer.print("Admin: " + "Se agregó una subasta, se envía la nueva subasta a todos los clientes.");
                Auction newAuction = (Auction)message.getMessage();
                newAuction.setOwnerId(message.getId());
                serverAdmin.sendMessageToAllObservers(AuctionsMsgFactForServer.createMsg(AuctionsMsgFactForServer.SENDING_AUCTION, newAuction));
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
                serverAdmin.addObserverToServer(AuctionsClientAdminFact.createObserverObj(message.getId(), (AuctionClient)message.getMessage()));
                break;
            case AuctionsMsgFactForClients.MESSAGE_TO_BIDDER:
                printer.print("Admin: " + "Se recibió un mensaje para un postor, se envía al postor.");
                AuctionsMsgMessageToBidder messageToBidder = (AuctionsMsgMessageToBidder)message.getMessage();
                serverAdmin.sendMessageToClient(messageToBidder.getIdBidder(), message);
                break;
            case AuctionsMsgFactForClients.AUCTION_FINISHED:
                printer.print("Admin: " + "Se terminó una subasta, se envía mensaje al ganador.");
                AuctionsMsgAuctionFinished auctionFinished = (AuctionsMsgAuctionFinished)message.getMessage();
                ((Auction)serverAdmin.getObservableFromServer(auctionFinished.getIdAuction())).setState(Auction.STATE.FINISHED);
                serverAdmin.sendMessageToClient(auctionFinished.getIdWinnerBidder(), message);
                break;
            /*case MessageClientFactory.CLOSE_CONNECTION:
                break;*/
            default:
                break;
        }
    }

    public AuctionsIMsgHandler getMessageHandler() {
        return msgHandler;
    }

    public void setMessageHandler(AuctionsIMsgHandler msgHandler) {
        this.msgHandler = msgHandler;
    }
    
}
