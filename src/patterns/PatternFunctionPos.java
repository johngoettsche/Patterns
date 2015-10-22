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
public class PatternFunctionPos extends PatternFunction{
    public PatternFunctionPos(String args){
        try {
            setArguments(defineFuncArguments(args));
        } catch (PatternException ex) {
            System.out.println(ex);
        }
        setElementName("Pattern Function Pos()");
    }
    
    public MatchResult evaluate(String subject, int pos){
        int oldPos = pos;
        int newPos = -1;
        if(getArgument(0).getClass().equals(PatternTypeInteger.class)){
            newPos = subject.length() - (int)getArgument(0).evaluate(subject, pos).getResult(); //.getIntValue();
        } else if(getArgument(0).equals(PatternLabel.Len)) {
            // evaluate internal function
        } else {
            System.out.println("argument must reduce to an integer.");
        }
        if(newPos >= 0 && subject.length() >= newPos && newPos > pos){
            this.setResult(new MatchResult(newPos, "", true));
        } else {
            this.setResult(new MatchResult(pos, "", false));
        }
        return this.getResult();
    }
}
