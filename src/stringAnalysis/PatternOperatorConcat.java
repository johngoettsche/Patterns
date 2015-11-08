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
public class PatternOperatorConcat extends PatternOperator {
    public PatternOperatorConcat(){
        setArguments(new ArrayList());
        setElementName("Pattern Operator Concat");
    }
    
    public MatchResult evaluate(String subject, int pos){
        //System.out.println("\t" + this.getDefinition().getPrevious().getResult().isSuccess());
        this.setResult(new MatchResult(pos, "", true)); //this.getDefinition().getPrevious().getResult().isSuccess()));
        return this.getResult();
    }
    
    @Override
    public void onMatch(){}
}
