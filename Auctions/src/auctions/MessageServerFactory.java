/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctions;

import observerserver.ObserverServerMessageFactory;

/**
 *
 * @author aborbon
 */
public class MessageServerFactory extends ObserverServerMessageFactory {
    public static final int NEW_OFFER = 201;
    public static final int MESSAGE_TO_BIDDER = TEXT_MESSAGE_TO_OBSERVER;
    public static final int SENDING_ALL_AUCTIONS = OBSERVABLES_LIST;
    public static final int SENDING_ALL_BIDDERS = OBSERVERS_LIST;
    public static final int SENDING_AUCTION = SENDING_OBSERVABLE;
    public static final int SENDING_BIDDER = SENDING_OBSERVER;
    public static final int MESSAGE_TO_AUCTIONEER = TEXT_MESSAGE_TO_OBSERVER;
    public static final int OFFER_ACCEPTED = 202;
}
