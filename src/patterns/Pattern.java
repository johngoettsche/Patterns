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
            if (braceL != -1) {//a pattern or a pattern function
                patternName = token.substring(0, braceL);
                patternLabel = PatternLabel.valueOf(patternName);
                braceR = token.lastIndexOf(")");
                if(braceR != -1) arguments = token.substring((braceL + 1), braceR);
                else System.out.println("Pattern Fucntion needs a \")\"");
                if(patternName.isEmpty()) { //a pattern
                    pat = new PatternElemPattern(arguments);
                } else {//a pattern function
                    switch(patternLabel){
                        case Abort :
                            pat = new PatternStructureAbort(arguments);
                            break;
                        case Any :
                            pat = new PatternFunctionAny(arguments);
                            break;
                        case Arb :
                            pat = new PatternStructureArb(definition, definition.size(), arguments);
                            break;
                        case Break :
                            pat = new PatternFunctionBreak(arguments);
                            break;
                        case Fail :
                            pat = new PatternFunctionFail(arguments);
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
                        case Success :
                            pat = new PatternFunctionSucceed(arguments);
                            break;
                        case Tab :
                            pat = new PatternFunctionTab(arguments);
                            break;
                        default :
                            System.out.println("Unknown Pattern Element.");
                            break;
                    } //end pattern function
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
                    } else if(token.contentEquals("+")){
                        pat = new PatternOperatorConcat();
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
        String result = "";
        try {
            result = patternMatch(subject, pos).getSubString();
        } catch (PatternException ex){
            System.out.println(ex);
        }
        return result;
    }
    
    public MatchResult match(String subject, int pos){
        MatchResult result = new MatchResult();
        try {
            result = patternMatch(subject, pos);
        } catch (PatternException ex){
            System.out.println(ex);
        }
        return result;
    }
    
    private MatchResult patternMatch(String subject, int pos) throws PatternException {
        int oldPos = pos;
        String matchString = "";
        MatchResult matchResult = new MatchResult(pos, matchString);
        matchResult.setSuccess(false);
        MatchResult internalResult = new MatchResult(pos, matchString);
        internalResult.setSuccess(false);
        MatchResult backResult = new MatchResult(pos, matchString);
        backResult.setSuccess(true);
        int patElem;
        int patBack = 0;
        while(!matchResult.isSuccess() && pos <= subject.length()) { // no match and chars left
            patElem = patBack;
            //matchString = "";
            System.out.println("pos: " + pos + " - " + matchString);
            while(!matchResult.isSuccess() && !definition.get(patElem).getClass().equals(PatternElemEnd.class)) {
                System.out.println("\t" + definition.get(patElem).getElementName());
                //not last pattern element
                //needs adjusted for operators possibly try MatchResult and reduce PatternElem variables
                definition.get(patElem).setOldPos(pos);
                definition.get(patElem).setSubString(matchString);
                //
                if(!definition.get(patElem).getClass().equals(PatternOperatorAlternate.class)) { //not Alternate 
                    if(patElem > 0) {// not first element
                        //System.out.println(definition.get(patElem - 1).getElementName());
                        if (definition.get(patElem - 1).getClass().getSuperclass().equals(PatternElem.class) || 
                                definition.get(patElem - 1).getClass().getSuperclass().equals(PatternFunction.class)){
                            //proceded by function, pattern, string
                            if(definition.get(patElem).getClass().getSuperclass().equals(PatternOperator.class)) {
                                //is an operator
                                definition.get(patElem).setSubString(matchString);
                                System.out.println("\t\t" + subject.substring(pos));
                                internalResult = definition.get(patElem).evaluate(subject, pos);
                            } else { // not an operator
                                throw new PatternException("Pattern Element or Pattern Function must be followed by a Pattern Operator.");
                            }
                        } else {//proceded by an operator
                            System.out.println("\t\t" + subject.substring(pos));
                            internalResult = definition.get(patElem).evaluate(subject, pos);
                        }
                    } else { //first element
                        System.out.println("\t\t" + subject.substring(pos));
                        internalResult = definition.get(patElem).evaluate(subject, pos);
                    }
                } else { //alternation.
                    matchString = "";
                    patElem++;
                    continue;
                } // end alternation
                
                patElem++;
                if(internalResult.isSuccess()) {
                    patBack = patElem;
                    matchString += internalResult.getSubString();
                    pos = internalResult.getPos();
                } else if(!internalResult.isSuccess()) {
                    if(internalResult.getPos() == -1){
                        break;
                    }
                    patElem = findOperatorAlternate(patElem);
                    continue;
                }
            } //end checking patterns 
 
            if(internalResult.isSuccess()){//successful internal match
                matchResult.setSubString(matchString);
                matchResult.setPos(internalResult.getPos());  
                matchResult.setSuccess(true);
                pos = matchResult.getPos();
                continue;
            }
            pos++;
        }
         return matchResult;
    }
    
    private int findOperatorAlternate(int patElem){
        while(!definition.get(patElem).getClass().equals(PatternElemEnd.class)){
        //for(int p = patElem; patElem < definition.size(); p++){
            System.out.println("finding Alternate: " + definition.get(patElem).getElementName());
            if(definition.get(patElem).getClass().equals(PatternOperatorAlternate.class))return patElem;
            patElem++;
        }
        return patElem;
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
                    System.out.println("\t" + pat.getArgument(i).getElementName());
                } else if(pat.getArgument(i).getClass().equals(PatternTypeInteger.class) ||
                        pat.getArgument(i).getClass().equals(PatternTypeString.class) ||
                        pat.getArgument(i).getClass().equals(PatternTypeCSet.class)
                        ){ //pattern primitive type
                    System.out.println("\t" + pat.getArgument(i).getCharSet());
                } else { //pattern other
                    System.out.println("\t" + pat.getArgument(i).toString());
                }
            }
        }
        System.out.println("=================");
    }
}
