/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/**
 *
 * @author John H. Goettsche
 */

public class Pattern {
    List<PatternElem> definition = new ArrayList();
    
    public Pattern(){
        
    }
    
    public Pattern(String p){
        definition = patternDefinition(p);
    }
    
    public void define(String p){
        definition = patternDefinition(p);
    }
    
    private List<PatternElem> patternDefinition(String p){
        List<PatternElem> def = new ArrayList<PatternElem>();
        String token, arguments;
        int braceL, braceR;
        PatternLabel patternLabel;
        String patternName;
        
        StringTokenizer stringTokenizer = new StringTokenizer(p);
        while (stringTokenizer.hasMoreTokens()) {
            patternName = "";
            arguments = "";
            token = stringTokenizer.nextToken();
            braceL = token.indexOf("(");
            if (braceL != -1) {
                patternName = token.substring(0, braceL);
                patternLabel = PatternLabel.valueOf(patternName);
                System.out.println("Label: " + patternLabel);
                braceR = token.indexOf(")");
                if(braceR != -1) arguments = token.substring((braceL + 1), braceR);
                else System.out.println("Pattern Fucntion needs a \")\"");
            } else {
                patternName = token;
            }
            System.out.println(patternName);
            System.out.println(arguments);
        }
        return def;
    }
}
