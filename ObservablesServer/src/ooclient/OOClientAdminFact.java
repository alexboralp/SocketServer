/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooclient;

import ooserver.admin.OOClientAdmin;
import ooserver.commoninterfaces.OOIPrintable;
import ooserver.commoninterfaces.OOISerializableIdable;
import ooserver.observables.OOIObservableObj;
import ooserver.observables.OOObservableObj;
import ooserver.observers.OOIObserverObj;
import ooserver.observers.OOObserverObj;

/**
 *
 * @author alexander
 */
public class OOClientAdminFact {
    
    
    public static OOClientAdmin createObserverClientAdministrator(String serverName, int port, OOIPrintable printer) {
        return new OOClientAdmin(serverName, port, printer);
    }
    
    public static OOIObserverObj createObserverObj (String owner, OOISerializableIdable object) {
        return new OOObserverObj(owner, object);
    }
    
    public static OOIObservableObj createObservableObj (String owner, OOISerializableIdable object) {
        return new OOObservableObj(owner, object);
    }
}
