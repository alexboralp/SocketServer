/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooserver.observermsg;

import java.io.Serializable;
import ooclient.OOClientMsgFact;
import ssclient.SSClientMsgFact;

/**
 *
 * @author alexander
 */
public class OOMsgToClient extends OOMsg<Serializable> {

    public OOMsgToClient(String idClient, Serializable message) {
        super(idClient, OOClientMsgFact.SEND_MESSAGE_TO_CLIENT, message);
    }
    
}
