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
import vip.msgs.VIPMsgLike;

/**
 *
 * @author aborbon
 */
public class VIPMsgFactForClients extends OOClientMsgFact {
    
    public static final int LIKE = -200;
    public static final int DISLIKE = -201;
    public static final int FOLLOW_FAMOUS = FOLLOW_OBSERVABLE;
    public static final int UNFOLLOW_FAMOUS = UNFOLLOW_OBSERVABLE;
    public static final int SEND_ALL_FAMOUS = SEND_ALL_OBSERVABLES;
    
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
    
    public static VIPMsgLike createMsgLike(String famousId, String msgId) {
        return new VIPMsgLike(famousId, msgId);
    }
}
