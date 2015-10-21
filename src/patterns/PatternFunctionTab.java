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
public class PatternFunctionTab extends PatternFunction{
        public PatternFunctionTab(String args){
        setArguments(defineFuncArguments(args));
        setElementName("Pattern Function Tab()");
    }
    
    public MatchResult evaluate(String subject, int pos){
        int oldPos = pos;
        int newPos = 0;
        if(getArgument(0).getClass().equals(PatternTypeInteger.class)){
            newPos = (int)getArgument(0).evaluate(subject, pos).getResult(); //.getIntValue();
        } else if(getArgument(0).equals(PatternLabel.Len)) {
            // evaluate internal function
        } else {
            System.out.println("argument must reduce to an integer.");
        }
        MatchResult result = new MatchResult();
        if(subject.length() >= newPos && newPos > pos){
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
