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
public class PatternTypeCSet extends PatternType{
    public PatternTypeCSet(String st) {
        setCharSet(st);
        setArguments(new ArrayList());
        setElementName("Pattern Type C-Set " + st);
    }
    
    public MatchResult evaluate(String subject, int pos){
        this.setResult(new MatchResult(pos, getCharSet(), true));
        return this.getResult();
    }
    
    @Override
    public void onMatch(){}
}
