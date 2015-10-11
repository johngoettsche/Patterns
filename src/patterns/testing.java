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
public class testing {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //PatternMatch patternMatch = new PatternMatch();
        Pattern testPat = new Pattern("'cde' + 'f'");
        System.out.println(testPat.match("abcdefg"));
    }
}
