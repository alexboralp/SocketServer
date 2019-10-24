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
        printer.print("AuctionsClientMsgHandler: Se le dio like al mensaje " + msgId);
        VIPFamousMsg msg = aAdmin.getFamous().getMessage(msgId);
        msg.setLikes(msg.getLikes() + 1);
        printer.print("Un like más para el mensaje " + msgId + ".");
    }
    
    private void dislike(String msgId) {
        printer.print("AuctionsClientMsgHandler: Se le dio like al mensaje " + msgId);
        VIPFamousMsg msg = aAdmin.getFamous().getMessage(msgId);
        msg.setDislikes(msg.getDislikes() + 1);
        printer.print("Un like más para el mensaje " + msgId + ".");
    }
    
    public void messageReceived(VIPFamousMsg msg){
        aAdmin.GUINewMessage(msg);
    }

    @Override
    public void handleMsg(Object message) {
        this.printer.print("AuctionsClientMsgHandler: " + "New message received.");
        super.handleMsg(message);
        Object msgObj;
        if (message instanceof OOIMsg) {
            msgObj = VIPMsgFactForServer.createMsg((OOIMsg)message);
        } else {
            msgObj = message;
        }
        if (msgObj instanceof VIPIMsg) {
            VIPIMsg msg = (VIPIMsg)msgObj;
            printer.print("AuctionsClientMsgHandler: Nuevo mensaje recibido: " + msg.toString());
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
                    printer.print("AuctionsClientMsgHandler: Se recibió el mensaje del famoso: " + famousMsg.toString());
                    if (famousMsg.getOwnerId().equals(aAdmin.getMyId())) {
                        messageReceived((VIPFamousMsg)msg.getMessage());
                    }
                    break;
                case VIPMsgFactForServer.INFO:
                    printer.print("AuctionsClientMsgHandler: Se recibió el mensaje: " + (String)msg.getMessage());
                    break;
                case VIPMsgFactForServer.MESSAGE_ID_REPEATED:
                    printer.print("AuctionsClientMsgHandler: El id del mensaje ya fue utilizado.");
                    aAdmin.GUIShowMessage("El id de la subasta ya fue utilizado.");
                    break;
                case VIPMsgFactForServer.TEXT_MESSAGE:
                    printer.print("AuctionsClientMsgHandler: Se recibió el mensaje: " + (String)msg.getMessage());
                    break;
                case VIPMsgFactForServer.TEXT_MESSAGE_TO_OBSERVER:
                    printer.print("AuctionsClientMsgHandler: Se recibió el mensaje: " + (String)msg.getMessage());
                    break;
                case VIPMsgFactForServer.DONE:
                    printer.print("AuctionsClientMsgHandler: Todo realizado de parte del servidor.");
                    break;
                case VIPMsgFactForServer.CLOSE_CONNECTION:
                    printer.print("AuctionsClientMsgHandler: El servidor solicita cerrar la conexión.");
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
            this.printer.print("AuctionsClientMsgHandler: " + "Sending message to message handler.");
            this.handleMsg(message);
    }
    
}
