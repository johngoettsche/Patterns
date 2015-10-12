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
    private int oldPos;
    private String subString;
    
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

    public void setOldPos(int oldPos) {
        this.oldPos = oldPos;
    }

    public void setSubString(String subString) {
        this.subString = subString;
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

    public int getOldPos() {
        return oldPos;
    }

    public String getSubString() {
        return subString;
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
                //check for user defined function
                patternLabel = PatternLabel.valueOf(argumentName);
                braceR = token.lastIndexOf(")");
                if(braceR != -1) internalArgument = token.substring((braceL + 1), braceR);
                //need to check for existing user defined fuctions too
                else System.out.println("Pattern Fucntion needs a \")\"");
                if(argumentName.isEmpty()) {
                    pat = new PatternElemPattern(internalArgument);
                } else {
                    switch(patternLabel){
                        //will copy all the cases later...
                        case Abort :
                        pat = new PatternStructureAbort(internalArgument);
                        break;
                    case Any :
                        pat = new PatternFunctionAny(internalArgument);
                        break;
                    /*case Arb :
                        pat = new PatternStructureArb(definition, internalArgument);
                        break;
                    case Arbno :
                        pat = new PatternStructureArbno(definition, internalArgument);
                        break;*/
                    case Break :
                        pat = new PatternFunctionBreak(internalArgument);
                        break;
                    case Fail :
                        pat = new PatternFunctionFail(internalArgument);
                        break;
                    case Len :
                        pat = new PatternFunctionLen(internalArgument);
                        break;
                    case NotAny :
                        pat = new PatternFunctionNotAny(internalArgument);
                        break;
                    case Rem :
                        pat = new PatternFunctionRem(internalArgument);
                        break;
                    case RTab :
                        pat = new PatternFunctionRTab(internalArgument);
                        break;
                    case Span :
                        pat = new PatternFunctionSpan(internalArgument);
                        break;
                    case Success :
                        pat = new PatternFunctionSucceed(internalArgument);
                        break;
                    case Tab :
                        pat = new PatternFunctionTab(internalArgument);
                        break;
                    default :
                        System.out.println("Unknown Pattern Element.");
                        break;
                    }
                }
                funcArgs.add(pat);
            } else { // not a function
                argumentName = token;

                if(isInteger(argumentName)) { //integer argument
                    PatternElem integerArgument = new PatternTypeInteger(argumentName);
                    funcArgs.add(integerArgument);
                } else if(argumentName.startsWith("'")) { //string argument
                    if(argumentName.endsWith("'")){
                        String st = token.substring(1, (token.length() - 1));
                        PatternElem stringArgument = new PatternTypeString(st);
                        funcArgs.add(stringArgument);
                    } else {
                        System.out.println("String Pattern Type must end with \"'\"");
                    }
                } else if(argumentName.startsWith("`")) { //C-Set argument
                    if(argumentName.endsWith("`")){
                        String st = token.substring(1, (token.length() - 1));
                        PatternElem csetArgument = new PatternTypeCSet(st);
                        funcArgs.add(csetArgument);
                    } else {
                        System.out.println("String Pattern Type must end with \"'\"");
                    }
                } else {//existing patterns or operators
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
    
    public int beginCSet(String subject, int pos, String cset){
        for(int i = 0; i < cset.length(); i++) {
            if(subject.charAt(pos) == cset.charAt(i)) return pos;
        }
        pos++;
        return -1;
    }
    
    public boolean atCSet(String subject, int pos, String cset){
        for(int i = pos; i < cset.length(); i++) {
            if(subject.charAt(pos) == cset.charAt(i)) return true;
        }
        return false;
    }
    
    public int endCSet(String subject, int pos, String cset){
        int p = pos;
        while(p < subject.length()) {
            boolean success = false;
            for(int i = 0; i < cset.length(); i++) {
                if(subject.charAt(p) == cset.charAt(i)) success = true;
            }
            if(!success)return p;
            p++;
        }
        return subject.length() + 1;
    }
}
