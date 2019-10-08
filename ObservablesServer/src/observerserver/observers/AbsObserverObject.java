/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerserver.observers;

import socketserver.commoninterfaces.IIdable;

/**
 *
 * @author alexander
 * @param <T>
 */
public abstract class AbsObserverObject<T extends IIdable> implements IObserverObject<T>{
    protected T object;

    public AbsObserverObject(T object) {
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

    public void setObject(T object) {
        this.object = object;
    }
    
    
}
