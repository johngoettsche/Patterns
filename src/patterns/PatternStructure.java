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
    private List<PatternElem> definition;
    private int patElem;

    public void setDefinition(List<PatternElem> definition) {
        this.definition = definition;
    }

    public void setPatElem(int patElem) {
        this.patElem = patElem;
    }
    
    public MatchResult nextMatch(String subject, int pos){
        MatchResult matchResult = new MatchResult();
        matchResult.setSuccess(false);
        while(pos < subject.length()){
            while(!definition.get(patElem).getClass().equals(PatternElemEnd.class)){
                matchResult = definition.get(patElem + 1).evaluate(subject, pos);
                if(matchResult.isSuccess()){
                    return matchResult;
                }
            }
            pos++;
        } 
        return matchResult;
    }
}
