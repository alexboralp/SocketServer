/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketserver.client;

import java.io.IOException;
import socketserver.ServerMessageFactory;
import socketserver.commoninterfaces.IPrintable;
import socketserver.commoninterfaces.IClientable;
import socketserver.message.IMessage;
import socketserver.patterns.observer.AbsObservable;

/**
 *
 * @author alexander
 */
public class WaitForClientMessages extends AbsObservable implements Runnable, IClientable {
    private IClient client;
    private boolean listening;
    private Thread socketThread;
    private final IPrintable printer;

    public WaitForClientMessages (IClient client, IPrintable printer) {
        listening = true;
        this.client = client;
        this.printer = printer;
    }
    
    @Override
    public void run() {
        Object message;
        
        printer.print("WaitForClientMessages: " + "Waiting for messages from clients.");
        try {
            while (client.isOk() && (message = client.getIn().readObject()) != null) {
                this.updateAll(message);
                IMessage response = ServerMessageFactory.createMessage(ServerMessageFactory.INFO, "Message received."); 
                response.setId(client.getId());
                client.sendMessage(response);
            }
        } catch (IOException | ClassNotFoundException ex) {
            printer.printError("WaitForClientMessages: " + ex.getMessage());
        }
        listening = false;
    }
    
    public void startListening() {
        if (client.isOk()) {
            listening = true;
            socketThread = new Thread(this);
            socketThread.start();
        } else {
            printer.printError("WaitForClientMessages: " + "Hay algún error con el cliente.");
        }
    }
    
    public void stopListening() {
        listening = false;
        socketThread.interrupt();
    }
    
    public void closeComunication() throws IOException {
        stopListening();
        client.terminate();
    }
    
    public boolean isOk() {
        return client.isOk() && listening;
    }

    @Override
    public IClient getClient() {
        return client;
    }
    
    @Override
    public void setClient(IClient client) {
        this.client = client;
    }
}
