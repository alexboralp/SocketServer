/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions.msgs;

import java.io.Serializable;

/**
 *
 * @author alexander
 */
public class AuctionsMsgNewOffer implements Serializable{
    private String idAuction;
    private String idBidder;
    private double newOffer;

    public AuctionsMsgNewOffer(String idAuction, String idBidder, double newOffer) {
        this.idAuction = idAuction;
        this.idBidder = idBidder;
        this.newOffer = newOffer;
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

    public double getNewOffer() {
        return newOffer;
    }

    public void setNewOffer(double newOffer) {
        this.newOffer = newOffer;
    }

    @Override
    public String toString() {
        return "AuctionMsgNewOffer{" + "idAuction=" + idAuction + ", newOffer=" + newOffer + '}';
    }
    
    
}
