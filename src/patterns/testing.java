/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package patterns;

/**
 *
 * @author John 
 */
public class testing {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Pattern testPat = new Pattern("Len(5)");
        System.out.println(testPat.match("abcdefg"));
    }
}
