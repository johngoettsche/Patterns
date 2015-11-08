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
 *  public MatchResult(int p, String s){
 */
public class PatternTypeInteger extends PatternType{
    public PatternTypeInteger(String st) {
        setCharSet(st);
        setArguments(new ArrayList());
        setElementName("Pattern Type Integer " + st);  
    }
    
    public MatchResult evaluate(String subject, int pos){
        this.setResult(new MatchResult(pos, getCharSet(), true));
        //System.out.println(getResult().getResult());
        return this.getResult();
    }
    
    @Override
    public void onMatch(){}
}
