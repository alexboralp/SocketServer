/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerserver.observables;

import observerserver.commoninterfaces.IObjectable;
import socketserver.commoninterfaces.IIdable;
import socketserver.patterns.observer.IObservable;

/**
 *
 * @author alexander
 * @param <T>
 */
public interface IObservableObject<T> extends IObservable, IIdable, IObjectable<T> {
    public void removeObserver(String id);
}
