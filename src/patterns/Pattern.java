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
                    case Any :
                        pat = new PatternFunctionAny(arguments);
                        break;
                    case Break :
                        pat = new PatternFunctionBreak(arguments);
                        break;
                    case Len :
                        pat = new PatternFunctionLen(arguments);
                        break;
                    case NotAny :
                        pat = new PatternFunctionNotAny(arguments);
                        break;
                    case Rem :
                        pat = new PatternFunctionRem(arguments);
                        break;
                    case RTab :
                        pat = new PatternFunctionRTab(arguments);
                        break;
                    case Span :
                        pat = new PatternFunctionSpan(arguments);
                        break;
                    case Tab :
                        pat = new PatternFunctionTab(arguments);
                        break;
                    default :
                        System.out.println("Unknown Pattern Element.");
                        break;
                }
            } else {
                patternName = token;
                if(token.startsWith("'")){
                    if(token.endsWith("'")){
                        String st = token.substring(1, (token.length() - 1));
                        pat = new PatternElemString(st);
                    } else {
                        System.out.println("String Pattern Type must end with \"'\"");
                    }
                } else { //existing patterns or operators
                    if(token.contentEquals("|")){
                        pat = new PatternOperatorAlternate();
                    }  
                }
            }
            printPatternElements(pat);
            def.add(pat);
        }
        PatternElem endElem = new PatternElemEnd();
        def.add(endElem);
        return def;
    }
    
    public String match(String subject){
        int pos = 0;
        int oldPos = 0;
        String matchString = "";
        MatchResult matchResult = new MatchResult(pos, matchString);
        matchResult.setSuccess(false);
        MatchResult internalResult = new MatchResult(pos, matchString);
        internalResult.setSuccess(false);
        int patEl = 0;
        while (!matchResult.isSuccess() && !definition.get(patEl).getClass().equals(PatternElemEnd.class)){
            oldPos = pos;
            while(!internalResult.isSuccess() && pos <= subject.length()) {
                internalResult = definition.get(patEl).evaluate(subject, pos);
                if(internalResult.isSuccess()){
                    pos = internalResult.getPos();
                    matchString = internalResult.getSubString();
                } else {
                    pos++;
                }
            }
            if(internalResult.isSuccess()){
                matchResult.setSubString(matchResult.getSubString() + internalResult.getSubString());
                matchResult.setPos(internalResult.getPos());
                patEl++;
                if(definition.get(patEl).getClass().equals(PatternElemEnd.class)){
                    matchResult.setSuccess(true);
                }
            } else {
                pos = oldPos;
                //patEl--;
            }
            
        }
        String result = matchResult.getSubString();
        return result;
    }
    
    private void printPatternElements(PatternElem pat){
        System.out.println("-----------------");
        System.out.println(pat.getElementName());
        System.out.println(pat.getArguments().size() + " args, Pattern");
        if(pat.getArguments().size() > 0){
            for(int i = 0; i < pat.getArguments().size(); i++){
                if(pat.getArgument(i).getClass().equals(PatternFunctionLen.class)||
                        pat.getArgument(i).getClass().equals(PatternElemNull.class)
                        ){ //pattern function
                    System.out.println("\t1 - " + pat.getArgument(i).getElementName());
                } else if(pat.getArgument(i).getClass().equals(PatternTypeInteger.class) ||
                        pat.getArgument(i).getClass().equals(PatternTypeString.class) ||
                        pat.getArgument(i).getClass().equals(PatternTypeCSet.class)
                        ){ //pattern primitive type
                    System.out.println("\t2 - " + pat.getArgument(i).getCharSet());
                } else { //pattern other
                    System.out.println("\t3 - " + pat.getArgument(i).toString());
                }
            }
        }
        System.out.println("=================");
    }
}
