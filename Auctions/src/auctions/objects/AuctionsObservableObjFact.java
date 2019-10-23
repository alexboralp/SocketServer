/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions.objects;

import ooserver.commoninterfaces.OOISerializableIdable;
import ooserver.observables.OOIObservableObj;

/**
 *
 * @author alexander
 */
public class AuctionsObservableObjFact {
    public static AuctionsObservableObj createAuctionsObservableObj(String ownerId, OOISerializableIdable object) {
        return new AuctionsObservableObj(ownerId, object);
    }
    
    public static AuctionsObservableObj createAuctionsObservableObj(OOIObservableObj obj) {
        return new AuctionsObservableObj(obj);
    }
}
