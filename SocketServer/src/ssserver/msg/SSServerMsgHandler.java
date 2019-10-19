/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver.msg;

import ssclient.SSClientMsgFact;
import ssserver.SSServerMsgFact;
import ssserver.admin.SSServerAdmin;
import ssserver.commoninterfaces.SSIMsgHandler;
import ssserver.commoninterfaces.SSIPrintable;

/**
 *
 * @author alexander
 */
public class SSServerMsgHandler implements SSIMsgHandler {
    
    
    protected SSIPrintable printer;
    protected SSServerAdmin admin;

    public SSServerMsgHandler(SSIPrintable printer, SSServerAdmin admin) {
        this.printer = printer;
        this.admin = admin;
    }

    public SSIPrintable getPrinter() {
        return printer;
    }

    public void setPrinter(SSIPrintable printer) {
        this.printer = printer;
    }

    public SSServerAdmin getAdmin() {
        return admin;
    }

    public void setAdmin(SSServerAdmin admin) {
        this.admin = admin;
    }

    @Override
    public void handleMsg(Object message) {
        this.printer.print("SSServerMsgHandler: " + "New message received.");
        if (message instanceof SSIMsg){
            SSIMsg msg = (SSIMsg)message;
            switch (msg.getType()) {
                case SSClientMsgFact.CLOSE_CONNECTION:
                    this.printer.print("SSServerAdmin: " + "El cliente solicitó cerrar su conección.");
                    admin.closeConnection(msg.getId());
                    break;
                case SSClientMsgFact.MESSAGE_RECEIVED:
                    this.printer.print("SSServerAdmin: " + "El cliente " + msg.getId() + " confirma la recepción de revisión de su conexión.");
                    break;
                case SSClientMsgFact.INFO:
                    this.printer.print("SSServerAdmin: " + "Se recibió información del cliente.");
                    this.printer.print(msg.getMessage().toString());
                    break;
                case SSClientMsgFact.SEND_MESSAGE_TO_CLIENT:
                    this.printer.print("SSServerAdmin: " + "Se recibió un mensaje para otro cliente.");
                    admin.sendMessageToClient(((SSMsgToClient)msg.getMessage()).getId(), SSServerMsgFact.createMsg(SSServerMsgFact.RESENDING_MESSAGE_FROM_CLIENT, msg.getMessage()));
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void update(Object message) {
            this.printer.print("SSServerAdmin: " + "Sending message to message handler.");
            handleMsg(message);
    }
    
}
