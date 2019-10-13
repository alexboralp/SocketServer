/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssclient;

import java.io.Serializable;
import ssserver.msg.SSMsg;
import ssserver.msg.SSIMsg;
import ssserver.client.SSIClient;
import ssserver.msg.SSMsgToClient;

/**
 *
 * @author alexander
 */
public class SSClientMsgFact {
    
    // Constantes principales del mensaje
    public static final int CLOSE_CONNECTION = 0;
    public static final int INFO = -1;
    public static final int MESSAGE_RECEIVED = -2;
    public static final int SEND_MESSAGE_TO_CLIENT = -3;
    
    public static SSIMsg createMsg() {
        return new SSMsg();
    }
    
    public static SSIMsg createMsg(int type, Serializable message) {
        SSIMsg mes = new SSMsg();
        mes.setType(type);
        mes.setMessage(message);
        return mes;
    }
    
    public static SSIMsg createMsg(SSIClient client, int type, Serializable message) {
        return new SSMsg(client.getId(), type, message);
    }
    
    public static SSMsgToClient createMsgToClient(String clientId, String message) {
        return new SSMsgToClient(clientId, message);
    }
}
