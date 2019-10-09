/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions.objects;

import java.io.Serializable;
import socketserver.commoninterfaces.IIdable;

/**
 *
 * @author alexander
 */
public class Bidder implements IIdable, Serializable {

    private String id;
    
    public Bidder() {
    }

    public Bidder(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
