/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketserver.client;

import java.io.EOFException;
import java.io.IOException;
import socketserver.commoninterfaces.IPrintable;
import socketserver.message.IMessage;
import socketserver.patterns.observer.AbsObservable;

/**
 *
 * @author alexander
 */
public class WaitForClientMessages extends AbsObservable implements Runnable{
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
        
        printer.print("ClientSocketThread: " + "Esperando mensajes del server");
        
        while (listening) {
            try {
                if ((message = client.getIn().readObject()) != null) {
                    this.updateAll(message);
                }
            } catch (EOFException ex) {
                listening = false;
            } catch (IOException | ClassNotFoundException ex) {
                printer.printError("WaitForClientMessages: " + ex.getMessage());
            }
            
            if (!client.isOk()) {
                listening = false;
            }
        }
    }
    
    public void sendMessage(IMessage message) {
        try {
            printer.print("ClientSocketThread: " + "Enviando un mensaje al server.");
            client.sendMessage(message);
        } catch (IOException ex) {
            printer.printError("ClientSocketThread: " + ex.getMessage());
        }
    }
    
    public void startListening() {
        if (client.isOk()) {
            listening = true;
            socketThread = new Thread(this);
            socketThread.start();
        } else {
            printer.printError("ClientSocketThread: " + "Hay algún error con el cliente.");
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

    public IClient getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
