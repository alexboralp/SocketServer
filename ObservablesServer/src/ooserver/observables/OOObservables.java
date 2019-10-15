/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooserver.observables;

import java.util.Collection;
import java.util.HashMap;
import ooserver.commoninterfaces.OOIListId;
import ooserver.observers.OOIObserverObj;

/**
 *
 * @author alexander
 */
public class OOObservables implements OOIListId<OOIObservableObj> {
    
    protected HashMap<String, OOIObservableObj> observables;

    public OOObservables() {
        observables = new HashMap();
    }

    @Override
    public void add(OOIObservableObj element) {
        observables.put(element.getId(), element);
    }

    @Override
    public void remove(OOIObservableObj element) {
        observables.remove(element.getId());
    }

    @Override
    public void remove(String id) {
        observables.remove(id);
    }

    @Override
    public OOIObservableObj get(String id) {
        return observables.get(id);
    }

    @Override
    public boolean containsKey(String id) {
        return observables.containsKey(id);
    }
    
    public void removeObserver(OOIObserverObj observer) {
        for (OOIObservableObj observable : observables.values()) {
            observable.removeObserver(observer);
        }
    }
    
    public void removeObserver(String id) {
        for (OOIObservableObj observable : observables.values()) {
            observable.removeObserver(id);
        }
    }
    
    public Collection<OOIObservableObj> getObservables() {
        return observables.values();
    }

    @Override
    public String toString() {
        return "OOObservables{" + "observables=" + observables + '}';
    }
    
}
