/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooserver.observables;

import java.util.HashMap;
import ooserver.commoninterfaces.OOISerializableIdable;
import ooserver.observers.OOIObserverObj;

/**
 *
 * @author alexander
 * @param <T>
 */
public abstract class OOAbsObservableObj<T extends OOISerializableIdable> implements OOIObservableObj<T>{
    protected HashMap<String, OOIObserverObj> observers;
    protected T object;
    protected String ownerId;

    public OOAbsObservableObj(T object) {
        this.object = object;
        observers = new HashMap();
    }

    public OOAbsObservableObj(String ownerId, T object) {
        this.ownerId = ownerId;
        this.object = object;
        observers = new HashMap();
    }

    @Override
    public String getId() {
        return object.getId();
    }

    @Override
    public void setId(String id) {
        object.setId(id);
    }

    @Override
    public String getOwnerId() {
        return ownerId;
    }

    @Override
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public T getObject() {
        return object;
    }

    @Override
    public void setObject(T object) {
        this.object = object;
    }
    
    @Override
    public void removeObserver(String id) {
        observers.remove(id);
    }

    @Override
    public void updateAll(Object message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addObserver(OOIObserverObj observer) {
        observers.put(observer.getId(), observer);
    }

    @Override
    public void removeObserver(OOIObserverObj observer) {
        observers.remove(observer.getId());
    }

    @Override
    public abstract void update(OOIObserverObj observer, Object message);
    
}
