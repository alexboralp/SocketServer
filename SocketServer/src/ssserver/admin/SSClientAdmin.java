/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver.admin;

import ssclient.SSClientMsgFact;
import ssclient.socket.SSWaitMsgsFromServer;
import ssserver.SSServerMsgFact;
import ssserver.commoninterfaces.SSIMsgHandler;
import ssserver.patterns.observer.SSAbsObservable;
import ssserver.commoninterfaces.SSIPrintable;
import ssserver.msg.SSIMsg;
import ssserver.patterns.observer.SSIObserver;

/**
 *
 * @author alexander
 */
public class SSClientAdmin extends SSAbsObservable implements SSIObserver, SSIMsgHandler<SSIMsg> {
    
    private final SSIPrintable printer;
    private final SSWaitMsgsFromServer waitMessagesFromServer;

    public SSClientAdmin(String serverName, int port, SSIPrintable printer) {
        waitMessagesFromServer = new SSWaitMsgsFromServer(serverName, port, printer);
        waitMessagesFromServer.addObserver(this);
        waitMessagesFromServer.startListening();

        this.printer = printer;

        this.printer.print("SSClientAdmin: " + "Waiting for messages from server.");
    }
    
    public void sendMessage(SSIMsg message) {
        waitMessagesFromServer.sendMessage(message);
    }
    
    public boolean isOk() {
        return waitMessagesFromServer.isOk();
    }
    
    public void closeConnection() {
        waitMessagesFromServer.closeConnection();
    }

    @Override
    public void update(Object message) {
        this.printer.print("SSClientAdmin: " + "Message received: " + message.toString());
        if (message instanceof SSIMsg) {
            this.printer.print("SSClientAdmin: " + "SSIMsg received.");
            handleMsg((SSIMsg) message);
        } else {
            this.printer.print("SSClientAdmin: " + "Non SSIMsg message received.");
        }
        this.printer.print("SSClientAdmin: " + "Resending message to observers.");
        updateAll(message);
    }
    
    @Override
    public void handleMsg(SSIMsg message) {
        this.printer.print("SSClientAdmin: " + "New message from server: " + message.getId() + ".");
        this.printer.print("SSClientAdmin: " + "Message: " + message.toString() + ".");
        switch (message.getType()) {
            case SSServerMsgFact.CLOSE_CONNECTION:
                this.printer.print("SSClientAdmin: " + "El servidor solicitó cerrar la conección.");
                closeConnection();
                break;
            case SSServerMsgFact.CHECKING_CONNECTION:
                this.printer.print("SSClientAdmin: " + "El servidor está checkeando la conección.");
                sendMessage(SSClientMsgFact.createMsg(SSClientMsgFact.MESSAGE_RECEIVED, null));
                break;
            case SSServerMsgFact.INFO:
                this.printer.print("SSClientAdmin: " + "Se recibió información del servidor.");
                this.printer.print(message.getMessage().toString());
                break;
            case SSServerMsgFact.RESENDING_MESSAGE_FROM_CLIENT:
                this.printer.print("SSClientAdmin: " + "Se recibió un mensaje del cliente " + message.getId());
                this.printer.print(message.getMessage().toString());
                break;
            default:
                break;
        }
    }

    @Override
    public String toString() {
        return "SSClientAdmin{" + "printer=" + printer + ", waitMessagesFromServer=" + waitMessagesFromServer + '}';
    }
    
    
}
