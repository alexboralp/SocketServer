/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions;

import auctions.objects.Client;

/**
 *
 * @author alexander
 */
public class ClientFactory {
    public static Client createClient(String id, String name) {
        return new Client(id, name);
    }
    
    public static Client createClient(String name) {
        return new Client("", name);
    }
}
