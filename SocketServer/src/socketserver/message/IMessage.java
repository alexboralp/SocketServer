/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketserver.message;

import java.io.Serializable;
import socketserver.commoninterfaces.IIdable;
import socketserver.commoninterfaces.IMessageable;
import socketserver.commoninterfaces.ITypeable;

/**
 *
 * @author alexander
 * @param <T>
 */
public interface IMessage<T extends Serializable> extends IIdable, ITypeable, IMessageable<T>, Serializable {
    
}
