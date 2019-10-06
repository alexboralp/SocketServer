/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.socketserver.message;

import java.io.Serializable;

/**
 *
 * @author aborbon
 */
public class Message implements Serializable {
    // Constantes principales del mensaje
    public static final int MESSAGE_FROM_SERVER = 0;
    public static final int MESSAGE_FROM_CLIENT = 1;
    
    // Constantes del tipo de mensaje que se envía
    public static final int NEW_CLIENT = 0;
    public static final int CONNECTION_LOST = 1;
    public static final int CLOSE_CONNECTION = 2;
    public static final int INFO = 3;
    
    // Identificación del que envía el mensaje
    private String id;
    
    // Tipo principal del mensaje
    private int type;
    
    // Tipo secundario del mensaje
    private int secondaryType;
    
    // Objeto enviado en el mensaje
    private Object mensaje;

    public Message() {
        this.id = "";
        this.type = 0;
        this.secondaryType = 0;
        this.mensaje = null;
    }

    public Message(String id, int type, int secondaryType, Object mensaje) {
        this.id = id;
        this.type = type;
        this.secondaryType = secondaryType;
        this.mensaje = mensaje;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSecondaryType() {
        return secondaryType;
    }

    public void setSecondaryType(int secondaryType) {
        this.secondaryType = secondaryType;
    }

    public Object getMensaje() {
        return mensaje;
    }

    public void setMensaje(Object mensaje) {
        this.mensaje = mensaje;
    }
    
}
