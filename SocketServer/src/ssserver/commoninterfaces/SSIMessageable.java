/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver.commoninterfaces;

import java.io.Serializable;

/**
 *
 * @author alexander
 * @param <T>
 */
public interface SSIMessageable<T extends Serializable> {
    public T getMessage();
    public void setMessage(T message);
}
