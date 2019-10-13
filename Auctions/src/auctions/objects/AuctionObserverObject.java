/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions.objects;

import ooserver.observers.OOAbsObserverObj;

/**
 *
 * @author aborbon
 */
public class AuctionObserverObject extends OOAbsObserverObj<Auction> {

    public AuctionObserverObject(String ownerId, Auction object) {
        super(ownerId, object);
    }

    @Override
    public void update(Object message) {
        
    }
    
}
