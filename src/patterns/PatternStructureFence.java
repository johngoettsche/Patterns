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
public class PatternStructureFence extends PatternStructure {
    public PatternStructureFence(String args){
        setArguments(defineFuncArguments(args));
        setElementName("Pattern Structure Fence()");
    }
    
    public MatchResult evaluate(String subject, int pos){
        MatchResult matchResult = new MatchResult();
        matchResult.setPos(pos);
        matchResult.setSubString("");
        matchResult.setSuccess(true);
        return matchResult;
    }
}
