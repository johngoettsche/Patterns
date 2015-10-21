/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patterns;

import java.util.ArrayList;

/**
 *
 * @author John
 */
public class PatternOperatorConcat extends PatternOperator {
    public PatternOperatorConcat(){
        setArguments(new ArrayList());
        setElementName("Pattern Operator Concat");
    }
    
    public MatchResult evaluate(String subject, int pos){
        MatchResult result = new MatchResult();
        result.setPos(pos);
        result.setResult(""); //.setSubString("");
        result.setSuccess(true);
        return result;
    }
}
