/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerserver;

import socketserver.MessageFactory;

/**
 *
 * @author alexander
 */
public class ObserverMessageFactory extends MessageFactory {
    
    public static final int OBSERVABLES_LIST = 100;
    public static final int OBSERVERS_LIST = 101;
    public static final int TEXT_MESSAGE = 102;
    public static final int SENDING_OBSERVABLE = 103;
    public static final int SENDING_OBSERVER = 104;
    public static final int TEXT_MESSAGE_TO_OBSERVER = 105;
    public static final int DONE = 106;
}
