/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vip.admin;

import vip.VIPClientFact;
import vip.VIPMsgFactForClients;
import vip.msgs.VIPAtributeChangedFact;
import vip.msgs.VIPClientMsgHandler;
import vip.objects.VIPFamous;
import vip.objects.VIPSimpleClient;
import vip.objects.VIPFamousList;
import ooserver.admin.OOClientAdmin;
import vip.msgs.VIPMsgLike;
import vip.objects.VIPFamousMsg;
import vip.interfaces.VIPIPrintable;

/**
 *
 * @author alexander
 */
    public class VIPClientAdmin extends OOClientAdmin {
    
    private VIPSimpleClient client;
    
    private VIPFamousList famous;
    private VIPFamousList followedFamous;

    public VIPClientAdmin(String serverName, int port, VIPIPrintable printer) {
        super(serverName, port, printer);
        _init_(printer);
        
        msgHandler = new VIPClientMsgHandler(printer, this);
        this.replaceMsgHandler(msgHandler);
    }

    public VIPClientAdmin(String serverName, int port, VIPIPrintable printer, VIPClientMsgHandler msgHandler) {
        super(serverName, port, printer, msgHandler);
        _init_(printer);
    }
    
    private void _init_(VIPIPrintable printer) {
        if (!this.isOk()) {
            printer.printError("AuctionsClientAdmin: " + "No se pudo establecer la conexión.");
            System.exit(1);
        }
        
        //printer.print("AuctionsClientAdmin: " + "Conexión establecida.");
        //printer.print("AuctionsClientAdmin: " + "Solicitando nuestro ID.");
        //this.sendMessage(AuctionsMsgFactForClients.createMsg(AuctionsMsgFactForClients.SEND_MY_ID, null));
        
        client = VIPClientFact.createClient(this.getMyId(), null);

        famous = new VIPFamousList();
        followedFamous = new VIPFamousList();
    }
    
    // Mensajes al servidor de realizar alguna acción
    
    public void addMeAsAClient() {
        if (client.getId() != null && client.getName() != null) {
            printer.print("AuctionsClientAdmin: " + "Asking server to add me as observer.");
            this.sendMessage(VIPMsgFactForClients.createMsg(VIPMsgFactForClients.ADD_ME_TO_OBSERVERS, client));
            this.sendMessage(VIPMsgFactForClients.createMsg(VIPMsgFactForClients.SEND_ALL_FAMOUS, null));
        }
    }
    
    public void followFamous(String idFamous) {
        this.sendMessage(VIPMsgFactForClients.createMsg(VIPMsgFactForClients.FOLLOW_FAMOUS, idFamous));
    }
    
    public void unfollowFamous(String idFamous) {
        this.sendMessage(VIPMsgFactForClients.createMsg(VIPMsgFactForClients.UNFOLLOW_FAMOUS, idFamous));
    }
    
    public void likeMsg(String idFamous, String idMsg) {
        VIPMsgLike like = VIPMsgFactForClients.createMsgLike(idFamous, idMsg);
        this.sendMessage(VIPMsgFactForClients.createMsg(VIPMsgFactForClients.LIKE, like));
    }
    
    public void dislikeMsg(String idFamous, String idMsg) {
        VIPMsgLike like = VIPMsgFactForClients.createMsgLike(idFamous, idMsg);
        this.sendMessage(VIPMsgFactForClients.createMsg(VIPMsgFactForClients.DISLIKE, like));
    }
    
    // Mensajes para el GUI sobre el cambio en algún atributo
    
    public void GUIAddFamous(VIPFamous famous) {
        this.famous.add(famous);
        this.updateAll(VIPAtributeChangedFact.createAuctionsAtributeChanged(VIPAtributeChangedFact.NEW_FAMOUS, famous));
    }
    
    public void GUINewMessage(VIPFamousMsg msg) {
        this.getFamous(msg.getOwnerId()).addMessage(msg);
        this.updateAll(VIPAtributeChangedFact.createAuctionsAtributeChanged(VIPAtributeChangedFact.NEW_MESSAGE, msg));
    }
    
    public void GUIFollowFamous(String famousId) {
        followedFamous.add(famous.get(famousId));
        this.updateAll(VIPAtributeChangedFact.createAuctionsAtributeChanged(VIPAtributeChangedFact.FOLLOW_FAMOUS, famousId));
    }
    
    public void GUIUnfollowAuction(String famousId) {
        followedFamous.remove(famous.get(famousId));
        this.updateAll(VIPAtributeChangedFact.createAuctionsAtributeChanged(VIPAtributeChangedFact.UNFOLLOW_FAMOUS, famousId));
    }
    
    public void GUIDeleteAllFamous() {
        famous = new VIPFamousList();
        this.updateAll(VIPAtributeChangedFact.createAuctionsAtributeChanged(VIPAtributeChangedFact.DELETE_ALL_FAMOUS, null));
    }
    
    public void GUIMsgLike(VIPMsgLike msg) {
        VIPFamousMsg famousMsg = getFamous(msg.getFamousId()).getMessage(msg.getMsgId());
        famousMsg.setLikes(famousMsg.getLikes() + 1);
        this.updateAll(VIPAtributeChangedFact.createAuctionsAtributeChanged(VIPAtributeChangedFact.MESSAGE_LIKE, msg));
    }
    
    public void GUIMsgDislike(VIPMsgLike msg) {
        VIPFamousMsg famousMsg = getFamous(msg.getFamousId()).getMessage(msg.getMsgId());
        famousMsg.setDislikes(famousMsg.getDislikes() + 1);
        this.updateAll(VIPAtributeChangedFact.createAuctionsAtributeChanged(VIPAtributeChangedFact.MESSAGE_DISLIKE, msg));
    }
    
    public void GUIShowMessage(String message) {
        this.updateAll(VIPAtributeChangedFact.createAuctionsAtributeChanged(VIPAtributeChangedFact.SHOW_MESSAGE, message));
    }
    
    // Setter y Getter del administrador

    public void setClientName(String name) {
        client.setName(name);
        addMeAsAClient();
    }

    public void setClientId(String clientId) {
        client.setId(clientId);
        if (client.getName() != null) {
            addMeAsAClient();
        }
    }

    public VIPSimpleClient getClient() {
        return client;
    }

    public VIPFamousList getFamous() {
        return famous;
    }

    public VIPFamousList getFollowedFamous() {
        return followedFamous;
    }
    
    public VIPFamous getFamous(String auctionId) {
        return famous.get(auctionId);
    }
}
