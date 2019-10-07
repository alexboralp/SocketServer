/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketserver.commoninterfaces;

/**
 *
 * @author alexander
 * @param <T>
 */
public interface IList<T> {
    public void add(T element);
    public void remove(T element);
}
