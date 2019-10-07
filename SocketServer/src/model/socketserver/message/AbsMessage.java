/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.socketserver.message;

import java.io.Serializable;

/**
 *
 * @author alexander
 * @param <T>
 */
public class AbsMessage<T extends Serializable> implements Serializable, IMessage<T>{
    // Constantes principales del mensaje
    public static final int MESSAGE_FROM_SERVER = 0;
    public static final int MESSAGE_FROM_CLIENT = 1;
    
    // Constantes del tipo de mensaje que se envía
    public static final int NEW_CLIENT = 0;
    public static final int CONNECTION_LOST = 1;
    public static final int CLOSE_CONNECTION = 2;
    public static final int INFO = 3;
    
    protected String id;
    protected int type;
    protected int secondaryType;
    protected T message;

    public AbsMessage() {
        id = "";
        type = 0;
        secondaryType = 0;
        message = null;
    }

    public AbsMessage(String id, int type, int secondaryType, T message) {
        this.id = id;
        this.type = type;
        this.secondaryType = secondaryType;
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
    public int getSecondaryType() {
        return secondaryType;
    }

    @Override
    public void setSecondaryType(int secondaryType) {
        this.secondaryType = secondaryType;
    }

    @Override
    public T getMensaje() {
        return message;
    }

    @Override
    public void setMensaje(T mensaje) {
        this.message = mensaje;
    }
}
