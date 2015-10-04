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
        setArguments(defineFuncArguments(args));
        setElementName("Pattern Function Len()");
    }
    
    public MatchResult evaluate(){
        MatchResult result = new MatchResult();
        result.setPos(0);
        result.setSubString("");
        
        return result;
    }
}
