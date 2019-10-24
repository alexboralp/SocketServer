/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vip.objects;

import ooserver.observers.OOAbsObserverObj;
import ooserver.observers.OOIObserverObj;

/**
 *
 * @author aborbon
 */
public class VIPObserverObj extends OOAbsObserverObj<VIPFamous> {

    public VIPObserverObj(String ownerId, VIPFamous object) {
        super(ownerId, object);
    }
    
    public VIPObserverObj(OOIObserverObj obj) {
        super(obj.getOwnerId(), (VIPFamous)obj.getObject());
    }

    @Override
    public void update(Object message) {
        
    }
    
}
