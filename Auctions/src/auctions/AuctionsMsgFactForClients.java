/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions;

import auctions.msgs.AuctionsMsg;
import auctions.msgs.AuctionsMsgAcceptOffer;
import auctions.msgs.AuctionsMsgAuctionFinished;
import auctions.msgs.AuctionsMsgMessageToBidder;
import auctions.msgs.AuctionsMsgNewOffer;
import java.io.Serializable;
import ooclient.OOClientMsgFact;
import ooserver.commoninterfaces.OOIMsg;

/**
 *
 * @author aborbon
 */
public class AuctionsMsgFactForClients extends OOClientMsgFact {
    
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
    
    public static AuctionsMsgMessageToBidder createMessageToBidder(String idAuction, String idBidder, String message) {
        return new AuctionsMsgMessageToBidder(idAuction, idBidder, message);
    }
    
    public static AuctionsMsgAuctionFinished createAuctionFinished(String idAuction, String idBidder, String messageToWinner) {
        return new AuctionsMsgAuctionFinished(idAuction, idBidder, messageToWinner);
    }
    
    public static AuctionsMsgNewOffer createNewOffer(String idAuction, String idBidder, double newOffer) {
        return new AuctionsMsgNewOffer(idAuction, idBidder, newOffer);
    }
    
    public static AuctionsMsgAcceptOffer createAcceptOffer(String idAuction, String idBidder, double newPrice) {
        return new AuctionsMsgAcceptOffer(idAuction,idBidder, newPrice);
    }
    
    public static AuctionsMsg createMsg() {
        return new AuctionsMsg();
    }
    
    public static AuctionsMsg createMsg(OOIMsg message) {
        return new AuctionsMsg(message);
    }
    
    public static AuctionsMsg createMsg(int type, Serializable message) {
        return new AuctionsMsg(type, message);
    }
    
    public static AuctionsMsg createMsg(String id, int type, Serializable message) {
        return new AuctionsMsg(id, type, message);
    }
    
}
