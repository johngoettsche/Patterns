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

public abstract class PatternElem extends PatternGlobals {
    private PatternType element;
    private String elementName;
    private List<PatternElem> arguments;    
    private String charSet;
    private int oldPos;
    private String subString;
    private MatchResult result;

    private CSets csets = new CSets();
    private PatternGlobals patternGlobals = new PatternGlobals();
    
    public abstract MatchResult evaluate(String subject, int pos);
    
/*    public PatternElem(){
        
    }*/
    
    public Class getPatternType(){
        return element.getClass();
    }

    public void setResult(MatchResult result) {
        this.result = result;
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

    public MatchResult getResult() {
        return result;
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
    
    public List<PatternElem> defineFuncArguments(String args) throws PatternException{
        PatternElem nullElem = new PatternElemNull();
        List<PatternElem> funcArgs = new ArrayList();
        PatternElem pat = new PatternElemNull();
        int stop, endLet;
        String argument;
        int braceL, braceR;
        int strL, strR;
        int operator;
        PatternLabel patternLabel;
        int comma = args.length();
        int pos = 0;
        while(args.charAt(pos) == ' ' && pos < args.length()) pos++;
        while(comma >= 0){
            comma = args.indexOf(",", pos);
            if(comma > 0) {
                stop = comma;
            } else {
                stop = args.length();
            }
            System.out.println(args + ", " + pos + ", " + stop);
            argument = args.substring(pos, stop);
            braceL = args.indexOf("(", pos);
            if(braceL < stop && braceL >= 0) {
                braceR = findClosingSymbol(args, "(", pos);
            }
            operator = findOperator(args);
            if(args.charAt(pos) == '\'') {
                strL = pos;
                strR = findClosingSymbol(args, "'", pos);
                if(strR > 0) {
                    pat = new PatternTypeString(args.substring(strL + 1, strR));
                } else {
                    throw new PatternException("Pattern String needs a closing \"'\"");
                }
            } else if(args.charAt(pos) == '('){
                System.out.println("Sub Pattern");
                braceR = findClosingSymbol(args, "(", braceL);
                if(braceR > 0) {
                    String subArgs = args.substring(braceL + 1, braceR);
                    pat = new PatternElemPattern(subArgs);
                    pos = braceR + 1;
                } else {
                    throw new PatternException("Pattern bracket needs a closing \")\"");
                }
            } else if(operator < comma) {
                pat = new PatternElemPattern(args);
            } else if(memberOfCSet(args.charAt(pos), csets.letters)) {
                if(braceL > pos) {
                    argument = args.substring(pos, braceL);
                    braceR = findClosingSymbol(args, "(", braceL);
                    if(braceR > pos) {
                        pat = makePatternFunction(argument, args, braceL, braceR);
                        if(pat.getClass().equals(PatternElemNull.class)) {
                            throw new PatternException("Unrecognized Pattern Function");
                        } 
                    } else {
                        throw new PatternException("Pattern bracket needs a closing \")\"");
                    }
                } else {
                    System.out.println("Variable");
                    endLet = endLetters(args, pos);
                    argument = args.substring(pos, endLet);
                    pat = new PatternElemVariable(argument);
                }
            }
            pos = stop;
            funcArgs.add(pat);
        }
        
        return funcArgs;
    }
    
    private PatternElem makePatternFunction(String token, String pattern, int braceL, int braceR) 
            throws PatternException{
        
        PatternElem pat;
        PatternLabel patternLabel;
        String args;
        
        patternLabel = PatternLabel.valueOf(token);
        args = pattern.substring(braceL + 1, braceR);
        switch(patternLabel){
            case Abort :
                pat = new PatternStructureAbort(args);
                break;
            case Any :
                pat = new PatternFunctionAny(args);
                break;
            /*case Arb :
                pat = new PatternStructureArb(definition, args);
                break;
            case Arbno :
                pat = new PatternStructureArbno(definition, args);
                break;*/
            case Break :
                pat = new PatternFunctionBreak(args);
                break;
            case Fail :
                pat = new PatternFunctionFail(args);
                break;
            case Len :
                pat = new PatternFunctionLen(args);
                break;
            case NotAny :
                pat = new PatternFunctionNotAny(args);
                break;
            case Rem :
                pat = new PatternFunctionRem(args);
                break;
            case RTab :
                pat = new PatternFunctionRTab(args);
                break;
            case Span :
                pat = new PatternFunctionSpan(args);
                break;
            case Success :
                pat = new PatternFunctionSucceed(args);
                break;
            case Tab :
                pat = new PatternFunctionTab(args);
                break;
            default :
                pat = new PatternElemNull();
        } 
        return pat;
    }
}
