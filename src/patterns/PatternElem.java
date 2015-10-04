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

public abstract class PatternElem {
    private PatternType element;
    private String elementName;
    private List<PatternElem> arguments;    
    private String charSet;
    
    public abstract MatchResult evaluate(String subject, int pos);
    
    public PatternElem(){
        
    }
    
    public Class getPatternType(){
        return element.getClass();
    }

    public void setElement(PatternType element) {
        this.element = element;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public void setArguments(List arguments) {
        this.arguments = arguments;
    }

    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }
    
    public PatternType getElement() {
        return element;
    }

    public String getElementName() {
        return elementName;
    }

    public String getCharSet() {
        return charSet;
    }
    
    public List getArguments() {
        return arguments;
    }
    
    public PatternElem getArgument(int i){
        return arguments.get(i);  
    }
    
    public List<PatternElem> defineFuncArguments(String args){
        PatternElem nullElem = new PatternElemNull();
        List<PatternElem> funcArgs = new ArrayList();
        int braceL, braceR;
        PatternLabel patternLabel;
        
        StringTokenizer stringTokenizer = new StringTokenizer(args);
        while (stringTokenizer.hasMoreTokens()) {
            // need to handle (exp exp) make recursive
            
            String argumentName = "";
            String internalArgument = "";
            
            String token = stringTokenizer.nextToken();
            braceL = token.indexOf("(");
            if (braceL != -1) {//is a fuction
                PatternElem pat = nullElem;
                argumentName = token.substring(0, braceL);
                System.out.println("\t- " + argumentName);
                //check for user defined function or enum
                patternLabel = PatternLabel.valueOf(argumentName);
                braceR = token.lastIndexOf(")");
                if(braceR != -1) internalArgument = token.substring((braceL + 1), braceR);
                //need to check for existing user defined fuctions too
                else System.out.println("Pattern Fucntion needs a \")\"");
                switch(patternLabel){
                    case Len :
                        pat = new PatternFunctionLen(internalArgument);
                        break;
                    default :
                        System.out.println("Unknown Pattern Element.");
                }
                funcArgs.add(pat);
            } else { // not a function
                argumentName = token;

                if(isInteger(argumentName)) { //integer  Need to add String and CSet
                    PatternElem integerArgument = new PatternTypeInteger(argumentName);
                    funcArgs.add(integerArgument);
                } else {//existing patterns or operators
                    System.out.println("token: " + argumentName);
                    funcArgs.add(nullElem);
                }
            }
        }
        return funcArgs;
    }
    
    private boolean isInteger(String st){
            if(st.matches("[0-9]")){
                return true;
            } else {
                return false;
            }
    }
}
