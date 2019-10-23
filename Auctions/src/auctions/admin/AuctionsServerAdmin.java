/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions.admin;

import auctions.interfaces.AuctionsIMsgHandler;
import auctions.interfaces.AuctionsIPrintable;
import auctions.msgs.AuctionsServerMsgHandler;
import auctions.objects.AuctionSimpleClient;
import auctions.objects.AuctionsObservableObj;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import ooserver.admin.OOServerAdmin;
import ooserver.observables.OOIObservableObj;
import ooserver.observers.OOIObserverObj;
import ssserver.client.SSIClient;

/**
 *
 * @author alexander
 */
public class AuctionsServerAdmin extends OOServerAdmin {

    public AuctionsServerAdmin(int port, AuctionsIPrintable printer) {
        super(port, printer);
        msgHandler = new AuctionsServerMsgHandler(printer, this);
    }

    public AuctionsServerAdmin(int port, AuctionsIPrintable printer, AuctionsIMsgHandler msgHandler) {
        super(port, printer, msgHandler);
    }
    
    public void sendMessageToAllObserversOfAbservable(String idObservable, Serializable message) {
        OOIObservableObj observable = (OOIObservableObj)this.getObservableFromServer(idObservable);
        printer.print("AuctionsServerAdmin: " + "Todo bien, mandando mensaje a los seguidores la subasta " + idObservable);
        System.out.println("AuctionsObservableObj: " + "Aquí estoy");
        for (OOIObserverObj obj : (Collection<OOIObserverObj>)observable.getObservers()) {
            SSIClient client = this.getClient(((AuctionSimpleClient)obj.getObject()).getId());
            try {
                System.out.println("AuctionsObservableObj: " + "Todo bien, mandando mensaje al cliente: " + client.getId());
                client.sendMessage((Serializable)message);
            } catch (IOException ex) {
                Logger.getLogger(AuctionsObservableObj.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
