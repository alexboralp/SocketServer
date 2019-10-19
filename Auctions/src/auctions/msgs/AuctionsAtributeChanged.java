/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions.msgs;

import auctions.interfaces.AuctionsINameable;
import auctions.interfaces.AuctionsIObjectable;

/**
 *
 * @author aborbon
 */
public class AuctionsAtributeChanged implements AuctionsINameable, AuctionsIObjectable {
    
    private String name;
    private Object object;

    public AuctionsAtributeChanged(String name, Object object) {
        this.name = name;
        this.object = object;
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
    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public Object getObject() {
        return object;
    }
    
}
