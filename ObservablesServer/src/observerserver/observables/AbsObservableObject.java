/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerserver.observables;

import java.io.Serializable;
import observerserver.observers.IObserverObject;
import socketserver.commoninterfaces.IIdable;
import socketserver.patterns.observer.AbsObservable;
import socketserver.patterns.observer.IObserver;

/**
 *
 * @author alexander
 * @param <T>
 */
public abstract class AbsObservableObject<T extends IIdable & Serializable> extends AbsObservable implements IObservableObject<T>{
    protected T object;

    public AbsObservableObject(T object) {
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
    public void removeObserver(String id) {
        for (IObserver observer : observers) {
            IObserverObject obs = (IObserverObject) observer;
            if (id.equals(obs.getId())){
                observers.remove(obs);
            }
        }
    }
    
}
