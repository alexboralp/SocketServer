/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver.commoninterfaces;

import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author alexander
 * @param <T>
 */
public interface SSISendMessageable<T extends Serializable> {
    public void sendMessage(T message) throws IOException;
}
