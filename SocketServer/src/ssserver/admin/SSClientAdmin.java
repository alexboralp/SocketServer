/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver.admin;

import ssclient.SSClientMsgFact;
import ssclient.socket.SSWaitMsgsFromServer;
import ssserver.SSServerMsgFact;
import ssserver.patterns.observer.SSAbsObservable;
import ssserver.commoninterfaces.SSIPrintable;
import ssserver.msg.SSIMsg;
import ssserver.patterns.observer.SSIObserver;

/**
 *
 * @author alexander
 */
public class SSClientAdmin extends SSAbsObservable implements SSIObserver{
    
    private final SSIPrintable printer;
    private final SSWaitMsgsFromServer waitMessagesFromServer;

    public SSClientAdmin(String serverName, int port, SSIPrintable printer) {
        waitMessagesFromServer = new SSWaitMsgsFromServer(serverName, port, printer);
        waitMessagesFromServer.addObserver(this);
        waitMessagesFromServer.startListening();

        this.printer = printer;

        this.printer.print("Waiting for messages from server.");
    }
    
    public void sendMessage(SSIMsg message) {
        waitMessagesFromServer.sendMessage(message);
    }
    
    public boolean isOk() {
        return waitMessagesFromServer.isOk();
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
        this.printer.print("New message from server: " + message.getId() + ".");
        this.printer.print("Message: " + message.toString() + ".");
        switch (message.getType()) {
            case SSServerMsgFact.CLOSE_CONNECTION:
                this.printer.print("El servidor solicitó cerrar la conección.");
                waitMessagesFromServer.closeConnection();
                break;
            case SSServerMsgFact.CHECKING_CONNECTION:
                this.printer.print("El servidor está checkeando la conección.");
                sendMessage(SSClientMsgFact.createMsg(SSClientMsgFact.MESSAGE_RECEIVED, null));
                break;
            case SSServerMsgFact.INFO:
                this.printer.print("Se recibió información del servidor.");
                this.printer.print(message.getMessage().toString());
                break;
            case SSServerMsgFact.RESENDING_MESSAGE_FROM_CLIENT:
                this.printer.print("Se recibió un mensaje del cliente " + message.getId());
                this.printer.print((String)message.getMessage());
                break;
            default:
                break;
        }
    }
    
}
