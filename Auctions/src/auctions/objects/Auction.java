/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions.objects;

import java.util.Date;
import javax.swing.Icon;
import ooserver.observermsg.OOAbsSendableObj;

/**
 *
 * @author alexander
 */
public class Auction extends OOAbsSendableObj {
    
    public enum STATE {
        IN_PROGRESS, CANCELED, TIME_FINISHED, FINISHED
    }
    
    private Date startDate;
    private int duration;
    private AuctionProduct product;
    private Icon image;
    
    private String auctioneerId;
    private double actualPrice;
    private String bidderId;
    private double nextPrice;
    private String newBidderId;
    
    private STATE state;
    
    public Auction() {
        super("");
        this.startDate = null;
        this.duration = 0;
        this.product = null;
        this.actualPrice = 0;
        
        this.auctioneerId = "";
        this.bidderId = "";
        this.newBidderId = "";
        state = STATE.IN_PROGRESS;
    }

    public Auction(String auctioneerId, String id, Date startDate, int duration, AuctionProduct product, double precioInicial, Icon image) {
        super(id);
        this.startDate = startDate;
        this.duration = duration;
        this.product = product;
        this.actualPrice = 0;
        this.nextPrice = precioInicial;
        this.image = image;
        
        this.auctioneerId = auctioneerId;
        
        this.bidderId = "";
        this.newBidderId = "";
        state = STATE.IN_PROGRESS;
    }
    
    public void acceptNewOffer() {
        if (!"".equals(this.newBidderId)) {
            setActualPrice(this.nextPrice);
            setBidderId(newBidderId);
            this.newBidderId = "";
        }
    }
    
    public void newOffer(String bidderId, double nextPrice) {
        setNextPrice(nextPrice);
        setNewBidderId(bidderId);
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

    public AuctionProduct getProduct() {
        return product;
    }

    public void setProduct(AuctionProduct product) {
        this.product = product;
    }

    public String getAuctioneerId() {
        return auctioneerId;
    }

    public void setAuctioneerId(String auctioneerId) {
        this.auctioneerId = auctioneerId;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getBidderId() {
        return bidderId;
    }

    public void setBidderId(String bidderId) {
        this.bidderId = bidderId;
    }

    public double getNextPrice() {
        return nextPrice;
    }

    public void setNextPrice(double nextPrice) {
        this.nextPrice = nextPrice;
    }

    public String getNewBidderId() {
        return newBidderId;
    }

    public void setNewBidderId(String newBidderId) {
        this.newBidderId = newBidderId;
    }

    public STATE getState() {
        return state;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    public Icon getImage() {
        return image;
    }

    public void setImage(Icon image) {
        this.image= image;
    }

    @Override
    public String toString() {
        return "Auction{" + "id=" + id + ", startDate=" + startDate + ", duration=" + duration + ", product=" + product + ", auctioneerId=" + auctioneerId + ", actualPrice=" + actualPrice + ", bidderId=" + bidderId + ", nextPrice=" + nextPrice + ", newBidderId=" + newBidderId + ", state=" + state + '}';
    }
    
}
