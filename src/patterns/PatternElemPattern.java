/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patterns;

/**
 *
 * @author John H. Goettsche
 */
public class PatternElemPattern extends PatternElem {
    Pattern pattern;
    
    public PatternElemPattern(String pat){
        pattern = new Pattern(pat);
    }
    
    public MatchResult evaluate(String subject, int pos){
        MatchResult matchResult = new MatchResult(0, "");
        matchResult = pattern.match(subject, pos);
        return matchResult;
    } 
}
