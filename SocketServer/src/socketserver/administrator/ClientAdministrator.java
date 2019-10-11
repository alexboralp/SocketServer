/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketserver.administrator;

import socketclient.ClientMessageFactory;
import socketclient.socket.WaitMessagesFromServer;
import socketserver.ServerMessageFactory;
import socketserver.patterns.observer.IObserver;
import socketserver.commoninterfaces.IPrintable;
import socketserver.message.IMessage;
import socketserver.patterns.observer.AbsObservable;

/**
 *
 * @author alexander
 */
public class ClientAdministrator extends AbsObservable implements IObserver{
    
    private final IPrintable printer;
    private final WaitMessagesFromServer waitMessagesFromServer;

    public ClientAdministrator(String serverName, int port, IPrintable printer) {
        waitMessagesFromServer = new WaitMessagesFromServer(serverName, port, printer);
        waitMessagesFromServer.addObserver(this);
        waitMessagesFromServer.startListening();

        this.printer = printer;

        this.printer.print("Waiting for messages from server.");
    }
    
    public void sendMessage(IMessage message) {
        waitMessagesFromServer.sendMessage(message);
    }
    
    public boolean isOk() {
        return waitMessagesFromServer.isOk();
    }

    @Override
    public void update(Object message) {
        if (message instanceof IMessage) {
            newMessageReceived((IMessage) message);
        }
    }
    
    private void newMessageReceived(IMessage message) {
        this.printer.print("New message from server: " + message.getId() + ".");
        this.printer.print("Message: " + message.toString() + ".");
        switch (message.getType()) {
            case ServerMessageFactory.CLOSE_CONNECTION:
                this.printer.print("El servidor solicitó cerrar la conección.");
                waitMessagesFromServer.closeConnection();
                break;
            case ServerMessageFactory.CHECKING_CONNECTION:
                this.printer.print("El servidor está checkeando la conección.");
                sendMessage(ClientMessageFactory.createMessage(ClientMessageFactory.MESSAGE_RECEIVED, null));
                break;
            case ServerMessageFactory.INFO:
                this.printer.print("Se recibió información del servidor.");
                this.printer.print(message.getMessage().toString());
                break;
        }
        this.printer.print("Sending message to observers.");
        updateAll(message);
    }
    
}
