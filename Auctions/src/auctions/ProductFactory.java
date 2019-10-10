/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions;

import auctions.objects.Product;
import javax.swing.Icon;

/**
 *
 * @author aborbon
 */
public class ProductFactory {
    public static Product createProduct(String id, String description, Icon image, double inititalPrice) {
        return new Product(id, description, image, inititalPrice);
    }
}
