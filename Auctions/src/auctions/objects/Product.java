/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions.objects;

import java.io.Serializable;
import socketserver.commoninterfaces.IIdable;

/**
 *
 * @author alexander
 */
public class Product implements IIdable, Serializable {
    
    private String id;
    
    private String description;
    private String imagePath;
    
    private double inititalPrice;
    private double finalPrice;
    
    public Product() {
        this.id = "";
        this.description = "";
        this.imagePath = "";
        this.inititalPrice = 0;
        this.finalPrice = 0;
    }

    public Product(String id, String description, String imagePath, double inititalPrice) {
        this.id = id;
        this.description = description;
        this.imagePath = imagePath;
        this.inititalPrice = inititalPrice;
        this.finalPrice = inititalPrice;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    
    
    
}
