/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooserver.observermsg;

import java.io.Serializable;
import ssserver.msg.SSAbsMsg;
import ssserver.msg.SSMsg;

/**
 *
 * @author alexander
 * @param <T>
 */
public class OOMsg <T extends Serializable> extends SSMsg<T>{

    public OOMsg() {
    }

    public OOMsg(int type, T mensaje) {
        super(type, mensaje);
    }

    public OOMsg(String id, int type, T mensaje) {
        super(id, type, mensaje);
    }
    
}
