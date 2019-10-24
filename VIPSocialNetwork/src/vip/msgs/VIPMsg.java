/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vip.msgs;

import java.io.Serializable;
import ooserver.msg.OOMsg;
import ssserver.msg.SSIMsg;
import vip.interfaces.VIPIMsg;

/**
 *
 * @author alexander
 */
public class VIPMsg extends OOMsg implements VIPIMsg {
    
    public VIPMsg(SSIMsg message) {
        super(message);
    }

    public VIPMsg() {
    }

    public VIPMsg(int type, Serializable mensaje) {
        super(type, mensaje);
    }

    public VIPMsg(String id, int type, Serializable mensaje) {
        super(id, type, mensaje);
    }

    @Override
    public String toString() {
        return "AuctionsMsg{" + super.toString() + '}';
    }
    
    
    
}
