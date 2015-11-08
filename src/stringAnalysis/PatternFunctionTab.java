/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringAnalysis;

/**
 *
 * @author John H. Goettsche
 */
public class PatternFunctionTab extends PatternFunction{
        public PatternFunctionTab(String args){
        try {
            setArguments(defineFuncArguments(args));
        } catch (StringAnalysisException ex) {
            System.out.println(ex);
        }
        setElementName("Pattern Function Tab()");
    }
    
    public MatchResult evaluate(String subject, int pos){
        int newPos = 0;
        if(getArgument(0).getClass().equals(PatternTypeInteger.class) ||
                getArgument(0).getClass().equals(PatternElemVariable.class)){
            newPos = Integer.parseInt(getArgument(0).evaluate(subject, pos).getResult().toString());
        } else {
            System.out.println("argument must reduce to an integer.");
        }
        if(subject.length() >= newPos && newPos > pos){
            this.setResult(new MatchResult(newPos, subject.substring(pos,
                    newPos), true));
        } else {
            this.setResult(new MatchResult(pos, "", false));
        }
        return this.getResult();
    }
    
    @Override
    public void onMatch(){}
}
