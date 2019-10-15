/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooserver.client;

import java.util.HashMap;

/**
 *
 * @author aborbon
 */
public abstract class OOAbsSimpleClients implements OOISimpleClients {

    HashMap<String, OOISimpleClient> simpleClients;

    public OOAbsSimpleClients() {
        simpleClients = new HashMap();
    }
    
    @Override
    public void remove(String id) {
        simpleClients.remove(id);
    }

    @Override
    public boolean containsKey(String id) {
        return simpleClients.containsKey(id);
    }

    @Override
    public void add(OOISimpleClient element) {
        simpleClients.put(element.getId(), element);
    }

    @Override
    public void remove(OOISimpleClient element) {
        simpleClients.remove(element.getId());
    }

    @Override
    public OOISimpleClient get(String id) {
        return simpleClients.get(id);
    }

    @Override
    public String toString() {
        return "OOAbsSimpleClients{" + "simpleClients=" + simpleClients + '}';
    }
    
}
