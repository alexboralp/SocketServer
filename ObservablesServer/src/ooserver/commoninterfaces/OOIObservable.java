/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooserver.commoninterfaces;

import ssserver.patterns.observer.SSIObservable;

/**
 *
 * @author alexander
 * @param <T>
 */
public interface OOIObservable<T extends OOIObserver> extends SSIObservable<T> {
    
}
