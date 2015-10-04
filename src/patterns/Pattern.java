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
        PatternElem nullElem = new PatternElemNull();
        List<PatternElem> def = new ArrayList<PatternElem>();
        int braceL, braceR;
        PatternLabel patternLabel;
        
        StringTokenizer stringTokenizer = new StringTokenizer(p);
        while (stringTokenizer.hasMoreTokens()) {
            // need to handle (exp exp) make recursive
            //need to check for existing user defined fuctions too
            
            String patternName = "";
            String arguments = "";
            PatternElem pat = nullElem;
            String token = stringTokenizer.nextToken();
            braceL = token.indexOf("(");
            if (braceL != -1) {
                patternName = token.substring(0, braceL);
                patternLabel = PatternLabel.valueOf(patternName);
                braceR = token.lastIndexOf(")");
                if(braceR != -1) arguments = token.substring((braceL + 1), braceR);
                else System.out.println("Pattern Fucntion needs a \")\"");
                switch(patternLabel){
                    case Len :
                        pat = new PatternFunctionLen(arguments);
                        break;
                    default :
                        System.out.println("Unknown Pattern Element.");
                }
            } else {
                patternName = token;
                if(token.startsWith("'")){
                    if(token.endsWith("'")){
                        String st = token.substring(1, (token.length() - 1));
                        pat = new PatternTypeString(st);
                    } else {
                        System.out.println("String Pattern Type must end with \"'\"");
                    }
                } else if(token.startsWith("`")){
                    if(token.endsWith("`")){
                        String st = token.substring(1, (token.length() - 1));
                        pat = new PatternTypeCSet(st);
                    } else {
                        System.out.println("C-Set Pattern Type must end with \"`\"");
                    }
                } else { //existing patterns or operators
                    if(token.contentEquals("|")){
                        pat = new PatternOperatorAlternate();
                    }
                        
                }
            }
            System.out.println("-----------------");
            System.out.println(pat.getElementName());
            System.out.println(pat.getArguments().size() + " args, Pattern");
            if(pat.getArguments().size() > 0){
                for(int i = 0; i < pat.getArguments().size(); i++){
                    System.out.println("\t" + pat.getArgument(i));
                }
            }
            System.out.println("=================");
        }
        return def;
    }
}
