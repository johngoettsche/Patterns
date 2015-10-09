/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package patterns;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author John H. Goettsche
 */

public class Pattern {
    PatternDefinition definition = new PatternDefinition();
    //List<PatternElem> definition = new ArrayList();
    
    public Pattern(){
        
    }
    
    public Pattern(String p){
        patternDefinition(p);
    }
    
    public void define(String p){
        patternDefinition(p);
    }
    
    private void patternDefinition(String p){
        PatternElem pat;
        String token;
        StringTokenizer stringTokenizer = new StringTokenizer(p);
        while (stringTokenizer.hasMoreTokens()) {
            token = stringTokenizer.nextToken();
            pat = definePatternElem(token);
            definition.add(pat);
        }
        //PatternElem endElem = new PatternElemEnd();
        //definition.add(endElem);
    }
    
    public PatternElem definePatternElem(String token){
        PatternElem nullElem = new PatternElemNull();
        int braceL, braceR;
        PatternLabel patternLabel;
        
        String patternName = "";
        String arguments = "";
        PatternElem pat = nullElem;
        braceL = token.indexOf("(");
        if (braceL != -1) { //a pattern or a pattern function
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
                        pat = new PatternStructureArb(definition, arguments);
                        break;
                    case Arbno :
                        pat = new PatternStructureArbno(definition, arguments);
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
        //printPatternElements(pat);
        return pat;
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
        //int oldPos = pos;
        String matchString = "";
        MatchResult matchResult = new MatchResult(pos, matchString);
        matchResult.setSuccess(false);
        MatchResult internalResult = new MatchResult(pos, matchString);
        internalResult.setSuccess(false);
        MatchResult backResult = new MatchResult(pos, matchString);
        backResult.setSuccess(true);

        while(!matchResult.isSuccess() && pos <= subject.length()) { // no match and chars left
            definition.start();            
            while(!matchResult.isSuccess() && definition.hasNext()) {
                //not last pattern element
                //needs adjusted for operators possibly try MatchResult and reduce PatternElem variables
                PatternElem elem = definition.current();
                elem.setOldPos(pos);
                elem.setSubString(matchString);
                //
                if(!elem.getClass().equals(PatternOperatorAlternate.class)) { //not Alternate 
                    if(definition.hasPrevious()) { //not first element
                        if (definition.getPrevious().getClass().getSuperclass().equals(PatternElem.class) || 
                                definition.getPrevious().getClass().getSuperclass().equals(PatternFunction.class) ||
                                definition.getPrevious().getClass().getSuperclass().equals(PatternStructure.class)){
                            //proceded by function, structure, pattern, string
                            if(elem.getClass().getSuperclass().equals(PatternOperator.class)) {
                                //is an operator
                                elem.setSubString(matchString);
                                internalResult = elem.evaluate(subject, pos);
                            } else { // not an operator
                                throw new PatternException("Pattern Element or Pattern Function must be followed by a Pattern Operator.");
                            }
                        } else {//proceded by an operator
                            internalResult = elem.evaluate(subject, pos);
                        }
                    } else { //first element
                        internalResult = elem.evaluate(subject, pos);
                    }
                } else { //alternation.
                    matchString = "";
                    definition.next();
                    continue;
                } // end alternation
                
                definition.next();
                if(internalResult.isSuccess()) {
                    matchString += internalResult.getSubString();
                    pos = internalResult.getPos();
                } else {
                    if(internalResult.getPos() == -1){
                        break;
                    }
                    findOperatorAlternate();
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
    
    private void findOperatorAlternate(){
        while(!definition.hasNext()) {
            //System.out.println("finding Alternate: " + definition.get(patElem).getElementName());
            if(definition.current().getClass().equals(PatternOperatorAlternate.class) || 
                    definition.current().getClass().equals(PatternElemNull.class)) {
                break;
            }
            definition.next();
        }
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
