/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver.msg;

import java.io.Serializable;

/**
 *
 * @author aborbon
 * @param <T>
 */
public class SSMsg<T extends Serializable> extends SSAbsMsg<T> {

    public SSMsg() {
        super();
    }

    public SSMsg(int type, T mensaje) {
        super("", type, mensaje);
    }

    public SSMsg(String id, int type, T mensaje) {
        super(id, type, mensaje);
    }
    
}
