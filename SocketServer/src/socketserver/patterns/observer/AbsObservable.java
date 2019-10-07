/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketserver.patterns.observer;

import java.util.LinkedList;

/**
 * Clase abstracta que implementa los métodos básicos del IObservable
 * @author aborbon
 */
public abstract class AbsObservable implements IObservable{
    // Lista de observers
    private final LinkedList<IObserver> observers;

    /**
     * Constructor vacío que inicia la lista de observers.
     */
    public AbsObservable() {
        observers = new LinkedList<>();
    }

    @Override
    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void update(IObserver observer, Object message){
        observer.update(message);
    }

    @Override
    public void updateAll(Object message) {
        for (IObserver observer : observers) {
            observer.update(message);
        }
    }
    
}
