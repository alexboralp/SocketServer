/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooserver.observermsg;

import ooserver.commoninterfaces.OOISerializableIdable;

/**
 *
 * @author alexander
 */
public class OOSendableObj implements OOISerializableIdable {

    protected String id;

    public OOSendableObj() {
        this.id = "";
    }
    
    public OOSendableObj(String id) {
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
