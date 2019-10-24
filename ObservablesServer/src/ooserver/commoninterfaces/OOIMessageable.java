/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooserver.commoninterfaces;

import java.io.Serializable;
import ssserver.commoninterfaces.SSIMessageable;

/**
 *
 * @author alexander
 * @param <T>
 */
public interface OOIMessageable<T extends Serializable> extends SSIMessageable<T> {
    
}
