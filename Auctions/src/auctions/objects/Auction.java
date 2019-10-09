/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions.objects;

import java.io.Serializable;
import java.util.Date;
import socketserver.commoninterfaces.IIdable;

/**
 *
 * @author alexander
 */
public class Auction implements IIdable, Serializable {
    
    private String id;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
    
    public enum STATE {
        IN_PROGRESS, CANCELED, FINISHED
    }
    
    private Date startDate;
    private int duration;
    private Product product;
    
    private Auctioneer auctioneer;
    private double actualPrice;
    private Bidder bidder;
    private double nextPrice;
    private Bidder newBidder;
    
    private STATE state;
    
    public Auction() {
        this.id = "";
        this.startDate = null;
        this.duration = 0;
        this.product = null;
        this.actualPrice = 0;
        
        this.auctioneer = null;
        this.bidder = null;
        this.newBidder = null;
        state = STATE.IN_PROGRESS;
    }

    public Auction(String id, Date startDate, int duration, Product product, double precioInicial) {
        this.id = id;
        this.startDate = startDate;
        this.duration = duration;
        this.product = product;
        this.actualPrice = 0;
        this.nextPrice = precioInicial;
        
        this.auctioneer = null;
        this.bidder = null;
        this.newBidder = null;
        state = STATE.IN_PROGRESS;
    }
    
    public void acceptNewOffer() {
        if (this.newBidder != null) {
            setActualPrice(this.nextPrice);
            setBidder(newBidder);
            this.newBidder = null;
        }
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Auctioneer getAuctioneer() {
        return auctioneer;
    }

    public void setAuctioneer(Auctioneer auctioneer) {
        this.auctioneer = auctioneer;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Bidder getBidder() {
        return bidder;
    }

    public void setBidder(Bidder bidder) {
        this.bidder = bidder;
    }

    public double getNextPrice() {
        return nextPrice;
    }

    public void setNextPrice(double nextPrice) {
        this.nextPrice = nextPrice;
    }

    public Bidder getNewBidder() {
        return newBidder;
    }

    public void setNewBidder(Bidder newBidder) {
        this.newBidder = newBidder;
    }

    public STATE getState() {
        return state;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Auction{" + "id=" + id + ", startDate=" + startDate + ", duration=" + duration + ", product=" + product + ", auctioneer=" + auctioneer + ", actualPrice=" + actualPrice + ", bidder=" + bidder + ", nextPrice=" + nextPrice + ", newBidder=" + newBidder + ", state=" + state + '}';
    }
    
}
