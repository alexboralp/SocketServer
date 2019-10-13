/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooserver.observers;

import ooserver.commoninterfaces.OOISerializableIdable;

/**
 *
 * @author aborbon
 */
public class OOObserverObj extends OOAbsObserverObj<OOISerializableIdable> {

    public OOObserverObj(String owner, OOISerializableIdable object) {
        super(owner, object);
    }

    @Override
    public void update(Object message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
