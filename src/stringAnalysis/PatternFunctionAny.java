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
public class PatternFunctionAny extends PatternFunction{
    public PatternFunctionAny(String args){
        try {
            setArguments(defineFuncArguments(args));
        } catch (StringAnalysisException ex) {
            System.out.println(ex);
        }
        setElementName("Pattern Function Any()");
    }
    
    public MatchResult evaluate(String subject, int pos){
        CSet cset = new CSet(getArgument(0).getCharSet());
        int stop = beginCSet(subject, pos, cset);
        if(stop > 0){
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
