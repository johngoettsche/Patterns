/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringAnalysis;

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
        this.setResult(new MatchResult(pos, "", false));
        return this.getResult();
    }
    
    @Override
    public void onMatch(){}
}
