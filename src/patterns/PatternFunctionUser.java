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
public class PatternFunctionUser extends PatternFunction{
    public PatternFunctionUser(){
        
    }
    
    public MatchResult evaluate(){
        MatchResult result = new MatchResult();
        result.setPos(0);
        result.setSubString("");
        
        return result;
    }
}
