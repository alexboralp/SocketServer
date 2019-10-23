/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions;

import auctions.objects.AuctionSimpleClient;

/**
 *
 * @author alexander
 */
public class AuctionsClientFact {
    public static AuctionSimpleClient createClient(String id, String name) {
        return new AuctionSimpleClient(id, name);
    }
    
    public static AuctionSimpleClient createClient(String name) {
        return new AuctionSimpleClient("", name);
    }
}
