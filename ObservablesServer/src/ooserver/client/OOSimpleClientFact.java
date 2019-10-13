/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooserver.client;

/**
 *
 * @author aborbon
 */
public class OOSimpleClientFact {
    
    public static OOSimpleClient create(String name) {
        return new OOSimpleClient(name);
    }
    
    public static OOSimpleClient create(String id, String name) {
        return new OOSimpleClient(id, name);
    }
}
