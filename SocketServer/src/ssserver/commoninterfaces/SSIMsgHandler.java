/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver.commoninterfaces;

/**
 *
 * @author alexander
 * @param <T>
 */
public interface SSIMsgHandler<T> {
    public void handleMsg(T message);
}
