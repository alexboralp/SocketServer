/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooserver;

import java.io.Serializable;
import ooserver.commoninterfaces.OOIMsg;
import ooserver.msg.OOMsg;
import ssserver.SSServerMsgFact;

/**
 *
 * @author alexander
 */
public class OOServerMsgFact extends SSServerMsgFact {
    
    public static final int OBSERVABLES_LIST = 100;
    public static final int OBSERVERS_LIST = 101;
    public static final int TEXT_MESSAGE = 102;
    public static final int SENDING_OBSERVABLE = 103;
    public static final int SENDING_OBSERVER = 104;
    public static final int TEXT_MESSAGE_TO_OBSERVER = 105;
    public static final int SENDING_ID_TO_OBSERVER = 106;
    public static final int DONE = 107;
    
    public static OOIMsg createMsg() {
        return new OOMsg();
    }
    
    public static OOIMsg createMsg(int type, Serializable message) {
        return new OOMsg(type, message);
    }
}
