/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver.client;

import java.io.IOException;
import ssserver.SSServerMsgFact;
import ssserver.patterns.observer.SSAbsObservable;
import ssserver.commoninterfaces.SSIPrintable;
import ssserver.commoninterfaces.SSIClientable;
import ssserver.commoninterfaces.SSIIdable;

/**
 *
 * @author alexander
 */
public class SSWaitForClientMsgs extends SSAbsObservable implements Runnable, SSIClientable {
    private SSIClient client;
    private boolean listening;
    private Thread socketThread;
    private final SSIPrintable printer;

    public SSWaitForClientMsgs (SSIClient client, SSIPrintable printer) {
        listening = true;
        this.client = client;
        this.printer = printer;
    }
    
    @Override
    public void run() {
        Object message;
        
        printer.print("SSWaitForClientMsgs: " + "Waiting for messages from clients.");
        try {
            while (client.isOk() && (message = client.getIn().readObject()) != null) {
                if (message instanceof SSIIdable){
                    ((SSIIdable)message).setId(((SSIIdable)client).getId());
                }
                this.updateAll(message);
                
                client.sendMessage(SSServerMsgFact.createMsg(client.getId(), SSServerMsgFact.INFO, "Message received."));
            }
        } catch (IOException | ClassNotFoundException ex) {
            printer.printError("SSWaitForClientMsgs: " + ex.getMessage());
        }
        listening = false;
    }
    
    public void startListening() {
        if (client.isOk()) {
            listening = true;
            socketThread = new Thread(this);
            socketThread.start();
        } else {
            printer.printError("SSWaitForClientMsgs: " + "Hay algún error con el cliente.");
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
    public SSIClient getClient() {
        return client;
    }
    
    @Override
    public void setClient(SSIClient client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "SSWaitForClientMsgs{" + "client=" + client + ", listening=" + listening + ", socketThread=" + socketThread + ", printer=" + printer + '}';
    }
    
    
}
