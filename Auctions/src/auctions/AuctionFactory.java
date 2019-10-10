/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions;

import auctions.objects.Auction;
import auctions.objects.Product;
import java.util.Date;
import javax.swing.Icon;

/**
 *
 * @author aborbon
 */
public class AuctionFactory {
    public static Auction createAuction(String id, Date startDate, int duration, Product product, double initialPrice, Icon image) {
        return new Auction(id, startDate, duration, product, initialPrice, image);
    }
}
