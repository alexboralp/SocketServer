/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooserver.client;

import ooserver.observermsg.OOAbsSendableObj;

/**
 *
 * @author aborbon
 */
public abstract class OOAbsSimpleClient extends OOAbsSendableObj implements OOISimpleClient {
    private String name;
    
    public OOAbsSimpleClient(String name) {
        super("");
        this.name = name;
    }
    
    public OOAbsSimpleClient(String id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "OOAbsSimpleClient{" + "name=" + name + super.toString() + '}';
    }
    
    
}
