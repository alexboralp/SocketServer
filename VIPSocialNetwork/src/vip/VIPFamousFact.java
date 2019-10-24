/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vip;

import vip.objects.VIPFamous;
import vip.objects.VIPFamousMsg;

/**
 *
 * @author aborbon
 */
public class VIPFamousFact {
    public static VIPFamous createFamous(String id, String name) {
        return new VIPFamous(id, name);
    }
    
    public static VIPFamousMsg createFamousMsg(String id, String message, String ownerId) {
        return new VIPFamousMsg(id, message, ownerId);
    }
}
