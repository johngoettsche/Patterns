/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringAnalysis;

/**
 *
 * @author John H. Goettsche
 */
public class CSets {
    public CSet lowerCase = new CSet("abcdefghijklmnopqrstuvwxyz");
    public CSet upperCase = new CSet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    public CSet letters = new CSet("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
    public CSet digits = new CSet("0123456789");
}
