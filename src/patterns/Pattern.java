/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package patterns;

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
        /**
         * performs the lexical analysis of the pattern definition provided
         */
        PatternElem pat = nullElem;
        int intValue;
        String stringValue;
        Object primitiveObject;
        
        int stop;
        int pos = 0;
        String token;
        int braceL, braceR;
        int strL, strR;
        int oldPos;
        String args;

        //clear beginning whitespace
        while(pattern.charAt(pos) == ' ' && pos < pattern.length()) pos++;
        while(pos < pattern.length()){
            oldPos = pos; 
            braceL = pattern.indexOf("(", pos);
            strL = pattern.indexOf("'", pos);
            if(pattern.charAt(pos) == '\'') {
                System.out.println("String Element");
                strR = findClosingSymbol(pattern, "'", strL);
                if(strR > 0) {
                    pat = new PatternElemString(pattern.substring(strL + 1,
                            strR));
                    pos = strR + 1;
                } else {
                    throw new PatternException("Pattern String needs a closing \"'\"");
                }
            } else if(pattern.charAt(pos) == '(') {
                System.out.println("Sub Pattern");
                braceR = findClosingSymbol(pattern, "(", braceL);
                if(braceR > 0) {
                    args = pattern.substring(braceL + 1, braceR);
                    pat = new PatternElemPattern(args);
                    pos = braceR + 1;
                } else {
                    throw new PatternException("Pattern bracket needs a closing \")\"");
                }
            } else if(pattern.charAt(pos) == '|') {
                System.out.println("Alternate Operator");
                pat = new PatternOperatorAlternate();
                pos++;
            } else if(pattern.charAt(pos) == '+') {
                System.out.println("Concatenation Operator");
                pat = new PatternOperatorConcat();
                pos++;
            } else if(pattern.charAt(pos + 1) == '>') { // assignments
                if(pattern.charAt(pos) == '-') {
                    System.out.println("Immediate Assignment");
                    pat = new PatternOperatorImmediateAssignment(definition);
                    pos = pos + 2;
                } else if(pattern.charAt(pos) == '=') {
                    System.out.println("Conditional Assignment");
                    pat = new PatternOperatorConditionalAssignment(definition);
                    pos = pos + 2;
                } else if(pattern.charAt(pos) == '.') {
                    System.out.println("Cursor Assignment");
                    pat = new PatternOperatorCursorAssignment(definition);
                    pos = pos + 2;
                } else {
                    throw new PatternException("Unrecognized Assignment Operator.");
                }
            } else if(memberOfCSet(pattern.charAt(pos), csets.letters)) {
                if(braceL > pos) {
                    token = pattern.substring(pos, braceL);
                    braceR = findClosingSymbol(pattern, "(", braceL);
                    if(braceR > pos) {
                        pat = makePatternFunction(token, pattern, braceL, braceR);
                        if(pat.getClass().equals(PatternElemNull.class)) {
                            throw new PatternException("Unrecognized Pattern Function");
                        } else {
                            pos = braceR + 1;
                        }
                    } else {
                        throw new PatternException("Pattern bracket needs a closing \")\"");
                    }
                } else {
                    System.out.println("Variable");
                    stop = endLetters(pattern, pos);
                    token = pattern.substring(pos, stop);
                    pat = new PatternElemVariable(token);
                    pos = stop + 1;
                }
            } else {
                System.out.println(" whats up ");
                break;
            }

            while(pos < pattern.length() && String.valueOf(pattern.charAt(pos)).equals(" ")) { pos++; }
            printPatternElements(pat);
            definition.add(pat);
        }//end while
    }

    private PatternElem makePatternFunction(String token, String pattern, int braceL, int braceR) throws PatternException{
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
        } catch (PatternException ex){
            System.out.println(ex);
        }
        
        return result;
    }
    
    public MatchResult match(String subject, int pos){
        MatchResult result = new MatchResult();
        anchored = true;
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
                //not last pattern element
                //needs adjusted for operators possibly try MatchResult and reduce PatternElem variables
                PatternElem elem = definition.current();
                elem.setOldPos(pos);
                elem.setSubString(matchString);
                System.out.println(elem.getElementName());
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
                    matchString += (String)internalResult.getResult(); //getSubString();
                    System.out.println("\tmatchString: " + matchString);
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
                System.out.println("\tmatchString on success: " + matchString);
                matchResult.setResult(matchString); //setSubString(matchString);
                matchResult.setPos(internalResult.getPos());  
                matchResult.setSuccess(true);
                pos = matchResult.getPos();
                continue;
            } else if(anchored) {
                matchResult.setResult(""); //setSubString("");
                matchResult.setPos(oldPos);
                matchResult.setSuccess(false);
                break;
            }
            //System.out.println("\t success at end: " + matchResult.isSuccess());
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
