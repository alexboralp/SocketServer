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
import vip.VIPMsgFactForFamousClients;
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
        printer.print("VIPClientMsgHandler: Se le dio like al mensaje " + msgLike.getMsgId() + " del famoso " + msgLike.getFamousId() + ".");
        aAdmin.GUIMsgLike(msgLike);
    }
    
    public void dislike(VIPMsgLike msgDislike) {
        printer.print("VIPClientMsgHandler: Se le dio dislike al mensaje " + msgDislike.getMsgId() + " del famoso " + msgDislike.getFamousId() + ".");
        aAdmin.GUIMsgDislike(msgDislike);
    }

    @Override
    public void handleMsg(Object message) {
        this.printer.print("VIPClientMsgHandler: " + "New message received.");
        super.handleMsg(message);
        Object msgObj;
        if (message instanceof OOIMsg) {
            msgObj = VIPMsgFactForServer.createMsg((OOIMsg)message);
        } else {
            msgObj = message;
        }
        if (msgObj instanceof VIPIMsg) {
            VIPIMsg msg = (VIPIMsg)msgObj;
            printer.print("VIPClientMsgHandler: Nuevo mensaje recibido: " + msg.toString());
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
                    printer.print("VIPClientMsgHandler: Se recibió el mensaje: " + (String)msg.getMessage());
                    break;
                case VIPMsgFactForServer.SENDING_ALL_FAMOUS:
                    printer.print("VIPClientMsgHandler: Se van a recibir todas los famosos.");
                    aAdmin.GUIDeleteAllFamous();
                    break;
                case VIPMsgFactForServer.SENDING_FAMOUS:
                    printer.print("VIPClientMsgHandler: Se recibe un famoso.");
                    aAdmin.GUIAddFamous((VIPFamous)msg.getMessage());
                    break;
                case VIPMsgFactForServer.SENDING_MESSAGE:
                    printer.print("VIPClientMsgHandler: Se recibe un nuevo mensaje de un famoso.");
                    aAdmin.GUINewMsg((VIPFamousMsg)msg.getMessage());
                    break;
                case VIPMsgFactForServer.SHOW_MESSAGE:
                    aAdmin.GUIShowMessage((String)msg.getMessage());
                    break;
                case VIPMsgFactForServer.TEXT_MESSAGE_TO_OBSERVER:
                    aAdmin.GUIShowMessage((String)msg.getMessage());
                    break;
                case VIPMsgFactForFamousClients.LEFT_SOCIAL_NETWORK: // Listo
                    aAdmin.GUIDeleteFamous((String)msg.getMessage());
                    break;
                case VIPMsgFactForServer.DONE:
                    printer.print("VIPClientMsgHandler: Todo realizado de parte del servidor.");
                    break;
                case VIPMsgFactForServer.CLOSE_CONNECTION:
                    printer.print("VIPClientMsgHandler: El servidor solicita cerrar la conexión.");
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
            this.printer.print("VIPClientMsgHandler: " + "Sending message to message handler.");
            this.handleMsg(message);
    }
    
}
