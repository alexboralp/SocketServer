/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ooserver.commoninterfaces;

import ssserver.commoninterfaces.SSIList;

/**
 *
 * @author alexander
 */
public interface OOIListId<T> extends SSIList<T> {
    public void remove(String id);
    public boolean containsKey(String id);
}
