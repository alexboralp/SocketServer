/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vip.msgs;

/**
 *
 * @author aborbon
 */
public class VIPAtributeChangedFact {
    public static final int NEW_FAMOUS = 1;
    public static final int NEW_MESSAGE = 2;
    public static final int FAMOUS_FOLLOWERS = 3;
    public static final int MESSAGE_LIKE = 4;
    public static final int MESSAGE_DISLIKE = 5;
    public static final int FOLLOW_FAMOUS = 6;
    public static final int UNFOLLOW_FAMOUS = 7;
    public static final int DELETE_ALL_FAMOUS = 8;
    public static final int SHOW_MESSAGE = 9;
    
    
    public static VIPAtributeChanged createAuctionsAtributeChanged(int type, Object object) {
        return new VIPAtributeChanged(type, object);
    }
}
