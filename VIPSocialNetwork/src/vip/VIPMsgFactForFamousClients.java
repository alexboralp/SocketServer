/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vip;

import vip.msgs.VIPMsg;
import java.io.Serializable;
import ooclient.OOClientMsgFact;
import ooserver.commoninterfaces.OOIMsg;

/**
 *
 * @author aborbon
 */
public class VIPMsgFactForFamousClients extends OOClientMsgFact {
    
    public static final int NEW_FAMOUS = ADD_OBSERVABLE;
    public static final int NEW_MESSAGE = -301;
    public static final int LEFT_SOCIAL_NETWORK = REMOVE_OBSERVABLE;
    
    public static VIPMsg createMsg() {
        return new VIPMsg();
    }
    
    public static VIPMsg createMsg(OOIMsg message) {
        return new VIPMsg(message);
    }
    
    public static VIPMsg createMsg(int type, Serializable message) {
        return new VIPMsg(type, message);
    }
    
    public static VIPMsg createMsg(String id, int type, Serializable message) {
        return new VIPMsg(id, type, message);
    }
    
}
