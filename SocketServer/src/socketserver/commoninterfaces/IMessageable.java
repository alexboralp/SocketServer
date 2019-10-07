/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketserver.commoninterfaces;

import java.io.Serializable;

/**
 *
 * @author alexander
 * @param <T>
 */
public interface IMessageable<T extends Serializable> {
    public T getMessage();
    public void setMessage(T message);
}
