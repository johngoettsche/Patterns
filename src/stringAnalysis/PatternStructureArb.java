/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringAnalysis;

import java.util.List;

/**
 *
 * @author John H. Goettsche
 */
public class PatternStructureArb extends PatternStructure {    
    public PatternStructureArb(PatternDefinitionIterator def, String args){
        setDefinition(def);
        try {
            setArguments(defineFuncArguments(args));
        } catch (StringAnalysisException ex) {
            System.out.println(ex);
        }
        setElementName("Pattern Structure Arb()");
    }
    
    public MatchResult evaluate(String subject, int pos){
        int start = pos;
        MatchResult matchResult = nextMatch(subject, pos);
        if(matchResult.isSuccess()) {
            matchResult.setResult(subject.substring(start, matchResult.getPos())); 
        } else {
            matchResult.setResult(""); 
        }
        this.setResult(matchResult);
        return this.getResult();
    }  
    
    @Override
    public void onMatch(){}
}
