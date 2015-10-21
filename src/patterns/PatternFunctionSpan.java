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
public class PatternFunctionSpan extends PatternFunction {
    public PatternFunctionSpan(String args){
        setArguments(defineFuncArguments(args));
        setElementName("Pattern Function Span()");
    }
    
    public MatchResult evaluate(String subject, int pos){
        MatchResult result = new MatchResult();
        int stop;
        if(atCSet(subject, pos, getArgument(0).getCharSet())){
            stop = endCSet(subject, pos, getArgument(0).getCharSet());
            result.setResult(subject.substring(pos, stop)); //.setSubString(subject.substring(pos, stop));
            result.setPos(stop);
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
            result.setResult(""); //.setSubString("");
            result.setPos(pos);
        }
        return result;
    }
}
