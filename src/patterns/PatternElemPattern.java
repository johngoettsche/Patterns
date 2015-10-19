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
public class PatternElemPattern extends PatternElem {
    Pattern pattern;
    
    public PatternElemPattern(String pat){
        setArguments(new ArrayList());
        pattern = new Pattern(pat);
        setElementName("Pattern Elem Pattern " + pat);
    }
    
    public MatchResult evaluate(String subject, int pos){
        MatchResult matchResult = new MatchResult(pos, "");
        matchResult = pattern.match(subject, pos);
        return matchResult;
    } 
}
