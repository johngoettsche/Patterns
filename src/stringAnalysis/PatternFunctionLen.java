/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringAnalysis;

//import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
//import java.util.StringTokenizer;

/**
 *
 * @author John H. Goettsche
 */
public class PatternFunctionLen extends PatternFunction {
    
    public PatternFunctionLen(String args){
        try {
            setArguments(defineFuncArguments(args));
        } catch (StringAnalysisException ex) {
            System.out.println(ex);
        }
        setElementName("Pattern Function Len()");
    }
    
    
    @Override
    public MatchResult evaluate(String subject, int pos) {
        int length = 0;
        if(getArgument(0).getClass().equals(PatternTypeInteger.class) ||
                getArgument(0).getClass().equals(PatternElemVariable.class)) {
            //System.out.println(getArgument(0).getElementName());
            length = Integer.parseInt(getArgument(0).evaluate(subject, pos).getResult().toString());
        } else {
            System.out.println("argument must reduce to an integer.");
        }
        if(subject.length() >= pos + length){
            this.setResult(new MatchResult(pos + length, subject.substring(
                    pos, pos + length), true));
        } else {
            this.setResult(new MatchResult(pos, "", false));
        }
        return this.getResult();
    }
    
    @Override
    public void onMatch(){}
}
