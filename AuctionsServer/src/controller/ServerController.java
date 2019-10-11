/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import auctions.MessageClientFactory;
import auctions.messages.MsgAcceptOffer;
import auctions.messages.MsgAuctionFinished;
import auctions.messages.MsgMessageToBidder;
import auctions.messages.MsgNewOffer;
import auctions.objects.Auction;
import observerserver.ObserverServerAdministratorFactory;
import observerserver.administrator.ObserverServerAdministrator;
import socketserver.commoninterfaces.IPrintable;
import socketserver.message.IMessage;
import socketserver.patterns.observer.IObserver;
import vista.ServerGUI;

/**
 *
 * @author alexander
 */
public class ServerController implements IPrintable, IObserver {

    private final ServerGUI serverGUI;
    private final ObserverServerAdministrator serverAdministrator;
            
    public ServerController(ServerGUI serverGUI, int port) {
        this.serverGUI = serverGUI;
        serverGUI.setServerController(this);
        
        print("ServerController: Starting serverGUI on port " + port + ".");
        
        serverAdministrator = ObserverServerAdministratorFactory.createObserverServerAdministrator(port, this);
        serverAdministrator.addObserver(this);
    }
    
    @Override
    public void print(String message) {
        serverGUI.print(message + "\n");
    }

    @Override
    public void printError(String message) {
        serverGUI.print("ERROR: " + message + "\n");
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
                    Auction auctionNewOffer = (Auction)serverAdministrator.getObservableFromServer(newOffer.getIdAuction());
                    serverAdministrator.sendMessageToClient(auctionNewOffer.getAuctioneerId(), message);
                    break;
                case MessageClientFactory.ACCEPT_OFFER:
                    MsgAcceptOffer acceptOffer = (MsgAcceptOffer)message.getMessage();
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
                case MessageClientFactory.MESSAGE_TO_BIDDER:
                    MsgMessageToBidder messageToBidder = (MsgMessageToBidder)message.getMessage();
                    serverAdministrator.sendMessageToClient(messageToBidder.getIdBidder(), message);
                    break;
                case MessageClientFactory.AUCTION_FINISHED:
                    MsgAuctionFinished auctionFinished = (MsgAuctionFinished)message.getMessage();
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
