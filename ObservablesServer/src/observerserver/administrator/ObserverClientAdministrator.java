/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerserver.administrator;

import java.io.Serializable;
import observerclient.ObserverClientMessageFactory;
import observerserver.ObserverServerMessageFactory;
import socketclient.ClientAdministratorFactory;
import socketserver.administrator.ClientAdministrator;
import socketserver.commoninterfaces.IPrintable;
import socketserver.message.IMessage;
import socketserver.patterns.observer.AbsObservable;
import socketserver.patterns.observer.IObserver;

/**
 *
 * @author alexander
 */
public class ObserverClientAdministrator extends AbsObservable implements IObserver {
    ClientAdministrator administrator;
    IPrintable printer;

    public ObserverClientAdministrator(String serverName, int port, IPrintable printer) {
        administrator = ClientAdministratorFactory.createClientAdministrator(serverName, port, printer);
        administrator.addObserver(this);
        this.printer = printer;
    }
    
    public void addNewObserver(Serializable observer) {
        sendMessage(ObserverClientMessageFactory.createMessage(ObserverClientMessageFactory.ADD_OBSERVER, observer));
    }
    
    public void addNewObservable(Serializable observable) {
        sendMessage(ObserverClientMessageFactory.createMessage(ObserverClientMessageFactory.ADD_OBSERVABLE, observable));
    }
    
    public void removeObservable(String idObservable) {
        sendMessage(ObserverClientMessageFactory.createMessage(ObserverClientMessageFactory.ADD_OBSERVABLE, idObservable));
    }
    
    public void followObservable(String idObservable) {
        sendMessage(ObserverClientMessageFactory.createMessage(ObserverClientMessageFactory.FOLLOW_OBSERVABLE, idObservable));
    }
    
    public void unfollowObservable(String idObservable) {
        sendMessage(ObserverClientMessageFactory.createMessage(ObserverClientMessageFactory.UNFOLLOW_OBSERVABLE, idObservable));
    }
    
    public void removeMeFromObservers() {
        sendMessage(ObserverClientMessageFactory.createMessage(ObserverClientMessageFactory.REMOVE_ME_FROM_OBSERVERS, null));
    }
    
    public void closeConnection() {
        sendMessage(ObserverClientMessageFactory.createMessage(ObserverClientMessageFactory.CLOSE_CONNECTION, null));
    }
    
    public void sendInfoServer(Serializable info) {
        sendMessage(ObserverClientMessageFactory.createMessage(ObserverClientMessageFactory.INFO, info));
    }
    
    public void sendAllObservers() {
        sendMessage(ObserverClientMessageFactory.createMessage(ObserverClientMessageFactory.SEND_ALL_OBSERVERS, null));
    }
    
    public void sendAllObservables() {
        sendMessage(ObserverClientMessageFactory.createMessage(ObserverClientMessageFactory.SEND_ALL_OBSERVABLES, null));
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
            messageReceived((IMessage)message);
        }
        printer.print("ObserverClientAdministrator: Sending message to observers.");
        updateAll(message);
    }
    
    private void messageReceived(IMessage message) {
        printer.print("ObserverClientAdministrator: New message received from server: " + message.toString());
        switch (message.getType()) {
            case ObserverServerMessageFactory.OBSERVABLES_LIST:
                printer.print("ObserverClientAdministrator: Se va a recibir la lista de observables.");
                break;
            case ObserverServerMessageFactory.OBSERVERS_LIST:
                printer.print("ObserverClientAdministrator: Se va a recibir la lista de observers.");
                break;
            case ObserverServerMessageFactory.TEXT_MESSAGE:
            case ObserverServerMessageFactory.TEXT_MESSAGE_TO_OBSERVER:
                printer.print("ObserverClientAdministrator: Se recibió un mensaje: " + (String)message.getMessage());
                break;
            case ObserverServerMessageFactory.SENDING_OBSERVABLE:
                printer.print("ObserverClientAdministrator: Se recibe un observable.");
                break;
            case ObserverServerMessageFactory.SENDING_OBSERVER:
                printer.print("ObserverClientAdministrator: Se recibe un observer.");
                break;
            case ObserverServerMessageFactory.SENDING_ID_TO_OBSERVER:
                printer.print("ObserverClientAdministrator: Se recibe el id dado por el server.");
                break;
            case ObserverServerMessageFactory.DONE:
                printer.print("ObserverClientAdministrator: El server avisó que terminó la solicitud anterior.");
                break;
            default:
                break;
        }
    }
}
