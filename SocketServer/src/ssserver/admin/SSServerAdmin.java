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
import ssclient.SSClientMsgFact;
import ssserver.SSServerMsgFact;
import ssserver.client.SSWaitForClientsMsgs;
import ssserver.patterns.observer.SSAbsObservable;
import ssserver.commoninterfaces.SSIPrintable;
import ssserver.msg.SSIMsg;
import ssserver.patterns.observer.SSIObserver;
import ssserver.client.SSIClient;
import ssserver.msg.SSMsgToClient;

/**
 *
 * @author alexander
 */
public class SSServerAdmin extends SSAbsObservable implements SSIObserver, Runnable {
    
    private SSWaitForClients waitClients;
    private SSClients clients;
    private SSIPrintable printer;
    private SSWaitForClientsMsgs waitForClientsMessages;
    private Thread thread;

    public SSServerAdmin(int port, SSIPrintable printer) {
        try {
            clients = new SSClients(printer);
            waitClients = new SSWaitForClients(port, printer);
            waitClients.addObserver(this);
            waitClients.startListening();
            
            waitForClientsMessages = new SSWaitForClientsMsgs(printer);
            
            this.printer = printer;
            
            this.printer.print("Waiting for clients.");
            
        } catch (IOException ex) {
            this.printer.printError("ServerAdministrator: " + "Unnable to start server on port " + port + ".");
        }
    }
    
    public SSIClient getClient(String clientId) {
        return clients.get(clientId);
    }
    
    public void sendMessageToClient(String clientId, Serializable message) {
        try {
            getClient(clientId).sendMessage(message);
        } catch (IOException ex) {
            printer.printError("No se pudo enviar el mensaje " + message.toString() + " to client " + clientId + ", mensaje de error: " + ex.getMessage());
        }
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

    @Override
    public void update(Object message) {
        if (message instanceof SSIMsg) {
            newMessageReceived((SSIMsg) message);
        }
        this.printer.print("Sending message to observers.");
        updateAll(message);
    }
    
    private void newMessageReceived(SSIMsg message) {
        this.printer.print("New message from client: " + message.getId() + ".");
        this.printer.print("Message: " + message.toString() + ".");
        switch (message.getType()) {
            case SSClientMsgFact.CLOSE_CONNECTION:
                this.printer.print("El cliente solicitó cerrar su conección.");
                try {
                    waitForClientsMessages.get(message.getId()).closeComunication();
                } catch (IOException ex) {
                    this.printer.printError("ServerAdministrator: " + ex.getMessage());
                }
                waitForClientsMessages.remove(message.getId());
                break;
            case SSClientMsgFact.MESSAGE_RECEIVED:
                this.printer.print("El cliente " + message.getId() + " confirma la recepción de revisión de su conexión.");
                break;
            case SSClientMsgFact.INFO:
                this.printer.print("Se recibió información del cliente.");
                this.printer.print(message.getMessage().toString());
                break;
            case SSClientMsgFact.SEND_MESSAGE_TO_CLIENT:
                this.printer.print("Se recibió un mensaje para otro cliente.");
                SSIMsg msgToClient = SSServerMsgFact.createMsg(SSServerMsgFact.RESENDING_MESSAGE_FROM_CLIENT, ((SSMsgToClient)message.getMessage()).getMessage());
                sendMessageToClient(((SSMsgToClient)message.getMessage()).getId(), msgToClient);
                break;
            default:
                break;
        }
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
