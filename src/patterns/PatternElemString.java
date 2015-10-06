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
public class PatternElemString extends PatternElem {
    public PatternElemString(String st) {
        setCharSet(st);
        setArguments(new ArrayList());
        setElementName("Pattern Elem String " + st);
    }
    
    public MatchResult evaluate(String subject, int pos) {
        MatchResult result = new MatchResult();
        String subString = subject.substring(pos);
        if(subString.startsWith(getCharSet())) {
            int start = subject.indexOf(getCharSet());
            int stop = start + getCharSet().length();
            result.setSubString(getCharSet());
            result.setPos(stop);
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
            result.setSubString("");
            result.setPos(pos);
        }
        return result;
    }
    
}
