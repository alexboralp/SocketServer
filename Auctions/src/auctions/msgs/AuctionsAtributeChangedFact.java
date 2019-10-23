/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions.msgs;

/**
 *
 * @author aborbon
 */
public class AuctionsAtributeChangedFact {
    public static final int NEW_AUCTION = 1;
    public static final int NEW_BID_ALLREADY_MADE = 2;
    public static final int NEW_MESSAGE = 3;
    public static final int DELETE_ALL_AUCTIONS = 4;
    public static final int DELETE_AUCTION = 5;
    public static final int FOLLOW_AUCTION = 6;
    public static final int UNFOLLOW_AUCTION = 7;
    
    
    public static final int NEW_OFFER = 8;
    public static final int ACCEPT_OFFER = 9;
    
    
    public static AuctionsAtributeChanged createAuctionsAtributeChanged(int type, Object object) {
        return new AuctionsAtributeChanged(type, object);
    }
}
