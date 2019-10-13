/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import auctions.AuctionMsgFactForClients;
import auctions.messages.AuctionMsgAcceptOffer;
import auctions.messages.AuctionMsgAuctionFinished;
import auctions.messages.AuctionMsgMessageToBidder;
import auctions.messages.AuctionMsgNewOffer;
import auctions.objects.Auction;
import ooserver.OOServerAdminFact;
import ooserver.admin.OOServerAdmin;
import ooserver.commoninterfaces.OOIMsg;
import ooserver.commoninterfaces.OOIObserver;
import ooserver.commoninterfaces.OOIPrintable;

/**
 *
 * @author alexander
 */
public class Admin implements OOIObserver {

    private final OOServerAdmin serverAdministrator;
    OOIPrintable printer;
            
    public Admin(int port, OOIPrintable printer) {
        this.printer = printer;
        
        printer.print("ServerController: Starting serverGUI on port " + port + ".");
        
        serverAdministrator = OOServerAdminFact.createObserverServerAdministrator(port, printer);
        serverAdministrator.addObserver(this);
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
                    printer.print("Se recibió una nueva oferta, se envía al subastador.");
                    AuctionMsgNewOffer newOffer = (AuctionMsgNewOffer)message.getMessage();
                    Auction auctionNewOffer = (Auction)serverAdministrator.getObservableFromServer(newOffer.getIdAuction());
                    serverAdministrator.sendMessageToClient(auctionNewOffer.getAuctioneerId(), message);
                    break;
                case AuctionMsgFactForClients.ACCEPT_OFFER:
                    printer.print("Se aceptó una oferta, se envía al postor.");
                    AuctionMsgAcceptOffer acceptOffer = (AuctionMsgAcceptOffer)message.getMessage();
                    Auction auctionAcceptOffer = (Auction)serverAdministrator.getObservableFromServer(acceptOffer.getIdAuction());
                    serverAdministrator.sendMessageToClient(auctionAcceptOffer.getAuctioneerId(), message);
                    break;
                /*case MessageClientFactory.ADD_AUCTION:
                    break;
                case MessageClientFactory.FOLLOW_AUCTION:
                    break;
                case MessageClientFactory.UNFOLLOW_AUCTION:
                    break;
                case MessageClientFactory.REMOVE_AUCTION:
                    break;
                case MessageClientFactory.ADD_BIDDER:
                    break;
                case MessageClientFactory.SEND_ALL_AUCTIONS:
                    break;
                case MessageClientFactory.SEND_ALL_BIDDERS:
                    break;*/
                case AuctionMsgFactForClients.MESSAGE_TO_BIDDER:
                    printer.print("Se recibió un mensaje para un postor, se envía al postor.");
                    AuctionMsgMessageToBidder messageToBidder = (AuctionMsgMessageToBidder)message.getMessage();
                    serverAdministrator.sendMessageToClient(messageToBidder.getIdBidder(), message);
                    break;
                case AuctionMsgFactForClients.AUCTION_FINISHED:
                    printer.print("Se terminó una subasta, se envía mensaje al ganador.");
                    AuctionMsgAuctionFinished auctionFinished = (AuctionMsgAuctionFinished)message.getMessage();
                    ((Auction)serverAdministrator.getObservableFromServer(auctionFinished.getIdAuction())).setState(Auction.STATE.FINISHED);
                    serverAdministrator.sendMessageToClient(auctionFinished.getIdWinnerBidder(), message);
                    break;
                /*case MessageClientFactory.CLOSE_CONNECTION:
                    break;*/
                default:
                    break;
            }
    }
    
}
