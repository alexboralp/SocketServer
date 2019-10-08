/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerserver.observables;

import java.util.Collection;
import java.util.HashMap;
import observerserver.commoninterfaces.IListId;
import observerserver.observers.IObserverObject;

/**
 *
 * @author alexander
 */
public class Observables implements IListId<IObservableObject> {
    
    protected HashMap<String, IObservableObject> observables;

    @Override
    public void add(IObservableObject element) {
        observables.put(element.getId(), element);
    }

    @Override
    public void remove(IObservableObject element) {
        observables.remove(element.getId());
    }

    @Override
    public void remove(String id) {
        observables.remove(id);
    }

    @Override
    public IObservableObject get(String id) {
        return observables.get(id);
    }

    @Override
    public boolean containsKey(String id) {
        return observables.containsKey(id);
    }
    
    public void removeObserver(IObserverObject observer) {
        for (IObservableObject observable : observables.values()) {
            observable.removeObserver(observer);
        }
    }
    
    public void removeObserver(String id) {
        for (IObservableObject observable : observables.values()) {
            observable.removeObserver(id);
        }
    }
    
    public Collection<IObservableObject> getObservables() {
        return observables.values();
    }
    
}
