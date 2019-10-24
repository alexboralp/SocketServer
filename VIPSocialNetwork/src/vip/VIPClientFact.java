/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vip;

import vip.objects.VIPSimpleClient;

/**
 *
 * @author alexander
 */
public class VIPClientFact {
    public static VIPSimpleClient createClient(String id, String name) {
        return new VIPSimpleClient(id, name);
    }
    
    public static VIPSimpleClient createClient(String name) {
        return new VIPSimpleClient("", name);
    }
}
