/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketserver.client;

import java.util.HashMap;
import socketserver.patterns.observer.IObserver;
import socketserver.commoninterfaces.IList;

/**
 *
 * @author alexander
 */
public class Clients implements IList<IClient>, IObserver{
    private HashMap<String,IClient> clients;

    @Override
    public void add(IClient client) {
        clients.put(client.getId(), client);
    }

    @Override
    public void remove(IClient client) {
        clients.remove(client.getId());
    }
    
    public void cleanClients() {
        for (IClient client: clients.values()) {
            if (!client.isOk()) {
                clients.remove(client.getId());
            }
        }
    }

    @Override
    public void update(Object message) {
        if (message instanceof IClient) {
            add((IClient)message);
        }
    }
    
}
