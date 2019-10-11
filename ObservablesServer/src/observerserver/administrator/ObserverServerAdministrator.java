/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerserver.administrator;

import java.io.IOException;
import java.io.Serializable;
import observerclient.ObserverClientMessageFactory;
import observerserver.ObserverServerMessageFactory;
import observerserver.observables.IObservableObject;
import observerserver.observables.ObservableObject;
import observerserver.observables.ObservableObjectFactory;
import observerserver.observables.Observables;
import observerserver.observers.IObserverObject;
import observerserver.observers.ObserverObjectFactory;
import observerserver.observers.Observers;
import socketserver.ServerAdministratorFactory;
import socketserver.administrator.ServerAdministrator;
import socketserver.client.IClient;
import socketserver.commoninterfaces.IPrintable;
import socketserver.message.IMessage;
import socketserver.patterns.observer.AbsObservable;
import socketserver.patterns.observer.IObservable;
import socketserver.patterns.observer.IObserver;

/**
 *
 * @author alexander
 */
public class ObserverServerAdministrator extends AbsObservable implements IObserver {
    private final ServerAdministrator administrator;
    private final IPrintable printer;
    Observables observablesServer;
    Observers observersServer;

    public ObserverServerAdministrator(int port, IPrintable printer) {
        administrator = ServerAdministratorFactory.createServerAdministrator(port, printer);
        administrator.startServerCleaning();
        administrator.addObserver(this);
        this.printer = printer;
        observablesServer = new Observables();
        observersServer = new Observers();
    }
    
    public void sendIdToClient(String idClient) {
        sendMessageToClient(idClient, ObserverServerMessageFactory.createMessage(ObserverServerMessageFactory.SENDING_ID_TO_OBSERVER, idClient));
    }
    
    public void sendObservableToClient(String idClient, String idObservable) {
        sendMessageToClient(idClient, ObserverServerMessageFactory.createMessage(ObserverServerMessageFactory.SENDING_OBSERVABLE, (Serializable)observablesServer.get(idObservable).getObject()));
    }
    
    public void sendObservableToClient(String idClient, Serializable observable) {
        sendMessageToClient(idClient, ObserverServerMessageFactory.createMessage(ObserverServerMessageFactory.SENDING_OBSERVABLE, observable));
    }
    
    private void sendObservableToClient(IClient client, Serializable observable) {
        sendMessageToClient(client, ObserverServerMessageFactory.createMessage(ObserverServerMessageFactory.SENDING_OBSERVABLE, observable));
    }
    
    public void sendObservablesToClient(String idClient) {
        IClient client = observersServer.get(idClient).getObject();
        sendMessageToClient(client, ObserverServerMessageFactory.createMessage(ObserverServerMessageFactory.OBSERVABLES_LIST, null));
        for (IObservableObject observable : observablesServer.getObservables()) {
            sendObservableToClient(client, (Serializable)observable.getObject());
        }
        sendMessageToClient(client, ObserverServerMessageFactory.createMessage(ObserverServerMessageFactory.DONE, null));
    }
    
    public void sendObserverToClient(String idClient, String idSendClient) {
        sendMessageToClient(idClient, ObserverServerMessageFactory.createMessage(ObserverServerMessageFactory.SENDING_OBSERVER, (Serializable)observersServer.get(idSendClient).getObject()));
    }
    
    public void sendObserverToClient(String idClient, Serializable observer) {
        sendMessageToClient(idClient, ObserverServerMessageFactory.createMessage(ObserverServerMessageFactory.SENDING_OBSERVER, observer));
    }
    
    private void sendObserverToClient(IClient client, Serializable observer) {
        sendMessageToClient(client, ObserverServerMessageFactory.createMessage(ObserverServerMessageFactory.SENDING_OBSERVER, observer));
    }
    
    public void sendObserversToClient(String idClient) {
        IClient client = observersServer.get(idClient).getObject();
        sendMessageToClient(client, ObserverServerMessageFactory.createMessage(ObserverServerMessageFactory.OBSERVERS_LIST, null));
        for (IObserverObject observer : observersServer.getObservers()) {
            sendObservableToClient(client, (Serializable)observer.getObject());
        }
        sendMessageToClient(client, ObserverServerMessageFactory.createMessage(ObserverServerMessageFactory.DONE, null));
    }
    
    public void sendTextMessageToClient(String idClient, String message) {
        sendMessageToClient(idClient, ObserverServerMessageFactory.createMessage(ObserverServerMessageFactory.TEXT_MESSAGE, message));
    }
    
    public void sendMessageToClient(String idClient, IMessage message) {
        try {
            observersServer.get(idClient).getObject().sendMessage(message);
        } catch (IOException ex) {
            printer.printError("ObserverServerAdministrator: " + ex.getMessage());
        }
    }
    
    private void sendMessageToClient(IClient client, IMessage message) {
        try {
            client.sendMessage(message);
        } catch (IOException ex) {
            printer.printError("ObserverServerAdministrator: " + ex.getMessage());
        }
    }
    
    public void addObservableToServer(IObservableObject object) {
        observablesServer.add(object);
    }
    
    public void removeObservableFromServer(IObservableObject object) {
        observablesServer.remove(object);
    }
    
    public void removeObservableFromServer(String id) {
        observablesServer.remove(id);
    }
    
    public IObservableObject getObservableFromServer(String id) {
        return observablesServer.get(id);
    }
    
    public void addObserverToServer(IObserverObject object) {
        observersServer.add(object);
    }
    
    public void removeObserverFromServer(IObserverObject object) {
        observersServer.remove(object.getId());
        removeObserverFromObservables(object.getId());
    }
    
    public void removeObserverFromServer(String id) {
        observersServer.remove(id);
        removeObserverFromObservables(id);
    }
    
    public IObserverObject getObserverFromServer(String id) {
        return observersServer.get(id);
    }
    
    public void setObserverToObservable(IObserverObject observer, IObservableObject observable) {
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
    
    public void removeObserverFromObservable(IObserverObject observer, IObservableObject observable) {
        removeObserverFromObservable(observer.getId(), observable.getId());
    }
    
    public void removeObserverFromObservable(String observerId, String observableId) {
        if (observersServer.containsKey(observerId) && observablesServer.containsKey(observableId)) {
            observablesServer.get(observableId).removeObserver(observersServer.get(observerId));
        }
    }
    
    private void removeObserverFromObservables(String id) {
        for (IObservable observable : observablesServer.getObservables()) {
            observable.removeObserver(this);
        }
    }

    @Override
    public void update(Object message) {
        if (message instanceof IClient) {
            newClientReceived((IClient)message);
        } else if (message instanceof IMessage) {
            messageReceived((IMessage)message);
        }
        printer.print("ObserverServerAdministrator: Sending message to all observers");
        updateAll(message);
    }
    
    private void newClientReceived(IClient client) {
        printer.print("ObserverServerAdministrator: New client received: " + client.toString() + ".");
        observersServer.add(ObserverObjectFactory.create(client));
    }
    
    private void messageReceived(IMessage message) {
        printer.print("ObserverServerAdministrator: Message received: " + message.toString());
        switch (message.getType()) {
            case ObserverClientMessageFactory.ADD_OBSERVABLE:
                IObservableObject newObservable = ObservableObjectFactory.create((ObservableObject)message.getMessage());
                addObservableToServer(newObservable);
                break;
            case ObserverClientMessageFactory.REMOVE_OBSERVABLE:
                removeObservableFromServer((String)message.getMessage());
                break;
            case ObserverClientMessageFactory.FOLLOW_OBSERVABLE:
                setObserverToObservable(message.getId(), (String)message.getMessage());
                break;
            case ObserverClientMessageFactory.UNFOLLOW_OBSERVABLE:
                removeObserverFromObservable(message.getId(), (String)message.getMessage());
                break;
            case ObserverClientMessageFactory.REMOVE_ME_FROM_OBSERVERS:
                removeObserverFromServer(message.getId());
                break;
            case ObserverClientMessageFactory.ADD_OBSERVER:
                IObserverObject newObserver = ObserverObjectFactory.create((IClient)message.getMessage());
                addObserverToServer(newObserver);
                break;
            case ObserverClientMessageFactory.SEND_ALL_OBSERVERS:
                sendObserversToClient(message.getId());
                break;
            case ObserverClientMessageFactory.SEND_ALL_OBSERVABLES:
                sendObservablesToClient(message.getId());
                break;
            case ObserverClientMessageFactory.SEND_MY_ID:
                sendIdToClient(message.getId());
                break;
            default:
                break;
        }
    }
    
    
}
