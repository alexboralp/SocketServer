/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooserver.admin;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import ooclient.OOClientMsgFact;
import ooserver.OOServerAdminFact;
import ooserver.OOServerMsgFact;
import ooserver.client.OOISimpleClient;
import ooserver.commoninterfaces.OOIMsg;
import ooserver.commoninterfaces.OOIObservable;
import ooserver.commoninterfaces.OOIObserver;
import ooserver.commoninterfaces.OOIPrintable;
import ooserver.commoninterfaces.OOISerializableIdable;
import ooserver.observables.OOAbsObservable;
import ooserver.observables.OOObservables;
import ooserver.observers.OOObservers;
import ssserver.SSServerAdminFact;
import ssserver.admin.SSServerAdmin;
import ooserver.observers.OOIObserverObj;
import ooserver.observables.OOIObservableObj;

/**
 *
 * @author alexander
 */
public class OOServerAdmin extends OOAbsObservable implements OOIObserver {
    private final SSServerAdmin administrator;
    private final OOIPrintable printer;
    OOObservables observablesServer;
    OOObservers observersServer;

    public OOServerAdmin(int port, OOIPrintable printer) {
        administrator = SSServerAdminFact.createServerAdministrator(port, printer);
        administrator.startServerCleaning();
        administrator.addObserver(this);
        this.printer = printer;
        observablesServer = new OOObservables();
        observersServer = new OOObservers();
    }
    
    public void sendIdToClient(String idClient) {
        sendMessageToClient(idClient, OOServerMsgFact.createMsg(OOServerMsgFact.SENDING_ID_TO_OBSERVER, idClient));
    }
    
    public void sendObservableToClient(String idClient, String idObservable) {
        sendMessageToClient(idClient, OOServerMsgFact.createMsg(OOServerMsgFact.SENDING_OBSERVABLE, (Serializable)observablesServer.get(idObservable).getObject()));
    }
    
    public void sendObservableToClient(String idClient, Serializable observable) {
        sendMessageToClient(idClient, OOServerMsgFact.createMsg(OOServerMsgFact.SENDING_OBSERVABLE, observable));
    }
    
    public void sendObservablesToClient(String idClient) {
        sendMessageToClient(idClient, OOServerMsgFact.createMsg(OOServerMsgFact.OBSERVABLES_LIST, null));
        for (OOIObservableObj observable : observablesServer.getObservables()) {
            sendObservableToClient(idClient, (Serializable)observable.getObject());
        }
        sendMessageToClient(idClient, OOServerMsgFact.createMsg(OOServerMsgFact.DONE, null));
    }
    
    public void sendObserverToClient(String idClient, String idObserver) {
        sendMessageToClient(idClient, OOServerMsgFact.createMsg(OOServerMsgFact.SENDING_OBSERVER, (Serializable)observersServer.get(idObserver).getObject()));
    }
    
    public void sendObserverToClient(String idClient, Serializable observer) {
        sendMessageToClient(idClient, OOServerMsgFact.createMsg(OOServerMsgFact.SENDING_OBSERVER, observer));
    }
    
    public void sendObserversToClient(String idClient) {
        sendMessageToClient(idClient, OOServerMsgFact.createMsg(OOServerMsgFact.OBSERVERS_LIST, null));
        for (OOIObserverObj observer : observersServer.getObservers()) {
            sendObserverToClient(idClient, (OOISimpleClient)observer.getObject());
        }
        sendMessageToClient(idClient, OOServerMsgFact.createMsg(OOServerMsgFact.DONE, null));
    }
    
    public void sendTextMessageToClient(String idClient, String message) {
        sendMessageToClient(idClient, OOServerMsgFact.createMsg(OOServerMsgFact.TEXT_MESSAGE, message));
    }
    
    public void sendMessageToClient(String idClient, OOIMsg message) {
        try {
            administrator.getClient(idClient).sendMessage(message);
        } catch (IOException ex) {
            printer.printError("OOServerAdministrator: " + ex.getMessage());
        }
    }
    
    public void sendMessageToAllObservers(OOIMsg message) {
        for (OOIObserverObj observer : observersServer.getObservers()) {
            sendMessageToClient(observer.getId(), message);
        }
    }
    
    public void addObservableToServer(OOIObservableObj object) {
        observablesServer.add(object);
        printer.print("OOServerAdministrator: " + "Sending observable to all clients.");
        sendMessageToAllObservers(OOServerMsgFact.createMsg(OOServerMsgFact.SENDING_OBSERVABLE, (Serializable)object.getObject()));
    }
    
    public void removeObservableFromServer(OOIObservableObj object) {
        observablesServer.remove(object);
    }
    
    public void removeObservableFromServer(String id) {
        observablesServer.remove(id);
    }
    
    public OOIObservableObj getObservableFromServer(String id) {
        return observablesServer.get(id);
    }
    
    public void addObserverToServer(OOIObserverObj object) {
        observersServer.add(object);
    }
    
    public void removeObserverFromServer(OOIObserverObj object) {
        observersServer.remove(object.getId());
        removeObserverFromObservables(object.getId());
    }
    
    public void removeObserverFromServer(String id) {
        observersServer.remove(id);
        removeObserverFromObservables(id);
    }
    
    public OOIObserverObj getObserverFromServer(String id) {
        return observersServer.get(id);
    }
    
    public void setObserverToObservable(OOIObserverObj observer, OOIObservableObj observable) {
        if (!observersServer.containsKey(observer.getId())) {
            observersServer.add(observer);
        }
        if (!observablesServer.containsKey(observable.getId())) {
            observablesServer.add(observable);
        }
        
        observablesServer.get(observable.getId()).addObserver(observersServer.get(observer.getId()));
    }
    
    public void setObserverToObservable(String observerId, String observableId) {
        if (observersServer.containsKey(observerId) && observablesServer.containsKey(observableId)) {
            observablesServer.get(observableId).addObserver(observersServer.get(observerId));
        }
    }
    
    public void removeObserverFromObservable(OOIObserverObj observer, OOIObservableObj observable) {
        removeObserverFromObservable(observer.getId(), observable.getId());
    }
    
    public void removeObserverFromObservable(String observerId, String observableId) {
        if (observersServer.containsKey(observerId) && observablesServer.containsKey(observableId)) {
            observablesServer.get(observableId).removeObserver(observersServer.get(observerId));
        }
    }
    
    private void removeObserverFromObservables(String id) {
        for (OOIObservable observable : observablesServer.getObservables()) {
            observable.removeObserver(this);
        }
    }

    @Override
    public void update(Object message) {
        if (message instanceof OOIMsg) {
            printer.print("OOServerAdministrator: OOIMsg message received");
            messageReceived((OOIMsg)message);
        } else {
            printer.print("OOServerAdministrator: Non OOIMsg message received");
        }
        printer.print("OOServerAdministrator: Sending message to all observers");
        updateAll(message);
    }
    
    private void messageReceived(OOIMsg message) {
        printer.print("OOServerAdministrator: Message received: " + message.toString());
        switch (message.getType()) {
            case OOClientMsgFact.ADD_OBSERVABLE:
                printer.print("OOServerAdministrator: ADD_OBSERVABLE.");
                OOIObservableObj newObservable = OOServerAdminFact.createObservableObj(message.getId(), (OOISerializableIdable)message.getMessage());
                addObservableToServer(newObservable);
                break;
            case OOClientMsgFact.REMOVE_OBSERVABLE:
                printer.print("OOServerAdministrator: REMOVE_OBSERVABLE.");
                removeObservableFromServer((String)message.getMessage());
                break;
            case OOClientMsgFact.FOLLOW_OBSERVABLE:
                printer.print("OOServerAdministrator: FOLLOW_OBSERVABLE.");
                setObserverToObservable(message.getId(), (String)message.getMessage());
                break;
            case OOClientMsgFact.UNFOLLOW_OBSERVABLE:
                printer.print("OOServerAdministrator: UNFOLLOW_OBSERVABLE.");
                removeObserverFromObservable(message.getId(), (String)message.getMessage());
                break;
            case OOClientMsgFact.REMOVE_ME_FROM_OBSERVERS:
                printer.print("OOServerAdministrator: REMOVE_ME_FROM_OBSERVERS.");
                removeObserverFromServer(message.getId());
                break;
            case OOClientMsgFact.ADD_ME_TO_OBSERVERS:
                printer.print("OOServerAdministrator: ADD_ME_TO_OBSERVERS.");
                OOIObserverObj newObserverClient = OOServerAdminFact.createObserverObj(message.getId(), (OOISimpleClient)message.getMessage());
                addObserverToServer(newObserverClient);
                break;
            case OOClientMsgFact.ADD_OBSERVER:
                printer.print("OOServerAdministrator: ADD_OBSERVER.");
                OOIObserverObj newObserver = OOServerAdminFact.createObserverObj(message.getId(), (OOISerializableIdable)message.getMessage());
                addObserverToServer(newObserver);
                break;
            case OOClientMsgFact.SEND_ALL_OBSERVERS:
                printer.print("OOServerAdministrator: SEND_ALL_OBSERVERS.");
                sendObserversToClient(message.getId());
                break;
            case OOClientMsgFact.SEND_ALL_OBSERVABLES:
                printer.print("OOServerAdministrator: SEND_ALL_OBSERVABLES.");
                sendObservablesToClient(message.getId());
                break;
            case OOClientMsgFact.SEND_MY_ID:
                printer.print("OOServerAdministrator: SEND_MY_ID.");
                sendIdToClient(message.getId());
                break;
            default:
                break;
        }
    }
    
    
}
