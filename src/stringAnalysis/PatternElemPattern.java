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
public class PatternElemPattern extends PatternElem {
    Pattern pattern;
    
    public PatternElemPattern(String pat){
        setArguments(new ArrayList());
        pattern = new Pattern(pat);
        setElementName("Pattern Elem Pattern " + pat);
    }
    
    public MatchResult evaluate(String subject, int pos){
        this.setResult(pattern.match(subject, pos));
        return this.getResult();
    } 
    
    @Override
    public void onMatch(){}
}
