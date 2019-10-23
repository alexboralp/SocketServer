/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver.patterns.observer;

import java.util.Collection;

/**
 * Interfaz que define las funciones de un objeto observable
 * @author aborbon
 * @param <T>
 */
public interface SSIObservable<T extends SSIObserver> {
    /**
     * Agrega un nuevo observador del objeto
     * @param observer El nuevo observador.
     */
    public void addObserver(T observer);
    
    /**
     * Elimina un observador
     * @param observer El observador que se desea eliminar
     */
    public void removeObserver(T observer);
    
    /**
     * Le envía un mensaje a un observador en particular
     * @param observer El observador al que se le quiere enviar el mensaje
     * @param message Mensaje que se desea enviar
     */
    public void update(T observer, Object message);
    
    /**
     * Le envía un mensaje a todos los observadores del objeto
     * @param message Mensaje que se desea enviar.
     */
    public void updateAll(Object message);
    
    public Collection<T> getObservers();
}
