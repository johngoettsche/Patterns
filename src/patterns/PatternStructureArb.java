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
public class PatternStructureArb extends PatternStructure {    
    public PatternStructureArb(List def, int pElem, String args){
        setDefinition(def);
        setPatElem(pElem);
        setArguments(defineFuncArguments(args));
        setElementName("Pattern Structure Abort()");
    }
    
    public MatchResult evaluate(String subject, int pos){
        MatchResult matchResult = nextMatch(subject, pos);
        matchResult.setSubString(subject.substring(pos, matchResult.getPos()));
        matchResult.setSuccess(true);
        return matchResult;
    }  
}
