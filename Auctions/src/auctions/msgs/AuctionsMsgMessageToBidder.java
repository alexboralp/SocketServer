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
public class AuctionsMsgMessageToBidder implements Serializable{
    private String idBidder;
    private String Message;
    private String idAuction;

    public AuctionsMsgMessageToBidder(String idAuction, String idBidder, String Message) {
        this.idAuction = idAuction;
        this.idBidder = idBidder;
        this.Message = Message;
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

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    @Override
    public String toString() {
        return "AuctionMsgMessageToBidder{" + "idBidder=" + idBidder + ", Message=" + Message + ", idAuction=" + idAuction + '}';
    }
    
    
}
