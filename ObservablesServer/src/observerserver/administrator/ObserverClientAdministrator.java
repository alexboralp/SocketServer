/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerserver.administrator;

import java.io.Serializable;
import observerclient.ObserverMessageFactory;
import socketclient.ClientAdministratorFactory;
import socketserver.administrator.ClientAdministrator;
import socketserver.commoninterfaces.IPrintable;
import socketserver.message.IMessage;
import socketserver.patterns.observer.IObserver;

/**
 *
 * @author alexander
 */
public class ObserverClientAdministrator implements IObserver {
    ClientAdministrator administrator;
    IPrintable printer;

    public ObserverClientAdministrator(String serverName, int port, IPrintable printer) {
        administrator = ClientAdministratorFactory.createClientAdministrator(serverName, 0, printer);
        administrator.addObserver(this);
        this.printer = printer;
    }
    
    public void addObserver(Serializable observer) {
        sendMessage(ObserverMessageFactory.createMessage(ObserverMessageFactory.ADD_OBSERVER, observer));
    }
    
    public void addObservable(Serializable observable) {
        sendMessage(ObserverMessageFactory.createMessage(ObserverMessageFactory.ADD_OBSERVABLE, observable));
    }
    
    public void removeObservable(String idObservable) {
        sendMessage(ObserverMessageFactory.createMessage(ObserverMessageFactory.ADD_OBSERVABLE, idObservable));
    }
    
    public void followObservable(String idObservable) {
        sendMessage(ObserverMessageFactory.createMessage(ObserverMessageFactory.FOLLOW_OBSERVABLE, idObservable));
    }
    
    public void unfollowObservable(String idObservable) {
        sendMessage(ObserverMessageFactory.createMessage(ObserverMessageFactory.UNFOLLOW_OBSERVABLE, idObservable));
    }
    
    public void removeMeFromObservers() {
        sendMessage(ObserverMessageFactory.createMessage(ObserverMessageFactory.REMOVE_ME_FROM_OBSERVERS, null));
    }
    
    public void closeConnection() {
        sendMessage(ObserverMessageFactory.createMessage(ObserverMessageFactory.CLOSE_CONNECTION, null));
    }
    
    public void sendInfoServer(Serializable info) {
        sendMessage(ObserverMessageFactory.createMessage(ObserverMessageFactory.INFO, info));
    }
    
    public void sendMessage(IMessage message) {
        administrator.sendMessage(message);
    }
    
    public boolean isOk() {
        return administrator.isOk();
    }

    @Override
    public void update(Object message) {
        if (message instanceof IMessage) {
            printer.print(message.toString());
        }
    }
}
