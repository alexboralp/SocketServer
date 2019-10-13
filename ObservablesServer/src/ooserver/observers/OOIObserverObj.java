/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooserver.observers;

import ooserver.commoninterfaces.OOIIdable;
import ooserver.commoninterfaces.OOIObjectable;
import ooserver.commoninterfaces.OOIObserver;
import ooserver.commoninterfaces.OOIOwnerable;
import ooserver.commoninterfaces.OOISerializableIdable;

/**
 *
 * @author alexander
 * @param <T>
 */
public interface OOIObserverObj<T extends OOISerializableIdable> extends OOIObserver, OOIIdable, OOIOwnerable, OOIObjectable<T>{
    
}
