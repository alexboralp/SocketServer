/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions.objects;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import ooserver.commoninterfaces.OOISerializableIdable;
import ooserver.observables.OOIObservableObj;
import ooserver.observables.OOObservableObj;
import ooserver.observers.OOIObserverObj;
import ssserver.client.SSIClient;

/**
 *
 * @author alexander
 */
public class AuctionsObservableObj extends OOObservableObj{
    
    public AuctionsObservableObj(String ownerId, OOISerializableIdable object) {
        super(ownerId, object);
    }
    
    public AuctionsObservableObj(OOIObservableObj obj) {
        super(obj.getOwnerId(), (OOISerializableIdable)obj.getObject());
    }
    
    @Override
    public void update(OOIObserverObj observer, Object message) {
        SSIClient client = (SSIClient)observer.getObject();
        try {
            client.sendMessage((Serializable)message);
        } catch (IOException ex) {
            Logger.getLogger(AuctionsObservableObj.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateAll(Object message) {
        for (OOIObserverObj obj : (Collection<OOIObserverObj>)this.getObservers()) {
            SSIClient client = (SSIClient)obj.getObject();
            try {
                client.sendMessage((Serializable)message);
            } catch (IOException ex) {
                Logger.getLogger(AuctionsObservableObj.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
