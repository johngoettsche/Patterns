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
        int oldPos = pos;
        int newPos = subject.length();        
        MatchResult result = new MatchResult();
        if(newPos >= 0 && newPos > pos){
            result.setResult(subject.substring(pos, newPos)); //.setSubString(subject.substring(pos, newPos));
            result.setPos(newPos);
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
            result.setResult(""); //.setSubString("");
            result.setPos(pos);
        }
        return result;
    }
}
