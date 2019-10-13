/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver;

import java.io.Serializable;
import ssserver.msg.SSMsg;
import ssserver.msg.SSIMsg;

/**
 *
 * @author alexander
 */
public class SSServerMsgFact {
    
    // Constantes principales del mensaje
    public static final int CLOSE_CONNECTION = 0;
    public static final int INFO = 1;
    public static final int CHECKING_CONNECTION = 2;
    public static final int RESENDING_MESSAGE_FROM_CLIENT = 3;
    
    public static SSIMsg createMsg() {
        return new SSMsg();
    }
    
    public static SSIMsg createMsg(int type, Serializable message) {
        SSIMsg mes = new SSMsg();
        mes.setType(type);
        mes.setMessage(message);
        return mes;
    }
}
