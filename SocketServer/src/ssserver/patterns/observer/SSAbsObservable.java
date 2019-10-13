/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver.patterns.observer;

import java.util.LinkedList;

/**
 * Clase abstracta que implementa los métodos básicos del IObservable
 * @author aborbon
 */
public abstract class SSAbsObservable implements SSIObservable<SSIObserver>{
    // Lista de observers
    protected LinkedList<SSIObserver> observers;

    /**
     * Constructor vacío que inicia la lista de observers.
     */
    public SSAbsObservable() {
        observers = new LinkedList<>();
    }

    @Override
    public void addObserver(SSIObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(SSIObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void update(SSIObserver observer, Object message){
        observer.update(message);
    }

    @Override
    public void updateAll(Object message) {
        for (SSIObserver observer : observers) {
            observer.update(message);
        }
    }
    
}
