/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patterns;

/**
 *
 * @author John
 */
public class PatternFunctionRem extends PatternFunction{
    public PatternFunctionRem(String args){
        setArguments(defineFuncArguments(args));
        setElementName("Pattern Function Rem()");
    }
    
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
}
