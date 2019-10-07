/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketserver.patterns.observer;

/**
 * Interfaz que define las funciones de un objeto observable
 * @author aborbon
 */
public interface IObservable {
    /**
     * Agrega un nuevo observador del objeto
     * @param observer El nuevo observador.
     */
    public void addObserver(IObserver observer);
    
    /**
     * Elimina un observador
     * @param observer El observador que se desea eliminar
     */
    public void removeObserver(IObserver observer);
    
    /**
     * Le envía un mensaje a un observador en particular
     * @param observer El observador al que se le quiere enviar el mensaje
     * @param message Mensaje que se desea enviar
     */
    public void update(IObserver observer, Object message);
    
    /**
     * Le envía un mensaje a todos los observadores del objeto
     * @param message Mensaje que se desea enviar.
     */
    public void updateAll(Object message);
}
