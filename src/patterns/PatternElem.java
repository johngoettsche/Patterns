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
    private List arguments;    
    private String charSet;
    
    public abstract MatchResult evaluate();
    
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
    
    public String getArgument(int i){
        return arguments.get(i).toString();  
    }
    
    public List<PatternElem> defineFuncArguments(String args){
        PatternElem nullElem = new PatternElemNull();
        List funcArgs = new ArrayList();
        int braceL, braceR;
        PatternLabel patternLabel;
        
        StringTokenizer stringTokenizer = new StringTokenizer(args);
        while (stringTokenizer.hasMoreTokens()) {
            // need to handle (exp exp) make recursive
            
            String argumentName = "";
            String argument = "";
            
            String token = stringTokenizer.nextToken();
            braceL = token.indexOf("(");
            if (braceL != -1) {//is a fuction
                PatternElem pat = nullElem;
                argumentName = token.substring(0, braceL);
                //check for user defined function or enum
                //if()
                patternLabel = PatternLabel.valueOf(argumentName);
                braceR = token.lastIndexOf(")");
                if(braceR != -1) argument = token.substring((braceL + 1), braceR);
                //need to check for existing user defined fuctions too
                
                else System.out.println("Pattern Fucntion needs a \")\"");
                switch(patternLabel){
                    case Len :
                        pat = new PatternFunctionLen(argument);
                        break;
                    default :
                        System.out.println("Unknown Pattern Element.");
                }
                funcArgs.add(pat);
            } else { // not a function
                argumentName = token;
                
                
                /*if(token.startsWith("'")){ // is a String
                    if(token.endsWith("'")){
                        String st = token.substring(1, (token.length() - 1));
                        System.out.println(" - " + st);
                        arguments.add(st);
                    } else {
                        System.out.println("String Pattern Type must end with \"'\"");
                    }
                } else if(token.startsWith("`")){ //is a C-Set
                    if(token.endsWith("`")){
                        String st = token.substring(1, (token.length() - 1));
                        System.out.println(" - " + st);
                        arguments.add(st);
                    } else {
                        System.out.println("C-Set Pattern Type must end with \"`\"");
                    }
                } else */ 
                if(isInteger(token)) { //integer
                    argumentName = token;
                    int value = Integer.parseInt(token);
                    funcArgs.add(value);
                } else {//existing patterns or operators
                    System.out.println("token: " + token);
                    funcArgs.add(token);
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
    
    private boolean isPatternFunction(String st){
        
        return false;
    }
}
