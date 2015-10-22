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
public class PatternElemEnd extends PatternElem {
    public PatternElemEnd(){
        
    }
    
    public MatchResult evaluate(String subject, int pos){
        this.setResult(new MatchResult(0, "", false));
        return this.getResult();
    }    
}
