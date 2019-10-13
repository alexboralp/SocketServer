/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver.msg;

import java.io.Serializable;
import ssserver.commoninterfaces.SSITypeable;
import ssserver.commoninterfaces.SSIMessageable;
import ssserver.commoninterfaces.SSIIdable;

/**
 *
 * @author alexander
 * @param <T>
 */
public interface SSIMsg<T extends Serializable> extends SSIIdable, SSITypeable, SSIMessageable<T>, Serializable {
    
}
