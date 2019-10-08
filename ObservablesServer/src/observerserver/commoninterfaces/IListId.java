/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerserver.commoninterfaces;

import socketserver.commoninterfaces.IList;

/**
 *
 * @author alexander
 */
public interface IListId<T> extends IList<T> {
    public void remove(String id);
    public boolean containsKey(String id);
}
