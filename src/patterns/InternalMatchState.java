/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patterns;

/**
 *
 * @author John H. Goettsche
 */
public interface InternalMatchState {
    public void hasElements() throws PatternException;
    public void noElements() throws PatternException;
}
