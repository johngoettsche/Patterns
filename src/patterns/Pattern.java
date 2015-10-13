/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package patterns;

import java.util.StringTokenizer;

/**
 *
 * @author John H. Goettsche
 */

public class Pattern {
    PatternDefinitionIterator definition = new PatternDefinitionIterator();
    PatternMatch patternMatch;
    //List<PatternElem> definition = new ArrayList();
    PatternElem nullElem = new PatternElemNull();
    
    public Pattern(){
        
    }
    
    public Pattern(String p){
        try {
            patternDefinition(p);
        } catch(PatternException ex) {
            System.out.println(ex);
        }
    }
    
    public void define(String p){
        try {
            patternDefinition(p);
        } catch(PatternException ex) {
            System.out.println(ex);
        }
    }
    
    private void patternDefinition(String pattern) throws PatternException{
        PatternElem pat = nullElem;
        int pos = 0;
        String token;
        int braceL, braceR;
        String args;
        PatternLabel patternLabel;
        while(pos < pattern.length()){
            
            braceL = pattern.indexOf("(");
            if(braceL >= 0) {
                System.out.println(pos + " : " + braceL);
                token = pattern.substring(braceL, pos);
                if(token.length() > 0) { //pattern functions
                    braceR = findClosingSymbol(token, "(", braceL);
                    if(braceR != -1) args = token.substring((braceL + 1), braceR);
                    else throw new PatternException("Pattern Fucntion needs a \")\"");
                    System.out.println(args);
                    patternLabel = PatternLabel.valueOf(token);
                    switch(patternLabel){
                        case Abort :
                            pat = new PatternStructureAbort(args);
                            break;
                        case Any :
                            pat = new PatternFunctionAny(args);
                            break;
                        case Arb :
                            pat = new PatternStructureArb(definition, args);
                            break;
                        case Arbno :
                            pat = new PatternStructureArbno(definition, args);
                            break;
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
                            throw new PatternException("Unrecognized Pattern Function.");
                    } 
                    pos = braceR + 1;
                    //end patternFunctions
                } else { //a pattern subset
                    braceR = findClosingSymbol(pattern, "(", braceL);
                    if(braceR != -1) args = pattern.substring(braceL + 1, braceR);
                    else throw new PatternException("Pattern bracket needs a closing \")\"");
                    System.out.println("---Internal Pattern---");
                    System.out.println(args);
                    pat = new PatternElemPattern(args);
                    System.out.println("---End Internal Pattern---");
                    pos = braceR;
                }
            } else {//a string or cset
                braceL = pos;
                System.out.println(pos + " : " + pattern.length());
                if(String.valueOf(pattern.charAt(pos)).equals("'")) {//is a string element
                    braceR = findClosingSymbol(pattern, "'", braceL);
                    System.out.println("R: " + braceR);
                    if(braceR >= 0) {
                        token = token = pattern.substring(pos, braceL);
                        pat = new PatternElemString(token);
                        pos = braceR + 1;
                    }
                } else if(String.valueOf(pattern.charAt(pos)).equals("|")) {//is a cset
                    pat = new PatternOperatorAlternate();
                    pos++;
                } else if(String.valueOf(pattern.charAt(pos)).equals("+")) {//is a cset
                    pat = new PatternOperatorConcat();
                    pos++;
                } else {
                    throw new PatternException("Unrecognized Pattern Element.");
                }
            }
            while(pos < pattern.length() && String.valueOf(pattern.charAt(pos)).equals(" ")) { pos++; }
        }//end while
    }
    
    public int findClosingSymbol(String subject, String startingSymbol, int left){
        String closingSymbol = "";
        System.out.println(subject + " : " + startingSymbol + " : " + left);
        if(startingSymbol.equals("'") || startingSymbol.equals("`")) closingSymbol = startingSymbol;
        else if(startingSymbol.equals("(")) closingSymbol = ")";
        else if(startingSymbol.equals("[")) closingSymbol = "]";
        else if(startingSymbol.equals("{")) closingSymbol = "}";
        System.out.println("Closing Symbol: " + closingSymbol);
        int count = 1;
        for(int pos = left + 1; pos < subject.length(); pos++){
            if(String.valueOf(subject.charAt(pos)).equals(closingSymbol)) count--;
            else if(String.valueOf(subject.charAt(pos)).equals(startingSymbol)) count++;
            System.out.println(count);
            if(count == 0) {
                System.out.println(left + " : " + pos);
                return pos;
            }
        }
        return -1;
    }
    
    public String match(String subject){
        int pos = 0;
        String result = "";
        /*patternMatch = new PatternMatch(subject, definition, 0);
        patternMatch.getState().notEndOfSubject();
        return patternMatch.getMatchResult().getSubString();*/
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

    public PatternDefinitionIterator getDefinition() {
        return definition;
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
