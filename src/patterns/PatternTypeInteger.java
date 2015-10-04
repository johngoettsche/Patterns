/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patterns;

import java.util.List;

/**
 *
 * @author John H. Goettsche
 *  public MatchResult(int p, String s){
 */
public class PatternTypeInteger extends PatternType{
    public PatternTypeInteger(String st) {
        setCharSet(st);
        setElementName("Pattern Type Integer " + st);  
    }
    
    public MatchResult evaluate(String subject, int pos){
        MatchResult result = new MatchResult(pos, getCharSet());
        result.setPos(pos);
        result.setSubString("");
        result.setIntValue(Integer.parseInt(getCharSet()));
        return result;
    }
}