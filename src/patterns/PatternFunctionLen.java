/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patterns;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import java.util.StringTokenizer;

/**
 *
 * @author John H. Goettsche
 */
public class PatternFunctionLen extends PatternFunction{
    
    public PatternFunctionLen(String args){
        System.out.println("LEn: " + args);
        //setCharSet(args);
        setArguments(defineFuncArguments(args));
        setElementName("Pattern Function Len()");
    }
    
    public MatchResult evaluate(String subject, int pos){
        int oldPos = pos;
        int length = 0;
        //System.out.println(getArgument(0).toString());
        System.out.println(getArgument(0).getCharSet());
        if(getArgument(0).getClass().equals(PatternTypeInteger.class)){
            length = getArgument(0).evaluate(subject, pos).getIntValue();
        } else if(getArgument(0).equals(PatternLabel.Len)) {
            // evaluate internal function
        } else {
            System.out.println("argument must reduce to an integer.");
        }
        System.out.println("length: " + length);
        MatchResult result = new MatchResult();
        if(subject.length() >= pos + length){
            result.setSubString(subject.substring(pos, (pos + length)));
            result.setPos(pos + length);
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
            result.setSubString("");
            result.setPos(pos);
        }
        return result;
    }
}
