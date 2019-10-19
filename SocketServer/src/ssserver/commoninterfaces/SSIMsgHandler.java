/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver.commoninterfaces;

import ssserver.patterns.observer.SSIObserver;

/**
 *
 * @author alexander
 */
public interface SSIMsgHandler extends SSIObserver {
    public void handleMsg(Object message);
}
