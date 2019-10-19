/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooserver.msg;

import ooserver.OOServerMsgFact;
import ooserver.admin.OOClientAdmin;
import ooserver.commoninterfaces.OOIMsg;
import ooserver.commoninterfaces.OOIMsgHandler;
import ooserver.commoninterfaces.OOIPrintable;
import ssserver.msg.SSClientMsgHandler;

/**
 *
 * @author alexander
 */
public class OOClientMsgHandler extends SSClientMsgHandler implements OOIMsgHandler {
    
    protected OOClientAdmin ooAdmin;

    public OOClientMsgHandler(OOIPrintable printer, OOClientAdmin admin) {
        super(printer, admin);
        ooAdmin = admin;
    }
    
    @Override
    public void handleMsg(Object message) {
        this.printer.print("OOClientMsgHandler: " + "New message received.");
        super.handleMsg(message);
        if (message instanceof OOIMsg) {
            OOIMsg msg = (OOIMsg)message;
            printer.print("OOClientMsgHandler: New message received from server: " + message.toString());
            switch (msg.getType()) {
                case OOServerMsgFact.OBSERVABLES_LIST:
                    printer.print("OOClientMsgHandler: OBSERVABLES_LIST, se va a recibir la lista de observables.");
                    break;
                case OOServerMsgFact.OBSERVERS_LIST:
                    printer.print("OOClientMsgHandler: OBSERVERS_LIST, se va a recibir la lista de observers.");
                    break;
                case OOServerMsgFact.TEXT_MESSAGE:
                case OOServerMsgFact.TEXT_MESSAGE_TO_OBSERVER:
                    printer.print("OOClientMsgHandler: TEXT_MESSAGE, se recibió un mensaje: " + (String)msg.getMessage());
                    break;
                case OOServerMsgFact.SENDING_OBSERVABLE:
                    printer.print("OOClientMsgHandler: SENDING_OBSERVABLE, se recibe un observable.");
                    break;
                case OOServerMsgFact.SENDING_OBSERVER:
                    printer.print("OOClientMsgHandler: SENDING_OBSERVER, se recibe un observer.");
                    break;
                case OOServerMsgFact.SENDING_ID_TO_OBSERVER:
                    printer.print("OOClientMsgHandler: SENDING_ID_TO_OBSERVER, se recibe el id dado por el server.");
                    break;
                case OOServerMsgFact.DONE:
                    printer.print("OOClientMsgHandler: DONE, el server avisó que terminó la solicitud anterior.");
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void update(Object message) {
            this.printer.print("OOClientMsgHandler: " + "Sending message to message handler.");
            this.handleMsg(message);
    }
    
}
