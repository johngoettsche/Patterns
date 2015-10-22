/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patterns;

//import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
//import java.util.StringTokenizer;

/**
 *
 * @author John H. Goettsche
 */
public class PatternFunctionLen extends PatternFunction {
    
    public PatternFunctionLen(String args){
        setArguments(defineFuncArguments(args));
        setElementName("Pattern Function Len()");
    }
    
    
    @Override
    public MatchResult evaluate(String subject, int pos) {
        int oldPos = pos;
        int length = 0;
        if(getArgument(0).getClass().equals(PatternTypeInteger.class)){
            length = (int)getArgument(0).evaluate(subject, pos).getResult(); //.getIntValue();
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
}
