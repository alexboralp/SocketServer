/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerserver.observers;

import java.util.Collection;
import java.util.HashMap;
import observerserver.commoninterfaces.IListId;
import socketserver.client.IClient;

/**
 *
 * @author alexander
 */
public class Observers implements IListId<IObserverObject<IClient>> {

    private HashMap<String, IObserverObject<IClient>> observers;
    
    public Collection<IObserverObject<IClient>> getObservers() {
        return observers.values();
    }
    
    @Override
    public void add(IObserverObject<IClient> element) {
        observers.put(element.getId(), element);
    }

    @Override
    public void remove(IObserverObject<IClient> element) {
        observers.remove(element.getId());
    }

    @Override
    public void remove(String id) {
        observers.remove(id);
    }

    @Override
    public IObserverObject<IClient> get(String id) {
        return observers.get(id);
    }

    @Override
    public boolean containsKey(String id) {
        return observers.containsKey(id);
    }
    
}
