/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver.msg;

import ssclient.SSClientMsgFact;
import ssserver.SSServerMsgFact;
import ssserver.admin.SSClientAdmin;
import ssserver.commoninterfaces.SSIMsgHandler;
import ssserver.commoninterfaces.SSIPrintable;

/**
 *
 * @author alexander
 */
public class SSClientMsgHandler implements SSIMsgHandler {
    
    
    protected SSIPrintable printer;
    protected SSClientAdmin admin;

    public SSClientMsgHandler(SSIPrintable printer, SSClientAdmin admin) {
        this.printer = printer;
        this.admin = admin;
    }

    public SSIPrintable getPrinter() {
        return printer;
    }

    public void setPrinter(SSIPrintable printer) {
        this.printer = printer;
    }

    public SSClientAdmin getAdmin() {
        return admin;
    }

    public void setAdmin(SSClientAdmin admin) {
        this.admin = admin;
    }

    @Override
    public void handleMsg(Object message) {
        this.printer.print("SSClientMsgHandler: " + "New message received.");
        if (message instanceof SSIMsg) {
            this.printer.print("SSClientMsgHandler: " + "SSIMsg received.");
            SSIMsg msg = (SSIMsg) message;
            this.printer.print("SSClientMsgHandler: " + "New message from server: " + msg.getId() + ".");
            this.printer.print("SSClientMsgHandler: " + "Message: " + msg.toString() + ".");
            switch (msg.getType()) {
                case SSServerMsgFact.CLOSE_CONNECTION:
                    this.printer.print("SSClientMsgHandler: " + "El servidor solicitó cerrar la conección.");
                    admin.closeConnection();
                    break;
                case SSServerMsgFact.CHECKING_CONNECTION:
                    this.printer.print("SSClientMsgHandler: " + "El servidor está checkeando la conección.");
                    admin.sendMessage(SSClientMsgFact.createMsg(SSClientMsgFact.MESSAGE_RECEIVED, null));
                    break;
                case SSServerMsgFact.INFO:
                    this.printer.print("SSClientMsgHandler: " + "Se recibió información del servidor.");
                    this.printer.print(msg.getMessage().toString());
                    break;
                case SSServerMsgFact.RESENDING_MESSAGE_FROM_CLIENT:
                    this.printer.print("SSClientMsgHandler: " + "Se recibió un mensaje del cliente " + msg.getId());
                    this.printer.print(msg.getMessage().toString());
                    break;
                default:
                    break;
            }
        } else {
            this.printer.print("SSClientMsgHandler: " + "Unknown messeage format.");
        }
    }

    @Override
    public void update(Object message) {
            this.printer.print("SSClientMsgHandler: " + "Sending message to message handler.");
            this.handleMsg(message);
    }
    
}
