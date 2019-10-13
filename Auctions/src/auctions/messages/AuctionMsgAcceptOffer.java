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
public class AuctionMsgAcceptOffer implements Serializable {
    String idAuction;
    String idBidder;
    double newPrice;

    public AuctionMsgAcceptOffer(String idAuction, String idBidder, double newPrice) {
        this.idAuction = idAuction;
        this.idBidder = idBidder;
        this.newPrice = newPrice;
    }

    public String getIdAuction() {
        return idAuction;
    }

    public void setIdAuction(String idAuction) {
        this.idAuction = idAuction;
    }

    public String getIdBidder() {
        return idBidder;
    }

    public void setIdBidder(String idBidder) {
        this.idBidder = idBidder;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }
    
    
}
