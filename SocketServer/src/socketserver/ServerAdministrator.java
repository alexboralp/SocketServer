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
import static java.lang.Thread.sleep;
import socketserver.client.WaitForClientMessages;
import socketserver.client.WaitForClientsMessages;
import socketserver.patterns.observer.IObserver;
import socketserver.commoninterfaces.IPrintable;
import socketserver.message.IMessage;
import socketserver.patterns.observer.AbsObservable;

/**
 *
 * @author alexander
 */
public class ServerAdministrator extends AbsObservable implements IObserver, Runnable {
    
    private WaitClients waitClients;
    private Clients clients;
    private IPrintable printer;
    private WaitForClientsMessages waitForClientsMessages;
    Thread thread;

    public ServerAdministrator(int port, IPrintable printer) {
        try {
            clients = new Clients(printer);
            waitClients = new WaitClients(port, printer);
            waitClients.addObserver(this);
            waitClients.startListening();
            
            waitForClientsMessages = new WaitForClientsMessages(printer);
            
            this.printer = printer;
            
            this.printer.print("Waiting for clients.");
            
        } catch (IOException ex) {
            this.printer.printError("ServerAdministrator: " + "Unnable to start server on port " + port + ".");
        }
    }
    
    public static ServerAdministrator createServerAdministrator(int port, IPrintable printer) {
        return new ServerAdministrator(port, printer);
    }
        
    public void stopServer() {
        waitClients.stopListening();
        waitForClientsMessages.stopAll();
    }
        
    public void startServerCleaning() {
        thread = new Thread(this);
    }
    
    public void stopServerCleaning() {
        thread.interrupt();
    }

    @Override
    public void update(Object message) {
        if (message instanceof IClient) {
            newClientReceived((IClient) message);
        } else if (message instanceof IMessage) {
            newMessageReceived((IMessage) message);
        }
    }
    
    private void newClientReceived(IClient client) {
        clients.add(client);
        this.printer.print("New client connected: " + client.getId() + ".");
        WaitForClientMessages waitForClientMessages = new WaitForClientMessages(client, printer);
        this.printer.print("Waiting for new messages from client: " + client.getId() + ".");
        waitForClientMessages.addObserver(this);
        waitForClientMessages.startListening();
        waitForClientsMessages.add(waitForClientMessages);
    }
    
    private void newMessageReceived(IMessage message) {
        this.printer.print("New message from client: " + message.getId() + ".");
        this.printer.print("Message: " + message.toString() + ".");
        this.printer.print("Sending message to observers.");
        updateAll(message);
    }

    @Override
    public void run() {
        while(true) {
            try {
                sleep(5*60*1000); // Realiza limpieza cada 5 minutos
                waitForClientsMessages.clean();
                clients.clean();
            } catch (InterruptedException ex) {
                printer.printError("ServerAdministrator:" + ex.getMessage());
            }
            
        }
    }
    
    
    
}
