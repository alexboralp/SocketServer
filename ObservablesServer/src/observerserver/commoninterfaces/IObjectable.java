/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerserver.commoninterfaces;

/**
 *
 * @author alexander
 * @param <T>
 */
public interface IObjectable<T> {
    public void setObject(T object);
    public T getObject();
}
