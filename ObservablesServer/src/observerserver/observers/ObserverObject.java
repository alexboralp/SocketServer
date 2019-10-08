/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerserver.observers;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import socketserver.client.IClient;

/**
 *
 * @author alexander
 */
public class ObserverObject extends AbsObserverObject<IClient> {

    public ObserverObject(IClient object) {
        super(object);
    }

    @Override
    public void update(Object message) {
        if (message instanceof Serializable){
            try {
                object.sendMessage((Serializable)message);
            } catch (IOException ex) {
                
                Logger.getLogger(ObserverObject.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
