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
public class testing {

    /**
     * @param args the command line arguments
     */
    Object myObject;
    
    public static void main(String[] args) {
        CSets csets = new CSets();
        StringScan ss = new StringScan("abcdefghi123jklmnopqrstuvwxyzABC");
        CSet vowels = new CSet("aeiou");
        ss.Pos(3);
        System.out.println(ss.Tab(ss.Many(csets.lowerCase)));
    }
}
