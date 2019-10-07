/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.socketserver.commoninterfaces;

import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author alexander
 * @param <T>
 */
public interface ISendMessageable<T extends Serializable> {
    public void sendMessage(T message) throws IOException;
}
