/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stringAnalysis;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author John H. Goettsche
 */
public class PatternTypeString extends PatternType{
    
    public PatternTypeString(String st) {
        setCharSet(st);
        setArguments(new ArrayList());
        setElementName("Pattern Type String " + st);
    }
    
    public MatchResult evaluate(String subject, int pos){
        this.setResult(new MatchResult(pos, getCharSet(), true));
        return this.getResult();
    }

    @Override
    public void onMatch(){}
}
