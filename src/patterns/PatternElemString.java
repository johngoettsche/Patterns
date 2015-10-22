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
        String subString = subject.substring(pos);
        if(subString.startsWith(getCharSet())) {
            int start = subject.indexOf(getCharSet());
            int stop = start + getCharSet().length();
            this.setResult(new MatchResult(stop, getCharSet(), true));
        } else {
            this.setResult(new MatchResult(pos, "", false));
        }
        return this.getResult();
    }
    
}
