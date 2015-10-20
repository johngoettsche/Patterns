/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patterns;

import java.util.List;

/**
 *
 * @author John H. Goettsche
 */
public class PatternStructureArbno extends PatternStructure{
    Pattern patternArgument;
    
    public PatternStructureArbno(PatternDefinitionIterator def, String args){
        setDefinition(def);
        setArguments(defineFuncArguments(args));
        setElementName("Pattern Structure Arbno()");
        patternArgument = new Pattern(args);
    }
    
    public MatchResult evaluate(String subject, int pos){
        int start;
        MatchResult matchResult = new MatchResult(pos, "");
        matchResult.setSuccess(false);
        MatchResult internalMatch = new MatchResult(pos, "");
        internalMatch.setSuccess(true);
        MatchResult nextMatch;
        nextMatch = nextMatch(subject, pos);
        start = pos;
        System.out.println(internalMatch.isSuccess());
        System.out.println(nextMatch.isSuccess());
        while(internalMatch.isSuccess() && nextMatch.isSuccess()) {
            matchResult.setSubString(subject.substring(start, pos));
            matchResult.setPos(internalMatch.getPos());
            internalMatch = patternArgument.match(subject, pos);
            pos++;
            nextMatch = nextMatch(subject, pos);
        } 
        matchResult.setSuccess(true);
        return matchResult;
    }  
}
