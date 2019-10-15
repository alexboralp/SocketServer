/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooserver.observers;

import ooserver.commoninterfaces.OOISerializableIdable;


/**
 *
 * @author alexander
 * @param <T>
 */
public abstract class OOAbsObserverObj<T extends OOISerializableIdable> implements OOIObserverObj<T>{
    protected T object;
    protected String ownerId;

    public OOAbsObserverObj(String owner, T object) {
        this.ownerId = owner;
        this.object = object;
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
    public T getObject() {
        return object;
    }

    @Override
    public void setObject(T object) {
        this.object = object;
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
    public abstract void update(Object message);

    @Override
    public String toString() {
        return "OOAbsObserverObj{" + "object=" + object.toString() + ", ownerId=" + ownerId + '}';
    }
    
    
    
}
