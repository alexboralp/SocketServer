/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions.msgs;

/**
 *
 * @author aborbon
 */
public class AuctionsAtributeChangedFact {
     public static AuctionsAtributeChanged createAuctionsAtributeChanged(String name, Object object) {
         return new AuctionsAtributeChanged(name, object);
     }
}
