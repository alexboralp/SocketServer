/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vip.objects;

import java.util.Collection;
import java.util.HashMap;
import ooserver.commoninterfaces.OOIListId;


/**
 *
 * @author alexander
 */
public class VIPFamousList implements OOIListId<VIPFamous>{
    HashMap<String, VIPFamous> famous;

    public VIPFamousList() {
        famous = new HashMap();
    }

    public VIPFamousList(HashMap<String, VIPFamous> famous) {
        this.famous = famous;
    }

    public HashMap<String, VIPFamous> getAuctions() {
        return famous;
    }

    public void setFamous(HashMap<String, VIPFamous> famous) {
        this.famous = famous;
    }
    
    @Override
    public void add (VIPFamous famous) {
        this.famous.put(famous.getId(), famous);
    }
    
    @Override
    public void remove (VIPFamous famous) {
        this.famous.remove(famous.getId());
    }
    
    @Override
    public VIPFamous get (String id) {
        return famous.get(id);
    }
    
    @Override
    public boolean containsKey(String id) {
        return famous.containsKey(id);
    }

    @Override
    public void remove(String id) {
        famous.remove(id);
    }

    @Override
    public Collection<VIPFamous> getValues() {
        return famous.values();
    }

    @Override
    public String toString() {
        return "Auctions{" + "famous=" + famous + '}';
    }
    
    
}
