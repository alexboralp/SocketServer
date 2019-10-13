/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooserver.observables;

import ooserver.commoninterfaces.OOISerializableIdable;
import ooserver.observers.OOIObserverObj;

/**
 *
 * @author alexander
 */
public class OOObservableObj extends OOAbsObservableObj{

    public OOObservableObj(String ownerId, OOISerializableIdable object) {
        super(ownerId, object);
    }

    @Override
    public void update(OOIObserverObj observer, Object message) {
        
    }

    
    
}
