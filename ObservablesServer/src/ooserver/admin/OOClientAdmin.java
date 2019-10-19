/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooserver.admin;

import java.io.Serializable;
import ooclient.OOClientMsgFact;
import ooserver.client.OOISimpleClient;
import ooserver.commoninterfaces.OOIMsg;
import ooserver.commoninterfaces.OOIPrintable;
import ooserver.commoninterfaces.OOISerializableIdable;
import ooserver.msg.OOClientMsgHandler;
import ssserver.admin.SSClientAdmin;

/**
 *
 * @author alexander
 */
public class OOClientAdmin extends SSClientAdmin {
    protected String id;

    public OOClientAdmin(String serverName, int port, OOIPrintable printer) {
        super(serverName, port, printer);
        this.printer = printer;
        msgHandler = new OOClientMsgHandler(printer, this);
        this.replaceMsgHandler(msgHandler);
    }

    public OOClientAdmin(String serverName, int port, OOIPrintable printer, OOClientMsgHandler msgHandler) {
        super(serverName, port, printer, msgHandler);
        this.printer = printer;
    }
    
    public void addMeAsObserver(OOISimpleClient client) {
        sendMessage(OOClientMsgFact.createMsg(OOClientMsgFact.ADD_ME_TO_OBSERVERS, client));
    }
    
    public void addNewObserver(OOISerializableIdable observer) {
        sendMessage(OOClientMsgFact.createMsg(OOClientMsgFact.ADD_OBSERVER, observer));
    }
    
    public void addNewObservable(OOISerializableIdable observable) {
        sendMessage(OOClientMsgFact.createMsg(OOClientMsgFact.ADD_OBSERVABLE, observable));
    }
    
    public void removeObservable(String idObservable) {
        sendMessage(OOClientMsgFact.createMsg(OOClientMsgFact.ADD_OBSERVABLE, idObservable));
    }
    
    public void followObservable(String idObservable) {
        sendMessage(OOClientMsgFact.createMsg(OOClientMsgFact.FOLLOW_OBSERVABLE, idObservable));
    }
    
    public void unfollowObservable(String idObservable) {
        sendMessage(OOClientMsgFact.createMsg(OOClientMsgFact.UNFOLLOW_OBSERVABLE, idObservable));
    }
    
    public void removeMeFromObservers() {
        sendMessage(OOClientMsgFact.createMsg(OOClientMsgFact.REMOVE_ME_FROM_OBSERVERS, null));
    }
    
    public void sendInfoServer(Serializable info) {
        sendMessage(OOClientMsgFact.createMsg(OOClientMsgFact.INFO, info));
    }
    
    public void sendAllObservers() {
        sendMessage(OOClientMsgFact.createMsg(OOClientMsgFact.SEND_ALL_OBSERVERS, null));
    }
    
    public void sendAllObservables() {
        sendMessage(OOClientMsgFact.createMsg(OOClientMsgFact.SEND_ALL_OBSERVABLES, null));
    }
    
    public void sendMyId() {
        sendMessage(OOClientMsgFact.createMsg(OOClientMsgFact.SEND_MY_ID, null));
    }
    
    public void sendMessage(OOIMsg message) {
        super.sendMessage(message);
    }
    
}
