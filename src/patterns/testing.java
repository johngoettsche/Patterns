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
    Object myObject;
    
    public static void main(String[] args) {
        PatternVariableMap patternVariableMap = PatternVariableMap.getInstance();
        patternVariableMap.setPatternVariable("start", 5);

        Pattern testPat = new Pattern("Len(start)");
        System.out.println(testPat.match("abcdefghijklmnop"));
    }
}
