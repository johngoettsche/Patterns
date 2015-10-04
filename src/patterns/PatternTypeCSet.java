/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patterns;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author John
 */
public class PatternTypeCSet extends PatternType{
    public PatternTypeCSet(String st) {
        setCharSet(st);
        setArguments(defineFuncArguments(st));
        setElementName("Pattern Type C-Set " + st);
    }
    
    public MatchResult evaluate(){
        MatchResult result = new MatchResult();
        result.setPos(0);
        result.setSubString("");
        
        return result;
    }
}
