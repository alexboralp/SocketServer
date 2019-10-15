/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions;

import auctions.objects.AuctionProduct;
import javax.swing.Icon;

/**
 *
 * @author aborbon
 */
public class AuctionsProductFact {
    public static AuctionProduct createProduct(String id, String description, Icon image, double inititalPrice) {
        return new AuctionProduct(id, description, image, inititalPrice);
    }
}
