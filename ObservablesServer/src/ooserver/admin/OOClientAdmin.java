/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooserver.admin;

import java.io.Serializable;
import ooclient.OOClientMsgFact;
import ooserver.OOServerMsgFact;
import ooserver.client.OOISimpleClient;
import ooserver.commoninterfaces.OOIMsg;
import ooserver.commoninterfaces.OOIObserver;
import ooserver.commoninterfaces.OOIPrintable;
import ooserver.commoninterfaces.OOISerializableIdable;
import ooserver.observables.OOAbsObservable;
import ssclient.SSClientAdminFact;
import ssserver.admin.SSClientAdmin;

/**
 *
 * @author alexander
 */
public class OOClientAdmin extends OOAbsObservable implements OOIObserver {
    SSClientAdmin administrator;
    OOIPrintable printer;
    private String id;

    public OOClientAdmin(String serverName, int port, OOIPrintable printer) {
        administrator = SSClientAdminFact.createClientAdministrator(serverName, port, printer);
        administrator.addObserver(this);
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
    
    public void closeConnection() {
        sendMessage(OOClientMsgFact.createMsg(OOClientMsgFact.CLOSE_CONNECTION, null));
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
        administrator.sendMessage(message);
    }
    
    public boolean isOk() {
        return administrator.isOk();
    }

    @Override
    public void update(Object message) {
        if (message instanceof OOIMsg) {
            messageReceived((OOIMsg)message);
        }
        printer.print("ObserverClientAdministrator: Sending message to observers.");
        updateAll(message);
    }
    
    private void messageReceived(OOIMsg message) {
        printer.print("ObserverClientAdministrator: New message received from server: " + message.toString());
        switch (message.getType()) {
            case OOServerMsgFact.OBSERVABLES_LIST:
                printer.print("ObserverClientAdministrator: Se va a recibir la lista de observables.");
                break;
            case OOServerMsgFact.OBSERVERS_LIST:
                printer.print("ObserverClientAdministrator: Se va a recibir la lista de observers.");
                break;
            case OOServerMsgFact.TEXT_MESSAGE:
            case OOServerMsgFact.TEXT_MESSAGE_TO_OBSERVER:
                printer.print("ObserverClientAdministrator: Se recibió un mensaje: " + (String)message.getMessage());
                break;
            case OOServerMsgFact.SENDING_OBSERVABLE:
                printer.print("ObserverClientAdministrator: Se recibe un observable.");
                break;
            case OOServerMsgFact.SENDING_OBSERVER:
                printer.print("ObserverClientAdministrator: Se recibe un observer.");
                break;
            case OOServerMsgFact.SENDING_ID_TO_OBSERVER:
                printer.print("ObserverClientAdministrator: Se recibe el id dado por el server.");
                break;
            case OOServerMsgFact.DONE:
                printer.print("ObserverClientAdministrator: El server avisó que terminó la solicitud anterior.");
                break;
            default:
                break;
        }
    }
}
