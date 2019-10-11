/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketclient;

import java.io.Serializable;
import socketserver.client.IClient;
import socketserver.message.IMessage;
import socketserver.message.Message;

/**
 *
 * @author alexander
 */
public class ClientMessageFactory {
    
    // Constantes principales del mensaje
    public static final int CLOSE_CONNECTION = 0;
    public static final int INFO = 1;
    public static final int MESSAGE_RECEIVED = 2;
    
    public static IMessage createMessage() {
        return new Message();
    }
    
    public static IMessage createMessage(int type, Serializable message) {
        IMessage mes = new Message();
        mes.setType(type);
        mes.setMessage(message);
        return mes;
    }
    
    public static IMessage createMessage(IClient client, int type, Serializable message) {
        return new Message(client.getId(), type, message);
    }
}
