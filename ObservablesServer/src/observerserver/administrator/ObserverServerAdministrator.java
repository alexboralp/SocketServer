/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerserver.administrator;

import java.io.IOException;
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
import socketserver.patterns.observer.IObservable;
import socketserver.patterns.observer.IObserver;

/**
 *
 * @author alexander
 */
public class ObserverServerAdministrator implements IObserver {
    private final ServerAdministrator administrator;
    private final IPrintable printer;
    Observables observables;
    Observers observers;

    public ObserverServerAdministrator(int port, IPrintable printer) {
        administrator = ServerAdministratorFactory.createServerAdministrator(port, printer);
        administrator.startServerCleaning();
        administrator.addObserver(this);
        this.printer = printer;
        observables = new Observables();
        observers = new Observers();
    }
    
    public void sendMessageToClient(String idClient, IMessage message) {
        try {
            observers.get(idClient).getObject().sendMessage(message);
        } catch (IOException ex) {
            printer.printError("ObserverServerAdministrator: " + ex.getMessage());
        }
    }
    
    public void addObservable(IObservableObject object) {
        observables.add(object);
    }
    
    public void removeObservable(IObservableObject object) {
        observables.remove(object);
    }
    
    public void removeObservable(String id) {
        observables.remove(id);
    }
    
    public void getObservable(String id) {
        observables.get(id);
    }
    
    public void addObserver(IObserverObject object) {
        observers.add(object);
    }
    
    public void removeObserver(IObserverObject object) {
        observers.remove(object.getId());
        removeObserverFromObservables(object.getId());
    }
    
    public void removeObserver(String id) {
        observers.remove(id);
        removeObserverFromObservables(id);
    }
    
    public void getObserver(String id) {
        observers.get(id);
    }
    
    public void setObserverToObservable(IObserverObject observer, IObservableObject observable) {
        if (!observers.containsKey(observer.getId())) {
            observers.add(observer);
        }
        if (!observables.containsKey(observable.getId())) {
            observables.add(observable);
        }
        
        observables.get(observable.getId()).addObserver(observers.get(observer.getId()));
    }
    
    public void removeObserverFromObservable(IObserverObject observer, IObservableObject observable) {
        if (observers.containsKey(observer.getId()) && observables.containsKey(observable.getId())) {
            observables.get(observable.getId()).removeObserver(observers.get(observer.getId()));
        }
    }
    
    private void removeObserverFromObservables(String id) {
        for (IObservable observable : observables.getObservables()) {
            observable.removeObserver(this);
        }
    }

    @Override
    public void update(Object message) {
        if (message instanceof IClient) {
            observers.add(ObserverObjectFactory.create((IClient)message));
        } else if (message instanceof IMessage) {
            printer.print(message.toString());
        }
    }
    
    
}
