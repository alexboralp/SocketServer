/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerclient;

import socketclient.ClientMessageFactory;

/**
 *
 * @author alexander
 */
public class ObserverClientMessageFactory extends ClientMessageFactory {
    
    public static final int ADD_OBSERVABLE = -100;
    public static final int REMOVE_OBSERVABLE = -101;
    public static final int FOLLOW_OBSERVABLE = -102;
    public static final int UNFOLLOW_OBSERVABLE = -103;
    public static final int REMOVE_ME_FROM_OBSERVERS = -104;
    public static final int ADD_OBSERVER = -105;
    public static final int SEND_ALL_OBSERVERS = -106;
    public static final int SEND_ALL_OBSERVABLES = -107;
    public static final int SEND_MY_ID = -108;
}
