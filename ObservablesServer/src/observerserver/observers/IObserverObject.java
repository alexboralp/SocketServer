/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerserver.observers;

import observerserver.commoninterfaces.IObjectable;
import socketserver.commoninterfaces.IIdable;
import socketserver.patterns.observer.IObserver;

/**
 *
 * @author alexander
 */
public interface IObserverObject<T> extends IObserver, IIdable, IObjectable<T>{
    
}
