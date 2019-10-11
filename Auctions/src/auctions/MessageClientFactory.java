/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions;

import auctions.messages.MsgAcceptOffer;
import auctions.messages.MsgAuctionFinished;
import auctions.messages.MsgMessageToBidder;
import auctions.messages.MsgNewOffer;
import observerclient.ObserverClientMessageFactory;

/**
 *
 * @author aborbon
 */
public class MessageClientFactory extends ObserverClientMessageFactory {
    
    public static final int NEW_OFFER = -200;
    public static final int ACCEPT_OFFER = -201;
    public static final int ADD_AUCTION = ADD_OBSERVABLE;
    public static final int FOLLOW_AUCTION = FOLLOW_OBSERVABLE;
    public static final int UNFOLLOW_AUCTION = UNFOLLOW_OBSERVABLE;
    public static final int REMOVE_AUCTION = REMOVE_OBSERVABLE;
    public static final int ADD_BIDDER = ADD_OBSERVER;
    public static final int MESSAGE_TO_BIDDER = -202;
    public static final int AUCTION_FINISHED = -203;
    public static final int CANCEL_AUCTION = -204;
    public static final int SEND_ALL_AUCTIONS = SEND_ALL_OBSERVABLES;
    public static final int SEND_ALL_BIDDERS = SEND_ALL_OBSERVERS;
    
    public static MsgMessageToBidder createMessageToBidder(String idAuction, String idBidder, String message) {
        return new MsgMessageToBidder(idAuction, idBidder, message);
    }
    
    public static MsgAuctionFinished createAuctionFinished(String idAuction, String idBidder, String messageToWinner) {
        return new MsgAuctionFinished(idAuction, idBidder, messageToWinner);
    }
    
    public static MsgNewOffer createNewOffer(String idAuction, double newOffer) {
        return new MsgNewOffer(idAuction, newOffer);
    }
    
    public static MsgAcceptOffer createAcceptOffer(String idAuction, String idBidder, double newPrice) {
        return new MsgAcceptOffer(idAuction,idBidder, newPrice);
    }
}
