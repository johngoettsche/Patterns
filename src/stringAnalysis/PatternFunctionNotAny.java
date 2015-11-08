/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringAnalysis;

/**
 *
 * @author John
 */
public class PatternFunctionNotAny extends PatternFunction {
        public PatternFunctionNotAny(String args){
        try {
            setArguments(defineFuncArguments(args));
        } catch (StringAnalysisException ex) {
            System.out.println(ex);
        }
        setElementName("Pattern Function NotAny()");
    }
    
    @Override
    public MatchResult evaluate(String subject, int pos){
        CSet cset = new CSet(getArgument(0).getCharSet());
        int stop = endCSet(subject, pos, cset);
        if(stop <= subject.length()){
            this.setResult(new MatchResult(stop, String.valueOf(subject.charAt(
                    stop)), true));
        } else {
            this.setResult(new MatchResult(pos, "", false));
        }
        return this.getResult();
    }
    
    @Override
    public void onMatch(){}
}
