/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringAnalysis;

/**
 *
 * @author John H. Goettsche
 */
public class PatternStructureFence extends PatternStructure {
    public PatternStructureFence(String args){
        try {
            setArguments(defineFuncArguments(args));
        } catch (StringAnalysisException ex) {
            System.out.println(ex);
        }
        setElementName("Pattern Structure Fence()");
    }
    
    public MatchResult evaluate(String subject, int pos){
        this.setResult(new MatchResult(pos, "", true));
        return this.getResult();
    }
    
    @Override
    public void onMatch(){}
}
