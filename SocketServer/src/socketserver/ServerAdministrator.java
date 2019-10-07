/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketserver;

import socketserver.client.Clients;
import socketserver.client.IClient;
import socketserver.client.WaitClients;
import java.io.IOException;
import socketserver.client.WaitForClientMessages;
import socketserver.patterns.observer.IObserver;
import socketserver.commoninterfaces.IPrintable;
import socketserver.message.IMessage;
import socketserver.patterns.observer.AbsObservable;

/**
 *
 * @author alexander
 */
public class ServerAdministrator extends AbsObservable implements IObserver{
    
    private WaitClients waitClients;
    private Clients clients;
    private IPrintable printer;

    public ServerAdministrator(int port, IPrintable printer) {
        try {
            clients = new Clients();
            waitClients = new WaitClients(port, printer);
            waitClients.addObserver(clients);
            waitClients.addObserver(this);
            
            new Thread(waitClients);
            
            this.printer = printer;
            
            this.printer.print("Waiting for clients.");
        } catch (IOException ex) {
            this.printer.printError("ServerAdministrator: Unnable to start server on port " + port + ".");
        }
    }

    @Override
    public void update(Object message) {
        if (message instanceof IClient) {
            IClient client = (IClient) message;
            this.printer.print("New client connected: " + client.getSocket().getInetAddress().toString() + ".");
            WaitForClientMessages waitForClientMessages = new WaitForClientMessages(client, printer);
            this.printer.print("Waiting for new messages from client: " + client.getSocket().getInetAddress().toString() + ".");
            waitForClientMessages.addObserver(this);
            new Thread(waitForClientMessages);
        } else if (message instanceof IMessage) {
            IMessage me = (IMessage) message;
            this.printer.print("New message from client: " + me.getId() + ".");
            this.printer.print("Sending message to observers.");
            updateAll(me);
        }
    }
    
}
