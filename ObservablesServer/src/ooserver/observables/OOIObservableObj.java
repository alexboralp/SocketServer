/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooserver.observables;

import ooserver.commoninterfaces.OOIIdable;
import ooserver.commoninterfaces.OOIObjectable;
import ooserver.commoninterfaces.OOIObservable;
import ooserver.commoninterfaces.OOIOwnerable;
import ooserver.commoninterfaces.OOISerializableIdable;
import ooserver.observers.OOIObserverObj;

/**
 *
 * @author alexander
 * @param <T>
 */
public interface OOIObservableObj<T extends OOISerializableIdable> extends OOIObservable<OOIObserverObj>, OOIIdable, OOIOwnerable, OOIObjectable<T> {
    public void removeObserver(String id);
}
