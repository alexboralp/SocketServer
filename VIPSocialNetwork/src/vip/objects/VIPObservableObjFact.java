/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vip.objects;

import ooserver.commoninterfaces.OOISerializableIdable;
import ooserver.observables.OOIObservableObj;

/**
 *
 * @author alexander
 */
public class VIPObservableObjFact {
    public static VIPObservableObj createAuctionsObservableObj(String ownerId, OOISerializableIdable object) {
        return new VIPObservableObj(ownerId, object);
    }
    
    public static VIPObservableObj createAuctionsObservableObj(OOIObservableObj obj) {
        return new VIPObservableObj(obj);
    }
}
