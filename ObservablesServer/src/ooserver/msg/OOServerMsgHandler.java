/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooserver.msg;

import ooclient.OOClientMsgFact;
import ooserver.OOServerAdminFact;
import ooserver.OOServerMsgFact;
import ooserver.admin.OOServerAdmin;
import ooserver.client.OOISimpleClient;
import ooserver.commoninterfaces.OOIMsg;
import ooserver.commoninterfaces.OOIMsgHandler;
import ooserver.commoninterfaces.OOIPrintable;
import ooserver.commoninterfaces.OOISerializableIdable;
import ssserver.msg.SSServerMsgHandler;

/**
 *
 * @author alexander
 */
public class OOServerMsgHandler extends SSServerMsgHandler implements OOIMsgHandler {
    
    protected OOServerAdmin ooAdmin;

    public OOServerMsgHandler(OOIPrintable printer, OOServerAdmin admin) {
        super(printer, admin);
        ooAdmin = admin;
    }
    
    @Override
    public void handleMsg(Object message) {
        this.printer.print("OOServerMsgHandler: " + "New message received.");
        super.handleMsg(message);
        if (message instanceof OOIMsg) {
            OOIMsg msg = (OOIMsg)message;
            printer.print("OOServerMsgHandler: Message received: " + message.toString());
            switch (msg.getType()) {
                case OOClientMsgFact.ADD_OBSERVABLE:
                    printer.print("OOServerMsgHandler: ADD_OBSERVABLE.");
                    OOISerializableIdable observable = (OOISerializableIdable)msg.getMessage();
                    if (ooAdmin.getObservableFromServer(observable.getId()) == null) {
                        ooAdmin.addObservableToServer(OOServerAdminFact.createObservableObj(msg.getId(), (OOISerializableIdable)msg.getMessage()));
                    } else {
                        ooAdmin.sendMessageToClient(msg.getId(), OOServerMsgFact.createMsg(OOServerMsgFact.ID_REPEATED, null));
                    }
                    break;
                case OOClientMsgFact.REMOVE_OBSERVABLE:
                    printer.print("OOServerMsgHandler: REMOVE_OBSERVABLE.");
                    ooAdmin.removeObservableFromServer((String)msg.getMessage());
                    break;
                case OOClientMsgFact.FOLLOW_OBSERVABLE:
                    printer.print("OOServerMsgHandler: FOLLOW_OBSERVABLE.");
                    ooAdmin.setObserverToObservable(msg.getId(), (String)msg.getMessage());
                    break;
                case OOClientMsgFact.UNFOLLOW_OBSERVABLE:
                    printer.print("OOServerMsgHandler: UNFOLLOW_OBSERVABLE.");
                    ooAdmin.removeObserverFromObservable(msg.getId(), (String)msg.getMessage());
                    break;
                case OOClientMsgFact.REMOVE_ME_FROM_OBSERVERS:
                    printer.print("OOServerMsgHandler: REMOVE_ME_FROM_OBSERVERS.");
                    ooAdmin.removeObserverFromServer(msg.getId());
                    break;
                case OOClientMsgFact.ADD_ME_TO_OBSERVERS:
                    printer.print("OOServerMsgHandler: ADD_ME_TO_OBSERVERS.");
                    ooAdmin.addObserverToServer(OOServerAdminFact.createObserverObj(msg.getId(), (OOISimpleClient)msg.getMessage()));
                    break;
                case OOClientMsgFact.ADD_OBSERVER:
                    printer.print("OOServerMsgHandler: ADD_OBSERVER.");
                    ooAdmin.addObserverToServer(OOServerAdminFact.createObserverObj(msg.getId(), (OOISerializableIdable)msg.getMessage()));
                    break;
                case OOClientMsgFact.SEND_ALL_OBSERVERS:
                    printer.print("OOServerMsgHandler: SEND_ALL_OBSERVERS.");
                    ooAdmin.sendObserversToClient(msg.getId());
                    break;
                case OOClientMsgFact.SEND_ALL_OBSERVABLES:
                    printer.print("OOServerMsgHandler: SEND_ALL_OBSERVABLES.");
                    ooAdmin.sendObservablesToClient(msg.getId());
                    break;
                case OOClientMsgFact.SEND_MY_ID:
                    printer.print("OOServerMsgHandler: SEND_MY_ID.");
                    ooAdmin.sendIdToClient(msg.getId());
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void update(Object message) {
            this.printer.print("OOServerMsgHandler: " + "Sending message to message handler.");
            this.handleMsg(message);
    }
    
}
