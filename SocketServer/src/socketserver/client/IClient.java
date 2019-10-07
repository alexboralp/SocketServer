/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketserver.client;

import java.io.Serializable;
import socketserver.commoninterfaces.IIdable;
import socketserver.commoninterfaces.IInputOutputStreamable;
import socketserver.commoninterfaces.IOk;
import socketserver.commoninterfaces.ISendMessageable;
import socketserver.commoninterfaces.ISocketable;
import socketserver.commoninterfaces.ITerminable;

/**
 *
 * @author alexander
 * @param <T>
 */
public interface IClient<T extends Serializable> extends ISendMessageable<T>, IIdable, ISocketable, IInputOutputStreamable, IOk, ITerminable {
    
}
