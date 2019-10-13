/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooserver.commoninterfaces;

/**
 *
 * @author alexander
 * @param <T>
 */
public interface OOIObjectable<T> {
    public void setObject(T object);
    public T getObject();
}
