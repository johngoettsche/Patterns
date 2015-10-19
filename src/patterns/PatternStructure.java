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
public abstract class PatternStructure extends PatternElem {
    private PatternDefinitionIterator definition;

    public void setDefinition(PatternDefinitionIterator def) {
        this.definition = def;
    }
    
    public MatchResult nextMatch(String subject, int pos){
        int start = pos;
        int endPos = pos;
        MatchResult matchResult = new MatchResult();
        matchResult.setSuccess(false);
        while(pos < subject.length()){
            if(definition.getNextNext().getClass().equals(PatternOperatorConcat.class)){
                definition.next();
                continue;
            }
            endPos = pos;
            matchResult = definition.getNextNext().evaluate(subject, pos);
            if(matchResult.isSuccess()){
                matchResult.setSubString(subject.substring(start, endPos));
                matchResult.setPos(endPos);
                return matchResult;
            }
            pos++;
        } 
        return matchResult;
    }
}
