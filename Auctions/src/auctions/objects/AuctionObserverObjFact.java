/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions.objects;

import ooserver.observers.OOIObserverObj;



/**
 *
 * @author alexander
 */
public class AuctionObserverObjFact {
    public static AuctionObserverObj create(String ownerId, Auction object) {
        return new AuctionObserverObj(ownerId, object);
    }
    
    public static AuctionObserverObj create(OOIObserverObj obj) {
        return new AuctionObserverObj(obj);
    }
}
