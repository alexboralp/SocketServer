/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vip.admin;

import ooserver.admin.OOClientAdmin;
import vip.VIPClientFact;
import vip.VIPFamousFact;
import vip.VIPMsgFactForClients;
import vip.VIPMsgFactForFamousClients;
import vip.msgs.VIPClientFamousMsgHandler;
import vip.msgs.VIPClientMsgHandler;
import vip.msgs.VIPAtributeChangedFact;
import vip.objects.VIPSimpleClient;
import vip.objects.VIPFamous;
import vip.objects.VIPFamousMsg;
import vip.interfaces.VIPIPrintable;

/**
 *
 * @author aborbon
 */
public class VIPClientFamousAdmin  extends OOClientAdmin {
    
    private VIPSimpleClient client;
    
    private VIPFamous famous;

    public VIPClientFamousAdmin(String serverName, int port, VIPIPrintable printer) {
        super(serverName, port, printer);
        _init_(printer);
        
        msgHandler = new VIPClientFamousMsgHandler(printer, this);
        this.replaceMsgHandler(msgHandler);
    }

    public VIPClientFamousAdmin(String serverName, int port, VIPIPrintable printer, VIPClientMsgHandler msgHandler) {
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
    }
    
    // Mensajes al servidor de realizar alguna acción
    
    public void addMeAsAClient() {
        if (client.getId() != null && client.getName() != null) {
            famous = VIPFamousFact.createFamous(client.getId(), client.getName());
            printer.print("AuctionsClientAdmin: " + "Asking server to add me as observer.");
            this.sendMessage(VIPMsgFactForClients.createMsg(VIPMsgFactForFamousClients.ADD_ME_TO_OBSERVERS, client));
            this.sendMessage(VIPMsgFactForClients.createMsg(VIPMsgFactForFamousClients.NEW_FAMOUS, famous));
        }
    }
    
    public void addMsg(String msg) {
        VIPFamousMsg message = VIPFamousFact.createFamousMsg(Integer.toString(famous.getMsgsNumber() + 1), msg, this.getMyId());
        this.sendMessage(VIPMsgFactForClients.createMsg(VIPMsgFactForFamousClients.NEW_MESSAGE, message));
    }
    
    // Mensajes para el GUI sobre el cambio en algún atributo
    
    public void GUINewMsg(VIPFamousMsg msg) {
        famous.addMsg(msg);
        this.updateAll(VIPAtributeChangedFact.createAuctionsAtributeChanged(VIPAtributeChangedFact.NEW_MESSAGE, msg));
    }
    
    public void GUIShowMsg(String message) {
        this.updateAll(VIPAtributeChangedFact.createAuctionsAtributeChanged(VIPAtributeChangedFact.SHOW_MESSAGE, message));
    }
    
    public void GUILike(String msgId) {
        VIPFamousMsg msg = getFamous().getMsg(msgId);
        msg.setLikes(msg.getLikes() + 1);
        printer.print("Un like más para el mensaje " + msgId + ".");
        this.updateAll(VIPAtributeChangedFact.createAuctionsAtributeChanged(VIPAtributeChangedFact.MESSAGE_LIKE, msgId));
    }
    
    public void GUIDislike(String msgId) {
        VIPFamousMsg msg = getFamous().getMsg(msgId);
        msg.setDislikes(msg.getDislikes() + 1);
        printer.print("Un dislike más para el mensaje " + msgId + ".");
        this.updateAll(VIPAtributeChangedFact.createAuctionsAtributeChanged(VIPAtributeChangedFact.MESSAGE_DISLIKE, msgId));
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

    public VIPFamous getFamous() {
        return famous;
    }
}
