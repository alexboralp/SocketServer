/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vip.msgs;

import java.io.Serializable;

/**
 *
 * @author aborbon
 */
public class VIPMsgLike implements Serializable {
    private String famousId;
    private String msgId;

    public VIPMsgLike(String famousId, String msgId) {
        this.famousId = famousId;
        this.msgId = msgId;
    }

    public String getFamousId() {
        return famousId;
    }

    public void setFamousId(String famousId) {
        this.famousId = famousId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
    
    
}
