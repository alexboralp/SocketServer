/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions.objects;



/**
 *
 * @author alexander
 */
public class AuctionObserverObjectFactory {
    public static AuctionObserverObject create(String ownerId, Auction object) {
        return new AuctionObserverObject(ownerId, object);
    }
}
