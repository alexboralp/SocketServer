/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vip.msgs;

import vip.VIPMsgFactForClients;
import vip.VIPMsgFactForServer;
import ooserver.commoninterfaces.OOIMsg;
import ooserver.msg.OOClientMsgHandler;
import vip.admin.VIPClientFamousAdmin;
import vip.objects.VIPFamousMsg;
import vip.interfaces.VIPIPrintable;
import vip.interfaces.VIPIMsg;
import vip.interfaces.VIPIMsgHandler;

/**
 *
 * @author alexander
 */
public class VIPClientFamousMsgHandler extends OOClientMsgHandler implements VIPIMsgHandler {
    
    VIPClientFamousAdmin aAdmin;
    
    public VIPClientFamousMsgHandler(VIPIPrintable printer, VIPClientFamousAdmin admin) {
        super(printer, admin);
        aAdmin = admin;
    }
    
    private void like(String msgId) {
        printer.print("VIPClientFamousMsgHandler: Se le dio like al mensaje " + msgId);
        aAdmin.GUILike(msgId);
    }
    
    private void dislike(String msgId) {
        printer.print("VIPClientFamousMsgHandler: Se le dio dislike al mensaje " + msgId);
        aAdmin.GUIDislike(msgId);
    }
    
    public void messageReceived(VIPFamousMsg msg){
        aAdmin.GUINewMsg(msg);
    }

    @Override
    public void handleMsg(Object message) {
        this.printer.print("VIPClientFamousMsgHandler: " + "New message received.");
        super.handleMsg(message);
        Object msgObj;
        if (message instanceof OOIMsg) {
            msgObj = VIPMsgFactForServer.createMsg((OOIMsg)message);
        } else {
            msgObj = message;
        }
        if (msgObj instanceof VIPIMsg) {
            VIPIMsg msg = (VIPIMsg)msgObj;
            printer.print("VIPClientFamousMsgHandler: Nuevo mensaje recibido: " + msg.toString());
            switch(msg.getType()) {
                case VIPMsgFactForClients.LIKE:
                    VIPMsgLike likeMsg = (VIPMsgLike)msg.getMessage();
                    like(likeMsg.getMsgId());
                    break;
                case VIPMsgFactForClients.DISLIKE:
                    VIPMsgLike dislikeMsg = (VIPMsgLike)msg.getMessage();
                    dislike(dislikeMsg.getMsgId());
                    break;
                case VIPMsgFactForServer.SENDING_MESSAGE:
                    VIPFamousMsg famousMsg = (VIPFamousMsg)msg.getMessage();
                    printer.print("VIPClientFamousMsgHandler: Se recibió el mensaje del famoso: " + famousMsg.toString());
                    if (famousMsg.getOwnerId().equals(aAdmin.getMyId())) {
                        messageReceived((VIPFamousMsg)msg.getMessage());
                    }
                    break;
                case VIPMsgFactForServer.SHOW_MESSAGE:
                    printer.print("VIPClientFamousMsgHandler: Se recibió el mensaje: " + (String)msg.getMessage());
                    aAdmin.GUIShowMsg((String)msg.getMessage());
                    break;
                case VIPMsgFactForServer.INFO:
                    printer.print("VIPClientFamousMsgHandler: Se recibió el mensaje: " + (String)msg.getMessage());
                    break;
                case VIPMsgFactForServer.MESSAGE_ID_REPEATED:
                    printer.print("VIPClientFamousMsgHandler: El id del mensaje ya fue utilizado.");
                    aAdmin.GUIShowMsg("El id de la subasta ya fue utilizado.");
                    break;
                case VIPMsgFactForServer.TEXT_MESSAGE_TO_OBSERVER:
                    printer.print("VIPClientFamousMsgHandler: Se recibió el mensaje: " + (String)msg.getMessage());
                    break;
                case VIPMsgFactForServer.DONE:
                    printer.print("VIPClientFamousMsgHandler: Todo realizado de parte del servidor.");
                    break;
                case VIPMsgFactForServer.CLOSE_CONNECTION:
                    printer.print("VIPClientFamousMsgHandler: El servidor solicita cerrar la conexión.");
                    break;
                case VIPMsgFactForServer.CHECKING_CONNECTION:
                    printer.print("El servidor solicitó checkar la conexión, se mandó un mensaje de confirmación.");
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void update(Object message) {
            this.printer.print("VIPClientFamousMsgHandler: " + "Sending message to message handler.");
            this.handleMsg(message);
    }
    
}
