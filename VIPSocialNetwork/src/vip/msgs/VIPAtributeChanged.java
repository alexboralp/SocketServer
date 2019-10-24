/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vip.msgs;

import vip.interfaces.VIPIObjectable;
import vip.interfaces.VIPITypeable;

/**
 *
 * @author aborbon
 */
public class VIPAtributeChanged implements VIPITypeable, VIPIObjectable {
    
    private int type;
    private Object object;

    public VIPAtributeChanged(int type, Object object) {
        this.type = type;
        this.object = object;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public Object getObject() {
        return object;
    }
    
}
