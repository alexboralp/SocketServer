/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerserver.administrator;

import java.io.IOException;
import java.io.Serializable;
import observerserver.ObserverMessageFactory;
import observerserver.observables.IObservableObject;
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
    
    public void sendObservableToClient(String idClient, String idObservable) {
        sendMessageToClient(idClient, ObserverMessageFactory.createMessage(ObserverMessageFactory.SENDING_OBSERVABLE, (Serializable)observablesServer.get(idObservable).getObject()));
    }
    
    public void sendObservableToClient(String idClient, Serializable observable) {
        sendMessageToClient(idClient, ObserverMessageFactory.createMessage(ObserverMessageFactory.SENDING_OBSERVABLE, observable));
    }
    
    private void sendObservableToClient(IClient client, Serializable observable) {
        sendMessageToClient(client, ObserverMessageFactory.createMessage(ObserverMessageFactory.SENDING_OBSERVABLE, observable));
    }
    
    public void sendObservablesToClient(String idClient) {
        IClient client = observersServer.get(idClient).getObject();
        sendMessageToClient(client, ObserverMessageFactory.createMessage(ObserverMessageFactory.OBSERVABLES_LIST, null));
        for (IObservableObject observable : observablesServer.getObservables()) {
            sendObservableToClient(client, (Serializable)observable.getObject());
        }
        sendMessageToClient(client, ObserverMessageFactory.createMessage(ObserverMessageFactory.DONE, null));
    }
    
    public void sendObserverToClient(String idClient, String idSendClient) {
        sendMessageToClient(idClient, ObserverMessageFactory.createMessage(ObserverMessageFactory.SENDING_OBSERVER, (Serializable)observersServer.get(idSendClient).getObject()));
    }
    
    public void sendObserverToClient(String idClient, Serializable observer) {
        sendMessageToClient(idClient, ObserverMessageFactory.createMessage(ObserverMessageFactory.SENDING_OBSERVER, observer));
    }
    
    private void sendObserverToClient(IClient client, Serializable observer) {
        sendMessageToClient(client, ObserverMessageFactory.createMessage(ObserverMessageFactory.SENDING_OBSERVER, observer));
    }
    
    public void sendObserversToClient(String idClient) {
        IClient client = observersServer.get(idClient).getObject();
        sendMessageToClient(client, ObserverMessageFactory.createMessage(ObserverMessageFactory.OBSERVERS_LIST, null));
        for (IObserverObject observer : observersServer.getObservers()) {
            sendObservableToClient(client, (Serializable)observer.getObject());
        }
        sendMessageToClient(client, ObserverMessageFactory.createMessage(ObserverMessageFactory.DONE, null));
    }
    
    public void sendTextMessageToClient(String idClient, String message) {
        sendMessageToClient(idClient, ObserverMessageFactory.createMessage(ObserverMessageFactory.TEXT_MESSAGE, message));
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
    
    public void removeObservableToServer(IObservableObject object) {
        observablesServer.remove(object);
    }
    
    public void removeObservableToServer(String id) {
        observablesServer.remove(id);
    }
    
    public void getObservableToServer(String id) {
        observablesServer.get(id);
    }
    
    public void addObserverToServer(IObserverObject object) {
        observersServer.add(object);
    }
    
    public void removeObserverToServer(IObserverObject object) {
        observersServer.remove(object.getId());
        removeObserverFromObservables(object.getId());
    }
    
    public void removeObserverToServer(String id) {
        observersServer.remove(id);
        removeObserverFromObservables(id);
    }
    
    public void getObserverToServer(String id) {
        observersServer.get(id);
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
    
    public void removeObserverFromObservable(IObserverObject observer, IObservableObject observable) {
        if (observersServer.containsKey(observer.getId()) && observablesServer.containsKey(observable.getId())) {
            observablesServer.get(observable.getId()).removeObserver(observersServer.get(observer.getId()));
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
            observersServer.add(ObserverObjectFactory.create((IClient)message));
        } else if (message instanceof IMessage) {
            printer.print(message.toString());
        }
    }
    
    
}
