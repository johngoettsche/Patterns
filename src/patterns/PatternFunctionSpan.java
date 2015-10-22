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
        try {
            setArguments(defineFuncArguments(args));
        } catch (PatternException ex) {
            System.out.println(ex);
        }
        setElementName("Pattern Function Span()");
    }
    
    public MatchResult evaluate(String subject, int pos){
        int stop;
        if(atCSet(subject, pos, getArgument(0).getCharSet())){
            stop = endCSet(subject, pos, getArgument(0).getCharSet());
            this.setResult(new MatchResult(stop, subject.substring(pos, stop), true));
        } else {
            this.setResult(new MatchResult(pos, "", false));
        }
        return this.getResult();
    }
}
