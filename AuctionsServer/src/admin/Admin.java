/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import auctions.AuctionsMsgFactForClients;
import auctions.AuctionsServerAdminFact;
import auctions.admin.AuctionsServerAdmin;
import auctions.interfaces.AuctionsIObserver;
import auctions.interfaces.AuctionsIPrintable;
import auctions.messages.AuctionsMsg;
import auctions.messages.AuctionsMsgAcceptOffer;
import auctions.messages.AuctionsMsgAuctionFinished;
import auctions.messages.AuctionsMsgMessageToBidder;
import auctions.messages.AuctionsMsgNewOffer;
import auctions.objects.Auction;

/**
 *
 * @author alexander
 */
public class Admin implements AuctionsIObserver {

    private final AuctionsServerAdmin serverAdministrator;
    private AuctionsIPrintable printer;
            
    public Admin(int port, AuctionsIPrintable printer) {
        this.printer = printer;
        
        printer.print("Admin: " + "Starting serverGUI on port " + port + ".");
        
        serverAdministrator = AuctionsServerAdminFact.createAuctionsServerAdmin(port, printer);
        serverAdministrator.addObserver(this);
    }

    @Override
    public void update(Object message) {
        if (message instanceof AuctionsMsg) {
            messageReceived((AuctionsMsg)message);
        }
    }
    
    private void messageReceived(AuctionsMsg message) {
        switch(message.getType()) {
            case AuctionsMsgFactForClients.NEW_OFFER:
                printer.print("Admin: " + "Se recibió una nueva oferta, se envía al subastador.");
                AuctionsMsgNewOffer newOffer = (AuctionsMsgNewOffer)message.getMessage();
                Auction auctionNewOffer = (Auction)serverAdministrator.getObservableFromServer(newOffer.getIdAuction());
                serverAdministrator.sendMessageToClient(auctionNewOffer.getAuctioneerId(), message);
                break;
            case AuctionsMsgFactForClients.ACCEPT_OFFER:
                printer.print("Admin: " + "Se aceptó una oferta, se envía al postor.");
                AuctionsMsgAcceptOffer acceptOffer = (AuctionsMsgAcceptOffer)message.getMessage();
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
            case AuctionsMsgFactForClients.MESSAGE_TO_BIDDER:
                printer.print("Admin: " + "Se recibió un mensaje para un postor, se envía al postor.");
                AuctionsMsgMessageToBidder messageToBidder = (AuctionsMsgMessageToBidder)message.getMessage();
                serverAdministrator.sendMessageToClient(messageToBidder.getIdBidder(), message);
                break;
            case AuctionsMsgFactForClients.AUCTION_FINISHED:
                printer.print("Admin: " + "Se terminó una subasta, se envía mensaje al ganador.");
                AuctionsMsgAuctionFinished auctionFinished = (AuctionsMsgAuctionFinished)message.getMessage();
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
