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
public class Client implements IIdable, Serializable {

    private String id;
    private String name;
    
    public Client() {
        id = "";
        name = "";
    }

    public Client(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
