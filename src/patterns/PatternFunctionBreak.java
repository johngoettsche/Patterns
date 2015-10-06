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
public class PatternFunctionBreak extends PatternFunction {
    public PatternFunctionBreak(String args){
        setArguments(defineFuncArguments(args));
        setElementName("Pattern Function Break()");
    }
    
    public MatchResult evaluate(String subject, int pos){
        MatchResult result = new MatchResult();
        int stop;
        stop = beginCSet(subject, pos, getArgument(0).getCharSet());
        if(stop <= subject.length()) {
            result.setSubString(subject.substring(pos, stop));
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
