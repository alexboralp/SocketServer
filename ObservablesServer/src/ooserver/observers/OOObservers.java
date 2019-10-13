/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooserver.observers;

import java.util.Collection;
import java.util.HashMap;
import ooserver.commoninterfaces.OOIListId;

/**
 *
 * @author alexander
 * @param <T>
 */
public class OOObservers implements OOIListId<OOIObserverObj> {

    private HashMap<String, OOIObserverObj> observers;
    
    public Collection<OOIObserverObj> getObservers() {
        return observers.values();
    }
    
    @Override
    public void add(OOIObserverObj element) {
        observers.put(element.getId(), element);
    }

    @Override
    public void remove(OOIObserverObj element) {
        observers.remove(element.getId());
    }

    @Override
    public void remove(String id) {
        observers.remove(id);
    }

    @Override
    public OOIObserverObj get(String id) {
        return observers.get(id);
    }

    @Override
    public boolean containsKey(String id) {
        return observers.containsKey(id);
    }
    
}
