/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver.admin;

import ssserver.client.SSClients;
import ssserver.client.SSWaitForClients;
import java.io.IOException;
import java.io.Serializable;
import static java.lang.Thread.sleep;
import ssserver.client.SSClient;
import ssserver.client.SSWaitForClientsMsgs;
import ssserver.patterns.observer.SSAbsObservable;
import ssserver.commoninterfaces.SSIPrintable;
import ssserver.patterns.observer.SSIObserver;
import ssserver.client.SSIClient;
import ssserver.client.SSWaitForClientMsgs;
import ssserver.commoninterfaces.SSIMsgHandler;
import ssserver.msg.SSServerMsgHandler;

/**
 *
 * @author alexander
 */
public class SSServerAdmin extends SSAbsObservable implements SSIObserver, Runnable {
    
    protected SSWaitForClients waitClients;
    protected SSClients clients;
    protected SSIPrintable printer;
    protected SSWaitForClientsMsgs waitForClientsMessages;
    protected SSIMsgHandler msgHandler;
    protected Thread thread;

    public SSServerAdmin(int port, SSIPrintable printer) {
        _init_(port, printer);
        
        msgHandler = new SSServerMsgHandler(printer, this);
    }
    
    public SSServerAdmin(int port, SSIPrintable printer, SSIMsgHandler msgHandler) {
        _init_(port, printer);
        
        this.msgHandler = msgHandler;
    }
    
    private void _init_(int port, SSIPrintable printer) {
        try {
            clients = new SSClients(printer);
            waitClients = new SSWaitForClients(port, printer);
            waitClients.addObserver(this);
            waitClients.startListening();
            
            this.printer = printer;
            
            waitForClientsMessages = new SSWaitForClientsMsgs(printer);
            
            this.printer.print("SSServerAdmin: " + "Waiting for clients on port " + port + ".");
            
        } catch (IOException ex) {
            this.printer.printError("SSServerAdmin: " + "Unnable to start server on port " + port + ".");
        }
    }
    
    public SSIClient getClient(String clientId) {
        return clients.get(clientId);
    }
    
    public void sendMessageToClient(String clientId, Serializable message) {
        try {
            this.printer.print("SSServerAdmin: " + "Sending message to client: " + clientId + ".");
            getClient(clientId).sendMessage(message);
        } catch (IOException ex) {
            printer.printError("SSServerAdmin: " + "No se pudo enviar el mensaje " + message.toString() + " to client " + clientId + ", mensaje de error: " + ex.getMessage());
        }
    }
    
    public void sendMessageToAllClients(Serializable message) {
        clients.sendMessageToAllClients(message);
    }
        
    public void stopServer() {
        waitClients.stopListening();
        waitForClientsMessages.stopAll();
    }
        
    public void startServerCleaning() {
        thread = new Thread(this);
        thread.start();
    }
    
    public void stopServerCleaning() {
        thread.interrupt();
    }
    
    public void closeConnection(String clientId) {
        try {
            waitForClientsMessages.get(clientId).closeComunication();
        } catch (IOException ex) {
            this.printer.printError("SSServerAdmin: " + ex.getMessage());
        }
        waitForClientsMessages.remove(clientId);
    }

    @Override
    public void update(Object message) {
        this.printer.print("SSServerAdmin: " + "New message received: " + message.toString() + ".");
        if (message instanceof SSIClient) {
            this.printer.print("SSServerAdmin: " + "New client received.");
            addNewClient((SSClient)message);
        } else {
            this.printer.print("SSServerAdmin: " + "Unknown message type.");
        }
    }
    
    private void addNewClient(SSIClient client) {
        clients.add(client);
        SSWaitForClientMsgs sswfcm = new SSWaitForClientMsgs(client, printer);
        sswfcm.addObserver(msgHandler);
        sswfcm.startListening();
        waitForClientsMessages.add(sswfcm);
        this.printer.print("SSServerAdmin: " + "New client added.");
    }

    @Override
    public void run() {
        while(true) {
            try {
                sleep(5*60*1000); // Realiza limpieza de clientes cada 5 minutos
                waitForClientsMessages.clean();
                clients.clean();
            } catch (InterruptedException ex) {
                printer.printError("SSServerAdmin:" + ex.getMessage());
            }
            
        }
    }

    @Override
    public String toString() {
        return "SSServerAdmin{" + "waitClients=" + waitClients + ", clients=" + clients + ", printer=" + printer + ", waitForClientsMessages=" + waitForClientsMessages + ", msgHandler=" + msgHandler + ", thread=" + thread + '}';
    }

    public SSWaitForClients getWaitClients() {
        return waitClients;
    }

    public void setWaitClients(SSWaitForClients waitClients) {
        this.waitClients = waitClients;
    }

    public SSClients getClients() {
        return clients;
    }

    public void setClients(SSClients clients) {
        this.clients = clients;
    }

    public SSIPrintable getPrinter() {
        return printer;
    }

    public void setPrinter(SSIPrintable printer) {
        this.printer = printer;
    }

    public SSWaitForClientsMsgs getWaitForClientsMessages() {
        return waitForClientsMessages;
    }

    public void setWaitForClientsMessages(SSWaitForClientsMsgs waitForClientsMessages) {
        this.waitForClientsMessages = waitForClientsMessages;
    }

    public SSIMsgHandler getMsgHandler() {
        return msgHandler;
    }

    public void setMsgHandler(SSIMsgHandler msgHandler) {
        this.msgHandler = msgHandler;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
    
    
    
}
