/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions;

import auctions.messages.AuctionsMsg;
import java.io.Serializable;
import ooserver.OOServerMsgFact;
import ooserver.commoninterfaces.OOIMsg;

/**
 *
 * @author aborbon
 */
public class AuctionsMsgFactForServer extends OOServerMsgFact {
    public static final int MESSAGE_TO_BIDDER = TEXT_MESSAGE_TO_OBSERVER;
    public static final int SENDING_ALL_AUCTIONS = OBSERVABLES_LIST;
    public static final int SENDING_ALL_BIDDERS = OBSERVERS_LIST;
    public static final int SENDING_AUCTION = SENDING_OBSERVABLE;
    public static final int SENDING_BIDDER = SENDING_OBSERVER;
    public static final int MESSAGE_TO_AUCTIONEER = TEXT_MESSAGE_TO_OBSERVER;
    
    public static AuctionsMsg createMsg() {
        return new AuctionsMsg();
    }
    
    public static AuctionsMsg createMsg(OOIMsg message) {
        return new AuctionsMsg(message);
    }
    
    public static AuctionsMsg createMsg(int type, Serializable mensaje) {
        return new AuctionsMsg(type, mensaje);
    }
    
    public static AuctionsMsg createMsg(String id, int type, Serializable mensaje) {
        return new AuctionsMsg(id, type, mensaje);
    }
    
}
