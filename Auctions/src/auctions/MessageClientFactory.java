/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions;

import auctions.messages.AuctionFinished;
import auctions.messages.MessageToBidder;
import auctions.messages.NewOffer;
import observerclient.ObserverMessageFactory;

/**
 *
 * @author aborbon
 */
public class MessageClientFactory extends ObserverMessageFactory {
    
    public static final int NEW_OFFER = 8;
    public static final int ACCEPT_OFFER = 9;
    public static final int ADD_AUCTION = ADD_OBSERVABLE;
    public static final int FOLLOW_AUCTION = FOLLOW_OBSERVABLE;
    public static final int UNFOLLOW_AUCTION = UNFOLLOW_OBSERVABLE;
    public static final int REMOVE_AUCTION = REMOVE_OBSERVABLE;
    public static final int ADD_BIDDER = ADD_OBSERVER;
    public static final int MESSAGE_TO_BIDDER = 10;
    public static final int AUCTION_FINISHED = 11;
    
    public static MessageToBidder createMessageToBidder(String idAuction, String idBidder, String message) {
        return new MessageToBidder(idAuction, idBidder, message);
    }
    
    public static AuctionFinished createAuctionFinished(String idAuction, String idBidder, String messageToWinner) {
        return new AuctionFinished(idAuction, idBidder, messageToWinner);
    }
    
    public static NewOffer createNewOffer(String idAuction, double newOffer) {
        return new NewOffer(idAuction, newOffer);
    }
}
