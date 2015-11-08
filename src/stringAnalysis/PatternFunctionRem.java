/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringAnalysis;

/**
 *
 * @author John
 */
public class PatternFunctionRem extends PatternFunction{
    public PatternFunctionRem(String args){
        try {
            setArguments(defineFuncArguments(args));
        } catch (StringAnalysisException ex) {
            System.out.println(ex);
        }
        setElementName("Pattern Function Rem()");
    }
    
    @Override
    public MatchResult evaluate(String subject, int pos){
        int newPos = subject.length();        
        if(newPos >= 0 && newPos > pos){
            this.setResult(new MatchResult(newPos, subject.substring(pos,
                    newPos), true));
        } else {
            this.setResult(new MatchResult(pos, "", false));
        }
        return this.getResult();
    }
    
    
    @Override
    public void onMatch(){}
}
