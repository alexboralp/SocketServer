/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vip.msgs;

import ooserver.commoninterfaces.OOIMsg;
import vip.VIPMsgFactForClients;
import vip.VIPMsgFactForServer;
import vip.admin.VIPServerAdmin;
import vip.objects.VIPFamous;
import ooserver.msg.OOServerMsgHandler;
import ooserver.observables.OOIObservableObj;
import vip.VIPMsgFactForFamousClients;
import vip.objects.VIPFamousMsg;
import vip.interfaces.VIPIPrintable;
import vip.interfaces.VIPIMsgHandler;

/**
 *
 * @author alexander
 */
public class VIPServerMsgHandler extends OOServerMsgHandler implements VIPIMsgHandler {
    
    protected VIPServerAdmin aAdmin;
    
    public VIPServerMsgHandler(VIPIPrintable printer, VIPServerAdmin admin) {
        super(printer, admin);
        aAdmin = admin;
    }
    
    @Override
    public void handleMsg(Object message) {
        this.printer.print("VIPServerMsgHandler: " + "New message received.");
        Object msgObj;
        if (message instanceof OOIMsg) {
            msgObj = VIPMsgFactForServer.createMsg((OOIMsg)message);
        } else {
            msgObj = message;
        }
        if (msgObj instanceof VIPMsg) {
            VIPMsg msg = (VIPMsg)msgObj;
            switch(msg.getType()) {
                case VIPMsgFactForClients.LIKE:
                    printer.print("VIPServerMsgHandler: " + "Se le dio like a un mensaje.");
                    VIPMsgLike like = (VIPMsgLike)msg.getMessage();
                    VIPFamousMsg msgLike = ((VIPFamous)aAdmin.getObservableFromServer(like.getFamousId()).getObject()).getMsg(like.getMsgId());
                    msgLike.setLikes(msgLike.getLikes() + 1);
                    //aAdmin.sendMessageToClient(msgLike.getOwnerId(), msg);
                    aAdmin.sendMessageToAllObservers(msg);
                    if (msgLike.getLikes() % 10 == 0) {
                        VIPMsg msgLike2 = VIPMsgFactForServer.createMsg(VIPMsgFactForServer.SHOW_MESSAGE, "El mensaje " +  msgLike.getId() + " del famoso " + msgLike.getOwnerId() + " llegó a " + msgLike.getLikes() + " likes.");
                        aAdmin.sendMessageToAllObserversOfAbservable(msgLike.getOwnerId(), msgLike2);
                        aAdmin.sendMessageToClient(like.getFamousId(), msgLike2);
                    }
                    break;
                case VIPMsgFactForClients.DISLIKE:
                    printer.print("VIPServerMsgHandler: " + "Se le dio dislike a un mensaje.");
                    VIPMsgLike dislike = (VIPMsgLike)msg.getMessage();
                    VIPFamousMsg msgDislike = ((VIPFamous)aAdmin.getObservableFromServer(dislike.getFamousId()).getObject()).getMsg(dislike.getMsgId());
                    msgDislike.setDislikes(msgDislike.getDislikes() + 1);
                    //aAdmin.sendMessageToClient(msgDislike.getOwnerId(), msg);
                    aAdmin.sendMessageToAllObservers(msg);
                    if (msgDislike.getDislikes() % 10 == 0) {
                        VIPMsg msgDislike2 = VIPMsgFactForServer.createMsg(VIPMsgFactForServer.SHOW_MESSAGE, "El mensaje " +  msgDislike.getId() + " del famoso " + msgDislike.getOwnerId() + " llegó a " + msgDislike.getDislikes() + " dislikes.");
                        aAdmin.sendMessageToAllObserversOfAbservable(msgDislike.getOwnerId(), msgDislike2);
                        aAdmin.sendMessageToClient(dislike.getFamousId(), msgDislike2);
                    }
                    break;
                case VIPMsgFactForFamousClients.NEW_FAMOUS: //Listo
                    printer.print("VIPServerMsgHandler: " + "Se agregó un famoso, se envía el nuevo famoso a todos los clientes.");
                    VIPFamous newFamous = (VIPFamous)msg.getMessage();
                    if (aAdmin.getObservableFromServer(newFamous.getId()) == null) {
                        aAdmin.sendMessageToAllObservers(VIPMsgFactForServer.createMsg(VIPMsgFactForServer.SENDING_FAMOUS, newFamous));
                    }
                    break;
                case VIPMsgFactForFamousClients.NEW_MESSAGE: //Listo
                    printer.print("VIPServerMsgHandler: " + "Se agregó un mensaje nuevo de un famoso, se envía el nuevo mensaje a todos los clientes.");
                    VIPFamousMsg newFamousMsg = (VIPFamousMsg)msg.getMessage();
                    ((VIPFamous)aAdmin.getObservableFromServer(newFamousMsg.getOwnerId()).getObject()).addMsg(newFamousMsg);
                    //aAdmin.sendMessageToClient(msg.getId(), VIPMsgFactForServer.createMsg(VIPMsgFactForServer.SENDING_MESSAGE, newFamousMsg));
                    aAdmin.sendMessageToAllObservers(VIPMsgFactForServer.createMsg(VIPMsgFactForServer.SENDING_MESSAGE, newFamousMsg));
                    break;
                case VIPMsgFactForClients.FOLLOW_FAMOUS: //Listo
                    printer.print("VIPServerMsgHandler: " + "El cliente " + msg.getId() + " solicita seguir al famoso " + (String)msg.getMessage() + ".");
                    OOIObservableObj observable1 = aAdmin.getObservableFromServer((String)msg.getMessage());
                    observable1.addObserver(aAdmin.getObserverFromServer(msg.getId()));
                    aAdmin.sendMessageToClient((String)msg.getMessage(), msg);
                    aAdmin.sendMessageToClient(msg.getId(), msg);
                    printer.print("VIPServerMsgHandler: " + "La cantidad de seguidores del famoso " + (String)msg.getMessage() + " llegó a " + observable1.getObservers().size() + ".");
                    if (observable1.getObservers().size() % 10 == 0) {
                        int level = observable1.getObservers().size() / 10;
                        VIPFamous famous = (VIPFamous)observable1.getObject();
                        //printer.print("Aquí estoy");
                        if (famous.getLevel() != level) {
                            famous.setLevel(level);
                            aAdmin.sendMessageToAllObserversOfAbservable((String)msg.getMessage(), VIPMsgFactForServer.createMsg(VIPMsgFactForServer.SHOW_MESSAGE, "El famoso " + (String)msg.getMessage() + " ha llegado al nivel " + level + "."));
                            aAdmin.sendMessageToClient((String)msg.getMessage(), VIPMsgFactForServer.createMsg(VIPMsgFactForServer.SHOW_MESSAGE, "Has bajado al nivel " + level + "."));
                            //printer.print("Aquí estoy2");
                        }
                    }
                    break;
                case VIPMsgFactForClients.UNFOLLOW_FAMOUS: //Listo
                    printer.print("VIPServerMsgHandler: " + "El cliente " + msg.getId() + " solicita dejar de seguir al famoso " + (String)msg.getMessage() + ".");
                    OOIObservableObj observable2 = aAdmin.getObservableFromServer((String)msg.getMessage());
                    observable2.removeObserver(msg.getId());
                    aAdmin.sendMessageToClient((String)msg.getMessage(), msg);
                    aAdmin.sendMessageToClient(msg.getId(), msg);
                    if (observable2.getObservers().size() % 10 == 0) {
                        int level = (observable2.getObservers().size() / 10);
                        VIPFamous famous = (VIPFamous)observable2.getObject();
                        if (famous.getLevel() != level) {
                            famous.setLevel(level);
                            aAdmin.sendMessageToAllObserversOfAbservable(msg.getId(), VIPMsgFactForServer.createMsg(VIPMsgFactForServer.SHOW_MESSAGE, "El famoso " + (String)msg.getMessage() + " ha llegado al nivel " + level + "."));
                            aAdmin.sendMessageToClient((String)msg.getMessage(), VIPMsgFactForServer.createMsg(VIPMsgFactForServer.SHOW_MESSAGE, "Has bajado al nivel " + level + "."));
                        }
                    }
                    break;
                /*case VIPMsgFactForFamousClients.NEW_MESSAGE:
                    printer.print("VIPServerMsgHandler: " + "Solicitud de eliminar una subasta.");
                    aAdmin.removeObservableFromServer((String)msg.getMessage());
                    break;*/
                case VIPMsgFactForClients.SEND_ALL_FAMOUS: //Listo
                    printer.print("VIPServerMsgHandler: " + "Solicitud de enviar todas las subastas.");
                    aAdmin.sendObservablesToClient(msg.getId());
                    break;
                case VIPMsgFactForClients.CLOSE_CONNECTION: //Listo
                    printer.print("VIPServerMsgHandler: " + "Close conection.");
                    break;
                default:
                    break;
            }
        }
        super.handleMsg(message);
    }

    @Override
    public void update(Object message) {
            this.printer.print("VIPServerMsgHandler: " + "Sending message to message handler.");
            this.handleMsg(message);
    }
    
}
