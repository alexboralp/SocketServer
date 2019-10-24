/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vip.objects;

import ooserver.observers.OOIObserverObj;



/**
 *
 * @author alexander
 */
public class VIPObserverObjFact {
    public static VIPObserverObj create(String ownerId, VIPFamous object) {
        return new VIPObserverObj(ownerId, object);
    }
    
    public static VIPObserverObj create(OOIObserverObj obj) {
        return new VIPObserverObj(obj);
    }
}
