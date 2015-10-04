/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patterns;

/**
 *
 * @author John
 */
public class PatternElemEnd extends PatternElem {
    public PatternElemEnd(){
        
    }
    
    public MatchResult evaluate(String subject, int pos){
        MatchResult matchResult = new MatchResult(0, "");
        return matchResult;
    }    
}
