/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patterns;

/**
 *
 * @author John H. Goettsche
 */
public class PatternStructureAbort extends PatternStructure {
    public PatternStructureAbort(String args){
        setArguments(defineFuncArguments(args));
        setElementName("Pattern Structure Abort()");
    }
    
    public MatchResult evaluate(String subject, int pos){
        MatchResult matchResult = new MatchResult(0, "");
        matchResult.setPos(-1);
        return matchResult;
    }    
}
