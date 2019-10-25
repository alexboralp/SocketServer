/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vip.admin;

import vip.msgs.VIPServerMsgHandler;
import vip.objects.VIPSimpleClient;
import vip.objects.VIPObservableObj;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import ooserver.admin.OOServerAdmin;
import ooserver.observables.OOIObservableObj;
import ooserver.observers.OOIObserverObj;
import ssserver.client.SSIClient;
import vip.interfaces.VIPIPrintable;
import vip.interfaces.VIPIMsgHandler;

/**
 *
 * @author alexander
 */
public class VIPServerAdmin extends OOServerAdmin {

    public VIPServerAdmin(int port, VIPIPrintable printer) {
        super(port, printer);
        msgHandler = new VIPServerMsgHandler(printer, this);
    }

    public VIPServerAdmin(int port, VIPIPrintable printer, VIPIMsgHandler msgHandler) {
        super(port, printer, msgHandler);
    }
    
    public void sendMessageToAllObserversOfObservable(String idObservable, Serializable message) {
        OOIObservableObj observable = (OOIObservableObj)this.getObservableFromServer(idObservable);
        for (OOIObserverObj obj : (Collection<OOIObserverObj>)observable.getObservers()) {
            SSIClient client = this.getClient(((VIPSimpleClient)obj.getObject()).getId());
            try {
                client.sendMessage((Serializable)message);
            } catch (IOException ex) {
                Logger.getLogger(VIPObservableObj.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
