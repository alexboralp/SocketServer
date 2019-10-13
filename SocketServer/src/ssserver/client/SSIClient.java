/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssserver.client;

import java.io.Serializable;
import ssserver.commoninterfaces.SSITerminable;
import ssserver.commoninterfaces.SSISocketable;
import ssserver.commoninterfaces.SSISendMessageable;
import ssserver.commoninterfaces.SSIOkable;
import ssserver.commoninterfaces.SSIInputOutputStreamable;
import ssserver.commoninterfaces.SSIIdable;

/**
 *
 * @author alexander
 * @param <T>
 */
public interface SSIClient<T extends Serializable> extends SSISendMessageable<T>, SSIIdable, SSISocketable, SSIInputOutputStreamable, SSIOkable, SSITerminable {
    
}
