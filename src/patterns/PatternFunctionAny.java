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
        MatchResult result = new MatchResult();
        int stop = beginCSet(subject, pos, getArgument(0).getCharSet());
        if(stop > 0){
            result.setSubString(String.valueOf(subject.charAt(stop)));
            result.setPos(stop);
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
            result.setSubString("");
            result.setPos(pos);
        }
        return result;
    }
}