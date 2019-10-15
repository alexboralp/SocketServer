/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver.msg;

import java.io.Serializable;

/**
 *
 * @author alexander
 * @param <T>
 */
public class SSAbsMsg<T extends Serializable> implements Serializable, SSIMsg<T>{
    
    protected String id;
    protected int type;
    protected T message;

    public SSAbsMsg() {
        id = "";
        type = 0;
        message = null;
    }

    public SSAbsMsg(String id, int type, T message) {
        this.id = id;
        this.type = type;
        this.message = message;
    }
    
    

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
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
    public T getMessage() {
        return message;
    }

    @Override
    public void setMessage(T message) {
        this.message = message;
    }

    @Override
    public String toString() {
        String msg;
        if (message == null) {
            msg = "null";
        } else {
            msg = message.toString();
        }
        return "SSAbsMessage{" + "id=" + id + ", type=" + type + ", message=" + msg + '}';
    }
    
    
}
