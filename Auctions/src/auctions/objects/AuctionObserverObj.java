/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions.objects;

import ooserver.observers.OOAbsObserverObj;
import ooserver.observers.OOIObserverObj;

/**
 *
 * @author aborbon
 */
public class AuctionObserverObj extends OOAbsObserverObj<Auction> {

    public AuctionObserverObj(String ownerId, Auction object) {
        super(ownerId, object);
    }
    
    public AuctionObserverObj(OOIObserverObj obj) {
        super(obj.getOwnerId(), (Auction)obj.getObject());
    }

    @Override
    public void update(Object message) {
        
    }
    
}
