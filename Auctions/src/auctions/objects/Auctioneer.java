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
public class Auctioneer implements IIdable, Serializable {
    
    private String id;

    public Auctioneer() {
        super();
    }

    public Auctioneer(String id) {
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
