/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stringAnalysis;

import java.lang.reflect.Field;

/**
 *
 * @author John H. Goettsche
 */

public class Pattern extends PatternGlobals{
    PatternDefinitionIterator definition = new PatternDefinitionIterator();
    PatternMatch patternMatch;
    PatternElem nullElem = new PatternElemNull();
    boolean anchored;
    PatternVariableMap patternVariableMap;
    CSets csets = new CSets();
    //private PatternGlobals patternGlobals = new PatternGlobals();
    
    public Pattern(){
        patternVariableMap = PatternVariableMap.getInstance();
    }
    
    public Pattern(String pat){
        patternVariableMap = PatternVariableMap.getInstance();
        try {
            patternDefinition(pat);
        } catch(StringAnalysisException ex) {
            System.out.println(ex);
        }
    }
    
    public void define(String p){
        try {
            patternDefinition(p);
        } catch(StringAnalysisException ex) {
            System.out.println(ex);
        }
    }
    
    private void patternDefinition(String pattern) throws StringAnalysisException{
        /**
         * performs the lexical analysis of the pattern definition provided
         */
        PatternElem pat = nullElem;
        int stop;
        int pos = 0;
        String token;
        int braceL, braceR;
        int strL, strR;
        String args;
        int op;

        //clear beginning whitespace
        while(pattern.charAt(pos) == ' ' && pos < pattern.length()) pos++;
        while(pos < pattern.length()){//more characters
            braceL = pattern.indexOf("(", pos);
            strL = pattern.indexOf("'", pos);
            if(pattern.charAt(pos) == '\'') {
                strR = findClosingSymbol(pattern, "'", strL);
                if(strR > 0) { // a string
                    pat = new PatternElemString(pattern.substring(strL + 1,
                            strR));
                    pos = strR + 1;
                } else {
                    throw new StringAnalysisException("Pattern String needs a closing \"'\"");
                }
            } else if(pattern.charAt(pos) == '(') {
                braceR = findClosingSymbol(pattern, "(", braceL);
                if(braceR > 0) { // a sub pattern
                    args = pattern.substring(braceL + 1, braceR);
                    pat = new PatternElemPattern(args);
                    pos = braceR + 1;
                } else {
                    throw new StringAnalysisException("Pattern bracket needs a closing \")\"");
                }
            } else if(pattern.charAt(pos) == '|') { // an alternation
                pat = new PatternOperatorAlternate();
                pos++;
            } else if(pattern.charAt(pos) == '+') { // a concatenation
                pat = new PatternOperatorConcat();
                pos++;
            } else if(pattern.charAt(pos + 1) == '>') { // assignments
                if(pattern.charAt(pos) == '-') { // immediate assignment
                    //System.out.println("immediate assignment");
                    pat = new PatternOperatorImmediateAssignment(definition);
                    pos = pos + 2;
                } else if(pattern.charAt(pos) == '=') { // conditional assignment
                    //System.out.println("conditional assignment");
                    pat = new PatternOperatorConditionalAssignment(definition);
                    pos = pos + 2;
                } else if(pattern.charAt(pos) == '.') { // cursor assignment
                    //System.out.println("cursor assignment");
                    pat = new PatternOperatorCursorAssignment(definition);
                    pos = pos + 2;
                } else {
                    throw new StringAnalysisException("Unrecognized Assignment Operator.");
                }
            } else if(csets.letters.memberOf(pattern.charAt(pos))) { //starts with a letter
                int alter = pattern.indexOf("|");
                int concat = pattern.indexOf("+");
                if((alter > pos && alter < braceL) || (concat > pos && concat < braceL)) { // variable before an operator
                    //expand to include all opperators
                    //System.out.println("making variable before operator");
                    stop = pos;
                    if(alter < concat && alter >= 0) stop = alter - 1;
                    else if(concat >= 0) stop = concat - 1;
                    //else stop = pattern.length();
                    while(pattern.charAt(stop - 1) == ' ') stop--;
                    token = pattern.substring(pos, stop);
                    //System.out.println(token);
                    pat = new PatternElemVariable(token);
                    pos = stop + 1;
                } else if(braceL > pos) {
                    token = pattern.substring(pos, braceL);
                    braceR = findClosingSymbol(pattern, "(", braceL);
                    if(braceR > pos) {
                        pat = makePatternFunction(token, pattern, braceL, braceR);
                        if(pat.getClass().equals(PatternElemNull.class)) {
                            throw new StringAnalysisException("Unrecognized Pattern Function");
                        } else {
                            pos = braceR + 1;
                        }
                    } else {
                        throw new StringAnalysisException("Pattern bracket needs a closing \")\"");
                    }
                } else { // variable
                    stop = pattern.length();
                    token = pattern.substring(pos, stop);
                    pat = new PatternElemVariable(token);
                    pos = stop + 1;
                }
            } 

            while(pos < pattern.length() && String.valueOf(pattern.charAt(pos)).equals(" ")) { pos++; }
            //printPatternElements(pat);
            definition.add(pat);
        }//end while
    }

    private PatternElem makePatternFunction(String token, String pattern, int braceL, int braceR) throws StringAnalysisException{
        PatternElem pat;
        PatternLabel patternLabel;
        String args;
        
        patternLabel = PatternLabel.valueOf(token);
        args = pattern.substring(braceL + 1, braceR);
        //System.out.println(args);
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
            case Pos :
                pat = new PatternFunctionPos(args);
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
    
    public String match(String subject){
        int pos = 0;
        String result = "";
        /*patternMatch = new PatternMatch(subject, definition, 0);
        patternMatch.getState().notEndOfSubject();
        return patternMatch.getMatchResult().getSubString();*/
        anchored = false;
        try {
            result = (String)patternMatch(subject, pos).getResult();  // getSubString();
        } catch (StringAnalysisException ex){
            System.out.println(ex);
        }
        
        return result;
    }
    
    public MatchResult match(String subject, int pos){
        MatchResult result = new MatchResult();
        anchored = true;
        try {
            result = patternMatch(subject, pos);
        } catch (StringAnalysisException ex){
            System.out.println(ex);
        }
        return result;
    }
    
    private MatchResult patternMatch(String subject, int pos) throws StringAnalysisException {
        int oldPos = pos;
        String matchString = "";
        MatchResult matchResult = new MatchResult(pos, matchString, false);
        MatchResult internalResult = new MatchResult(pos, matchString, false);
        MatchResult backResult = new MatchResult(pos, matchString, true);
        //definition.start(); 
        while(!matchResult.isSuccess() && pos <= subject.length()) { // no match and chars left
            definition.start();   
            //System.out.println(subject + " : " + pos);
            //System.out.println("success: " + matchResult.isSuccess());
            //System.out.println("next: " + definition.hasNext());
            while(!matchResult.isSuccess() && definition.hasNext()) {
                //System.out.println("matchString: " + matchString);
                //not last pattern element
                //needs adjusted for operators possibly try MatchResult and reduce PatternElem variables
                PatternElem elem = definition.current();
                elem.setOldPos(pos);
                elem.setSubString(matchString);
                //System.out.println(elem.getElementName());
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
                                //System.out.println(elem.getElementName());
                                //System.out.println(subject);
                                //System.out.println(pos);
                                internalResult = elem.evaluate(subject, pos);
                            } else { // not an operator
                                throw new StringAnalysisException("Pattern Element or Pattern Function must be followed by a Pattern Operator.");
                            }
                        } else {//proceded by an operator
                            if(!elem.getClass().equals(PatternElemVariable.class)) {
                                internalResult = elem.evaluate(subject, pos);
                            }
                        }
                    } else { //first element
                        internalResult = elem.evaluate(subject, pos);
                    }
                } else { //alternation.
                    if(internalResult.isSuccess()){
                        matchResult.setSuccess(true);
                        matchResult.setResult(matchString); //setSubString(matchString);
                        matchResult.setPos(internalResult.getPos());  
                        break;
                    }
                    matchString = "";
                    internalResult = elem.evaluate(subject, pos);
                    definition.next();
                    continue;
                } // end alternation
                
                
                if(internalResult.isSuccess()) {
                    //System.out.println("Internal result is a success.");
                    matchString += internalResult.getResult(); //getSubString();
                    //System.out.println("\tmatchString: " + matchString);
                    pos = internalResult.getPos();
                } else {
                    if(internalResult.getPos() == -1){
                        break;
                    }
                    //System.out.println("pos at failed internal match: " + pos);
                    findOperatorAlternate();
                }
                definition.next();
            } //end checking patterns 
            //System.out.println("is success: " + internalResult.isSuccess());
            if(internalResult.isSuccess()){//successful internal match
                //System.out.println("\tmatchString on success: " + matchString);
                matchResult.setResult(matchString); //setSubString(matchString);
                matchResult.setPos(internalResult.getPos());  
                matchResult.setSuccess(true);
                pos = matchResult.getPos();
                continue;
            } else if(anchored) {
                matchResult.setResult(""); 
                matchResult.setPos(oldPos);
                matchResult.setSuccess(false);
                break;
            }
            //System.out.println("\t success at end: " + matchResult.isSuccess());
            pos++;
        }
        if(matchResult.isSuccess()){
            definition.start();
            while(definition.hasNext()){
                if(definition.current().getClass().equals(PatternOperatorConditionalAssignment.class)) {
                    definition.current().onMatch();
                }
                definition.next();
            }
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
