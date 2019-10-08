/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerserver.observers;

import socketserver.client.IClient;

/**
 *
 * @author alexander
 */
public class ObserverObjectFactory {
    public static ObserverObject create(IClient object) {
        return new ObserverObject(object);
    }
}
