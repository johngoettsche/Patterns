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
public class PatternFunctionNotAny extends PatternFunction {
        public PatternFunctionNotAny(String args){
        setArguments(defineFuncArguments(args));
        setElementName("Pattern Function NotAny()");
    }
    
    @Override
    public MatchResult evaluate(String subject, int pos){
        MatchResult result = new MatchResult();
        int stop = endCSet(subject, pos, getArgument(0).getCharSet());
        if(stop <= subject.length()){
            result.setResult(String.valueOf(subject.charAt(stop))); //.setSubString(String.valueOf(subject.charAt(stop)));
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
