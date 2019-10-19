/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver.admin;

import ssclient.socket.SSWaitMsgsFromServer;
import ssserver.commoninterfaces.SSIMsgHandler;
import ssserver.patterns.observer.SSAbsObservable;
import ssserver.commoninterfaces.SSIPrintable;
import ssserver.msg.SSClientMsgHandler;
import ssserver.msg.SSIMsg;

/**
 *
 * @author alexander
 */
public class SSClientAdmin extends SSAbsObservable {
    
    protected SSIPrintable printer;
    protected SSWaitMsgsFromServer waitMessagesFromServer;
    protected SSIMsgHandler msgHandler;

    public SSClientAdmin(String serverName, int port, SSIPrintable printer) {
        _init_(serverName, port, printer);
        msgHandler = new SSClientMsgHandler(printer, this);
        waitMessagesFromServer.addObserver(msgHandler);
    }

    public SSClientAdmin(String serverName, int port, SSIPrintable printer, SSIMsgHandler ssMsgHandler) {
        _init_(serverName, port, printer);
        this.msgHandler = ssMsgHandler;
        waitMessagesFromServer.addObserver(ssMsgHandler);
    }
    
    private void _init_(String serverName, int port, SSIPrintable printer) {
        waitMessagesFromServer = new SSWaitMsgsFromServer(serverName, port, printer);
        waitMessagesFromServer.startListening();

        this.printer = printer;

        this.printer.print("SSClientAdmin: " + "Waiting for messages from server.");
    }
    
    public void sendMessage(SSIMsg message) {
        waitMessagesFromServer.sendMessage(message);
    }
    
    public String getMyId() {
        return waitMessagesFromServer.getMyId();
    }
    
    public boolean isOk() {
        return waitMessagesFromServer.isOk();
    }
    
    public void closeConnection() {
        waitMessagesFromServer.closeConnection();
    }
    
    public final void replaceMsgHandler(SSIMsgHandler msgHandler) {
        waitMessagesFromServer.removeObserver(this.msgHandler);
        this.msgHandler = msgHandler;
        waitMessagesFromServer.addObserver(this.msgHandler);
    }

    public SSIMsgHandler getMsgHandler() {
        return msgHandler;
    }

    public void setMsgHandler(SSIMsgHandler msgHandler) {
        this.msgHandler = msgHandler;
    }

    @Override
    public String toString() {
        return "SSClientAdmin{" + "printer=" + printer + ", waitMessagesFromServer=" + waitMessagesFromServer + '}';
    }
    
    
}
