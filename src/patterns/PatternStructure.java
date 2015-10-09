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
    private PatternDefinition definition;

    public void setDefinition(PatternDefinition definition) {
        this.definition = definition;
    }
    
    public MatchResult nextMatch(String subject, int pos){
        MatchResult matchResult = new MatchResult();
        matchResult.setSuccess(false);
        while(pos < subject.length()){
            while(definition.hasNext()){
                matchResult = definition.getNextNext().evaluate(subject, pos);
                if(matchResult.isSuccess()){
                    return matchResult;
                }
            }
        } 
        return matchResult;
    }
}
