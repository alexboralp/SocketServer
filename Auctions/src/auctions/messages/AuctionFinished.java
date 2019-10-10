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
public class AuctionFinished implements Serializable {
    private String idAuction;
    private String idWinnerBidder;
    private String messageToWinner;

    public AuctionFinished(String idAuction, String idWinnerBidder, String messageToWinner) {
        this.idAuction = idAuction;
        this.idWinnerBidder = idWinnerBidder;
        this.messageToWinner = messageToWinner;
    }

    public String getIdAuction() {
        return idAuction;
    }

    public void setIdAuction(String idAuction) {
        this.idAuction = idAuction;
    }

    public String getIdWinnerBidder() {
        return idWinnerBidder;
    }

    public void setIdWinnerBidder(String idWinnerBidder) {
        this.idWinnerBidder = idWinnerBidder;
    }

    public String getMessageToWinner() {
        return messageToWinner;
    }

    public void setMessageToWinner(String messageToWinner) {
        this.messageToWinner = messageToWinner;
    }
    
    
}
