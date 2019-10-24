/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vip.msgs;

import vip.VIPMsgFactForClients;
import vip.VIPMsgFactForServer;
import vip.admin.VIPClientAdmin;
import vip.objects.VIPFamous;
import ooserver.commoninterfaces.OOIMsg;
import ooserver.msg.OOClientMsgHandler;
import vip.objects.VIPFamousMsg;
import vip.interfaces.VIPIPrintable;
import vip.interfaces.VIPIMsg;
import vip.interfaces.VIPIMsgHandler;

/**
 *
 * @author alexander
 */
public class VIPClientMsgHandler extends OOClientMsgHandler implements VIPIMsgHandler {
    
    VIPClientAdmin aAdmin;
    
    public VIPClientMsgHandler(VIPIPrintable printer, VIPClientAdmin admin) {
        super(printer, admin);
        aAdmin = admin;
    }
    
    public void like(VIPMsgLike msgLike) {
        VIPFamousMsg msg = aAdmin.getFamous().get(msgLike.getFamousId()).getMessage(msgLike.getMsgId());
        msg.setLikes(msg.getLikes() + 1);
        printer.print("Un like más para el mensaje " + msgLike.getMsgId() + " del famoso " + msgLike.getFamousId() + ".");
    }
    
    public void dislike(VIPMsgLike msgLike) {
        VIPFamousMsg msg = aAdmin.getFamous().get(msgLike.getFamousId()).getMessage(msgLike.getMsgId());
        msg.setDislikes(msg.getDislikes() + 1);
        printer.print("Un like más para el mensaje " + msgLike.getMsgId() + " del famoso " + msgLike.getFamousId() + ".");
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
                    like((VIPMsgLike)msg.getMessage());
                    break;
                case VIPMsgFactForClients.DISLIKE:
                    dislike((VIPMsgLike)msg.getMessage());
                    break;
                case VIPMsgFactForClients.FOLLOW_FAMOUS: // Listo
                    aAdmin.GUIFollowFamous((String)msg.getMessage());
                    break;
                case VIPMsgFactForClients.UNFOLLOW_FAMOUS: // Listo
                    aAdmin.GUIUnfollowAuction((String)msg.getMessage());
                    break;
                case VIPMsgFactForServer.INFO: // Listo
                    printer.print("AuctionsClientMsgHandler: Se recibió el mensaje: " + (String)msg.getMessage());
                    break;
                case VIPMsgFactForServer.SENDING_ALL_FAMOUS:
                    printer.print("AuctionsClientMsgHandler: Se van a recibir todas los famosos.");
                    aAdmin.GUIDeleteAllFamous();
                    break;
                case VIPMsgFactForServer.SENDING_FAMOUS:
                    printer.print("AuctionsClientMsgHandler: Se recibe un famoso.");
                    aAdmin.GUIAddFamous((VIPFamous)msg.getMessage());
                    break;
                case VIPMsgFactForServer.TEXT_MESSAGE:
                    aAdmin.GUIShowMessage((String)msg.getMessage());
                    break;
                case VIPMsgFactForServer.TEXT_MESSAGE_TO_OBSERVER:
                    aAdmin.GUIShowMessage((String)msg.getMessage());
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
