/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooserver.admin;

import java.io.IOException;
import java.io.Serializable;
import ooserver.OOServerMsgFact;
import ooserver.client.OOISimpleClient;
import ooserver.commoninterfaces.OOIMsg;
import ooserver.commoninterfaces.OOIMsgHandler;
import ooserver.commoninterfaces.OOIObservable;
import ooserver.commoninterfaces.OOIPrintable;
import ooserver.msg.OOServerMsgHandler;
import ooserver.observables.OOObservables;
import ooserver.observers.OOObservers;
import ssserver.admin.SSServerAdmin;
import ooserver.observers.OOIObserverObj;
import ooserver.observables.OOIObservableObj;

/**
 *
 * @author alexander
 */
public class OOServerAdmin extends SSServerAdmin {
    OOObservables observablesServer;
    OOObservers observersServer;

    public OOServerAdmin(int port, OOIPrintable printer) {
        super(port, printer);
        _init_(port, printer);
    }

    public OOServerAdmin(int port, OOIPrintable printer, OOIMsgHandler msgHandler) {
        super(port, printer, msgHandler);
    }
    
    private void _init_(int port, OOIPrintable printer) {
        OOServerMsgHandler ooMsgHandler = new OOServerMsgHandler(printer, this);
        this.setMsgHandler(ooMsgHandler);
        this.startServerCleaning();
        //this.addObserver(this);
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
            printer.print("OOServerAdmin: " + "Sending message to client " + idClient + ".");
            getClient(idClient).sendMessage(message);
        } catch (IOException ex) {
            printer.printError("OOServerAdmin: " + ex.getMessage());
        }
    }
    
    public void sendMessageToAllObservers(OOIMsg message) {
        printer.print("OOServerAdmin: " + "Sending message to all observers.");
        printer.print("OOServerAdmin: " + "Message:" + message.toString() + ".");
        for (OOIObserverObj observer : observersServer.getObservers()) {
            printer.print("OOServerAdmin: " + "Sending message to observer " + observer.getId() + ".");
            sendMessageToClient(observer.getId(), message);
        }
    }
    
    public void addObservableToServer(OOIObservableObj object) {
        observablesServer.add(object);
    }
    
    public void sendObservableToAllClients(OOIObservableObj object) {
        printer.print("OOServerAdmin: " + "Sending observable to all clients.");
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
    
    public void sendObserverToAllClients(OOIObservableObj object) {
        printer.print("OOServerAdmin: " + "Sending observer to all clients.");
        sendMessageToAllObservers(OOServerMsgFact.createMsg(OOServerMsgFact.SENDING_OBSERVER, (Serializable)object.getObject()));
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
    
}
