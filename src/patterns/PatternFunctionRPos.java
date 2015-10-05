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
public class PatternFunctionRPos extends PatternFunction{
    public PatternFunctionRPos(String args){
        setArguments(defineFuncArguments(args));
        setElementName("Pattern Function RPos()");
    }
    
    public MatchResult evaluate(String subject, int pos){
        int oldPos = pos;
        int newPos = -1;
        if(getArgument(0).getClass().equals(PatternTypeInteger.class)){
            newPos = subject.length() - subject.length() - getArgument(0).evaluate(subject, pos).getIntValue();
        } else if(getArgument(0).equals(PatternLabel.Len)) {
            // evaluate internal function
        } else {
            System.out.println("argument must reduce to an integer.");
        }
        MatchResult result = new MatchResult();
        if(newPos >= 0 && subject.length() >= newPos && newPos > pos){
            result.setSubString("");
            result.setPos(newPos);
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
            result.setSubString("");
            result.setPos(pos);
        }
        return result;
    }
}
