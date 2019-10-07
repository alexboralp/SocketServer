/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketserver.message;

import java.io.Serializable;

/**
 *
 * @author aborbon
 * @param <T>
 */
public class Message<T extends Serializable> extends AbsMessage<T> {

    public Message() {
        super();
    }

    public Message(int type, T mensaje) {
        super("", type, mensaje);
    }

    public Message(String id, int type, T mensaje) {
        super(id, type, mensaje);
    }
    
}
