/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver.msg;

import java.io.Serializable;
import ssclient.SSClientMsgFact;

/**
 *
 * @author alexander
 */
public class SSMsgToClient extends SSMsg<Serializable> {

    public SSMsgToClient(String idClient, Serializable message) {
        super(idClient, SSClientMsgFact.SEND_MESSAGE_TO_CLIENT, message);
    }
    
}
