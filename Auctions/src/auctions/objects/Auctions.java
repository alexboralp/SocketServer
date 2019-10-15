/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions.objects;

import java.util.HashMap;
import ooserver.commoninterfaces.OOIListId;


/**
 *
 * @author alexander
 */
public class Auctions implements OOIListId<Auction>{
    HashMap<String, Auction> auctions;

    public Auctions() {
        auctions = new HashMap();
    }

    public Auctions(HashMap<String, Auction> auctions) {
        this.auctions = auctions;
    }

    public HashMap<String, Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(HashMap<String, Auction> auctions) {
        this.auctions = auctions;
    }
    
    @Override
    public void add (Auction auction) {
        auctions.put(auction.getId(), auction);
    }
    
    @Override
    public void remove (Auction auction) {
        auctions.remove(auction.getId());
    }
    
    @Override
    public Auction get (String id) {
        return auctions.get(id);
    }
    
    @Override
    public boolean containsKey(String id) {
        return auctions.containsKey(id);
    }

    @Override
    public void remove(String id) {
        auctions.remove(id);
    }

    @Override
    public String toString() {
        return "Auctions{" + "auctions=" + auctions + '}';
    }
    
    
}
