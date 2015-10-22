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
        try {
            setArguments(defineFuncArguments(args));
        } catch (PatternException ex) {
            System.out.println(ex);
        }
        setElementName("Pattern Structure Arbno()");
        patternArgument = new Pattern(args);
    }
    
    public MatchResult evaluate(String subject, int pos){
        int start;
        MatchResult matchResult = new MatchResult(pos, "", false);
        MatchResult internalMatch = new MatchResult(pos, "", true);
        MatchResult nextMatch;
        nextMatch = nextMatch(subject, pos);
        start = pos;
        while(internalMatch.isSuccess() && nextMatch.isSuccess()) {
            matchResult.setResult(subject.substring(start, pos)); //.setSubString(subject.substring(start, pos));
            matchResult.setPos(internalMatch.getPos());
            internalMatch = patternArgument.match(subject, pos);
            pos++;
            nextMatch = nextMatch(subject, pos);
        } 
        matchResult.setSuccess(true);
        this.setResult(matchResult);
        return this.getResult();
    }  
}
