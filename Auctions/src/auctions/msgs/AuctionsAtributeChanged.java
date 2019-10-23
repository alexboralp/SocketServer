/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions.msgs;

import auctions.interfaces.AuctionsIObjectable;
import auctions.interfaces.AuctionsITypeable;

/**
 *
 * @author aborbon
 */
public class AuctionsAtributeChanged implements AuctionsITypeable, AuctionsIObjectable {
    
    private int type;
    private Object object;

    public AuctionsAtributeChanged(int type, Object object) {
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
