/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions.objects;

import javax.swing.Icon;
import ooserver.observermsg.OOAbsSendableObj;

/**
 *
 * @author alexander
 */
public class AuctionProduct extends OOAbsSendableObj {
    
    private String description;
    private Icon image;
    
    private double inititalPrice;
    private double finalPrice;
    
    public AuctionProduct() {
        super("");
        this.description = "";
        this.image = null;
        this.inititalPrice = 0;
        this.finalPrice = 0;
    }

    public AuctionProduct(String id, String description, Icon image, double inititalPrice) {
        super(id);
        this.description = description;
        this.image = image;
        this.inititalPrice = inititalPrice;
        this.finalPrice = inititalPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Icon getImage() {
        return image;
    }

    public void setImage(Icon image) {
        this.image = image;
    }

    public double getInititalPrice() {
        return inititalPrice;
    }

    public void setInititalPrice(double inititalPrice) {
        this.inititalPrice = inititalPrice;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    @Override
    public String toString() {
        return "AuctionProduct{" + "description=" + description + ", image=" + image + ", inititalPrice=" + inititalPrice + ", finalPrice=" + finalPrice + '}';
    }
    
}
