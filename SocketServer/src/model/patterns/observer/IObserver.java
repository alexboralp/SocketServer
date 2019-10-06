/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.patterns.observer;

/**
 * Interfaz que define los métodos que debe tener un Observer
 * @author aborbon
 */
public interface IObserver {
    /**
     * Método que se lanza cuando el observable sufre algún cambio que se le avisa a los observers.
     * @param message Mensaje que envía el observable al observer.
     */
    public void update(Object message);
}
