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
public class PatternFunctionAny extends PatternFunction{
    public PatternFunctionAny(String args){
        setArguments(defineFuncArguments(args));
        setElementName("Pattern Function Any()");
    }
    
    public MatchResult evaluate(String subject, int pos){
        int stop = beginCSet(subject, pos, getArgument(0).getCharSet());
        if(stop > 0){
            this.setResult(new MatchResult(stop, String.valueOf(subject.charAt(
                    stop)), true));
        } else {
            this.setResult(new MatchResult(pos, "", false));
        }
        return this.getResult();
    }
}
