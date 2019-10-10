/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions.messages;

import java.io.Serializable;

/**
 *
 * @author alexander
 */
public class NewOffer implements Serializable{
    private String idAuction;
    private double newOffer;

    public NewOffer(String idAuction, double newOffer) {
        this.idAuction = idAuction;
        this.newOffer = newOffer;
    }

    public String getIdAuction() {
        return idAuction;
    }

    public void setIdAuction(String idAuction) {
        this.idAuction = idAuction;
    }

    public double getNewOffer() {
        return newOffer;
    }

    public void setNewOffer(double newOffer) {
        this.newOffer = newOffer;
    }
    
    
}
