/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooclient;

import java.io.Serializable;
import ooserver.commoninterfaces.OOIMsg;
import ooserver.msg.OOMsg;
import ssclient.SSClientMsgFact;

/**
 *
 * @author alexander
 */
public class OOClientMsgFact extends SSClientMsgFact {
    
    public static final int ADD_OBSERVABLE = -100;
    public static final int REMOVE_OBSERVABLE = -101;
    public static final int FOLLOW_OBSERVABLE = -102;
    public static final int UNFOLLOW_OBSERVABLE = -103;
    public static final int REMOVE_ME_FROM_OBSERVERS = -104;
    public static final int ADD_ME_TO_OBSERVERS = -105;
    public static final int ADD_OBSERVER = -106;
    public static final int SEND_ALL_OBSERVERS = -107;
    public static final int SEND_ALL_OBSERVABLES = -108;
    public static final int SEND_MY_ID = -109;
    
    public static OOIMsg createMsg() {
        return new OOMsg(SSClientMsgFact.createMsg());
    }
    
    public static OOIMsg createMsg(int type, Serializable message) {
        return new OOMsg(type, message);
    }
}
