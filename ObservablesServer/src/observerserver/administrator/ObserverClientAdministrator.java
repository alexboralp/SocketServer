/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerserver.administrator;

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
    
    public void addObservable(String id) {
        administrator.sendMessage(MessageFactory.);
    }
    
    public void sendMessage(IMessage message) {
        administrator.sendMessage(message);
    }

    @Override
    public void update(Object message) {
        if (message instanceof IMessage) {
            printer.print(message.toString());
        }
    }
}
