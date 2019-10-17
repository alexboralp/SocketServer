/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooserver.msg;

import java.io.Serializable;
import ooserver.commoninterfaces.OOIMsg;
import ssserver.msg.SSIMsg;
import ssserver.msg.SSMsg;

/**
 *
 * @author alexander
 */
public class OOMsg extends SSMsg implements OOIMsg{

    public OOMsg(SSIMsg message) {
        this.id = message.getId();
        this.type = message.getType();
        this.message = message.getMessage();
    }

    public OOMsg() {
    }

    public OOMsg(int type, Serializable mensaje) {
        super(type, mensaje);
    }

    public OOMsg(String id, int type, Serializable mensaje) {
        super(id, type, mensaje);
    }

    @Override
    public String toString() {
        return "OOMsg{" + super.toString() + '}';
    }
    
    
    
}
