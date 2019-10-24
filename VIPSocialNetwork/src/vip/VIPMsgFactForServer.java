/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vip;

import vip.msgs.VIPMsg;
import java.io.Serializable;
import ooserver.OOServerMsgFact;
import ooserver.commoninterfaces.OOIMsg;

/**
 *
 * @author aborbon
 */
public class VIPMsgFactForServer extends OOServerMsgFact {
    public static final int SENDING_ALL_FAMOUS = OBSERVABLES_LIST;
    public static final int SENDING_FAMOUS = SENDING_OBSERVABLE;
    public static final int SENDING_MESSAGE = 200;
    public static final int FAMOUS_ID_REPEATED = 201;
    public static final int MESSAGE_ID_REPEATED = 202;
    public static final int SHOW_MESSAGE = TEXT_MESSAGE;
    
    public static VIPMsg createMsg() {
        return new VIPMsg();
    }
    
    public static VIPMsg createMsg(OOIMsg message) {
        return new VIPMsg(message);
    }
    
    public static VIPMsg createMsg(int type, Serializable mensaje) {
        return new VIPMsg(type, mensaje);
    }
    
    public static VIPMsg createMsg(String id, int type, Serializable mensaje) {
        return new VIPMsg(id, type, mensaje);
    }
    
}
