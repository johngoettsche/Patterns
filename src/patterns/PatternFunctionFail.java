/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patterns;

import java.util.ArrayList;

/**
 *
 * @author John H. Goettsche
 */
public class PatternFunctionFail extends PatternFunction {
    public PatternFunctionFail(String args){
        setArguments(new ArrayList());
        setElementName("Pattern Function Fail()");
    }
    
    public MatchResult evaluate(String subject, int pos){
        MatchResult result = new MatchResult();
        result.setSuccess(false);
        result.setSubString("");
        result.setPos(pos);
        return result;
    }
}
