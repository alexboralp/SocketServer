/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions;

import auctions.objects.AuctionClient;

/**
 *
 * @author alexander
 */
public class AuctionsClientFact {
    public static AuctionClient createClient(String id, String name) {
        return new AuctionClient(id, name);
    }
    
    public static AuctionClient createClient(String name) {
        return new AuctionClient("", name);
    }
}
